package team.dedica.logoart.positioners

import processing.core.PApplet
import processing.core.PVector
import team.dedica.logoart.Util
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder
import java.util.function.Consumer
import kotlin.math.max

private const val MARGIN = 10


/**
 * Plants a forest with a path through the trees.
 *
 *
 */
internal class AreaWithProtectedPathPositioner(
    private val width: Int,
    private val height: Int,
    private val seeder: Seeder
) : Positioner {

    private val plants: MutableList<Drawable> = mutableListOf()

    override fun calculate(): Int {

        val limit = height * (1 - MAX_FOREST_HEIGHT_FACTOR)
        var step = 2
        var line = limit.toInt()
        while (line < height) {

            val probability = (height - line) / limit
            if (hasNoChanceToPosition(probability)) {
                line += step
                continue
            }

            //scale
            val scaleRatio = (line - limit) / (height - limit)
            step = (step * (1.32 + scaleRatio)).toInt()
            if (isTooFarAway(scaleRatio)) {
                line += step
                continue
            }

            drawItemsOnLine(scaleRatio, step, line)
            line += step
        }

        return plants.size
    }

    /**
     * draw the actual number of trees on this line
     */
    private fun drawItemsOnLine(scaleRatio: Float, step: Int, line: Int) {

        val scRSq = (1 - scaleRatio) * (1 - scaleRatio / 2) //very low for "near" lines
        val path = getProtectedAreaOnPath(scaleRatio, width)
        var x = MARGIN
        while (x < width - MARGIN) {
            val xPos = (x + max(1f, 100 * scaleRatio)).toInt()
            if (hasNoChanceToPosition(scRSq)) {
                x = xPos
                continue
            }
            if (x > path.x && x < path.y) {
                x = xPos
                continue
            }

            val seed = createSeedAt(x, step, line, scaleRatio)
            plants.add(seed)
            x = xPos
        }
    }

    private fun createSeedAt(
        x: Int,
        step: Int,
        line: Int,
        scaleRatio: Float
    ): Drawable {

        val seedX = Util.random((x - MARGIN).toFloat(), (x + MARGIN).toFloat())
        val halfStep = (step / 2.5).toFloat()
        val y = line + Util.random(-halfStep, halfStep)

        return seeder.getSeed(PVector(seedX, y), scaleRatio)
    }

    private fun isTooFarAway(scaleRatio: Float) = scaleRatio < 0.01

    private fun hasNoChanceToPosition(probability: Float): Boolean {
        val test = Util.random(0f, 1f)
        return test > probability
    }

    override fun draw(applet: PApplet) {
        applet.background(40f, 40f, 40f)
        applet.ellipseMode(PApplet.CENTER)
        applet.stroke(255f, 100f, 0f, 255f) //orange
        applet.smooth()
        applet.noStroke()
        applet.fill(60f, 60f, 60f, 255f)
        val horizon = (1 - MAX_FOREST_HEIGHT_FACTOR) * height + MARGIN
        applet.rect(0f, 0f, width.toFloat(), horizon)
        plants.forEach(Consumer { plant -> plant.draw(applet) })
    }

    private fun getProtectedAreaOnPath(scaleRatio: Float, width: Int): PVector {
        val goldenCut = 1 - 1 / GOLDEN_CUT
        val pathWidth = Util.random(300f, 320f) * scaleRatio
        val center = width * goldenCut
        return PVector(center - pathWidth, center + pathWidth)
    }

    companion object {
        const val MAX_FOREST_HEIGHT_FACTOR = 0.385f
        const val GOLDEN_CUT = 1.618033988749f
    }
}