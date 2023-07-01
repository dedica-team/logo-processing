package team.dedica.logo.forest.items

import processing.core.PApplet
import processing.core.PVector
import java.util.function.Consumer

/**
 * Plants a forest with a path through the trees.
 *
 *
 */
class ForestPositioner(
    private val width: Int,
    private val height: Int,
    private val parameters: GrowthParameters
) : Positioner {

    private val plants: MutableList<Drawable> = ArrayList()

    override fun calculate(): Int {
        val limit = height * (1 - MAX_FOREST_HEIGHT_FACTOR)
        var step = 2
        var line = limit.toInt()
        while (line < height) {
            val chance = (height - line) / limit
            var test = Util.random(0f, 1f)
            if (test > chance) {
                line += step
                continue
            }

            //scale
            val scaleRatio = (line - limit) / (height - limit)
            step = (step * (1.32 + scaleRatio)).toInt()
            if (scaleRatio < 0.01) {
                line += step
                continue
            }
            val scRSq = (1 - scaleRatio) * (1 - scaleRatio / 2) //very low for "near" lines
            //draw the actual number of trees on this line
            val path = getPath(scaleRatio, width)
            var x = 10
            while (x < width - 10) {
                test = Util.random(0f, 1f)
                if (test > scRSq) {
                    x = (x + Math.max(1f, 100 * scaleRatio)).toInt()
                    continue
                }
                if (x > path.x && x < path.y) {
                    x = (x + Math.max(1f, 100 * scaleRatio)).toInt()
                    continue
                }
                val seedX = Util.random((x - 10).toFloat(), (x + 10).toFloat())
                val halfStep = (step / 2.5).toFloat()
                val y = line + Util.random(-halfStep, halfStep)
                plants.add(parameters.getSeed(PVector(seedX, y), scaleRatio))
                x = (x + Math.max(1f, 100 * scaleRatio)).toInt()
            }
            line += step
        }
        return plants.size
    }

    override fun draw(applet: PApplet) {
        applet.background(40f, 40f, 40f)
        applet.ellipseMode(PApplet.CENTER)
        applet.stroke(255f, 100f, 0f, 255f) //orange
        applet.smooth()
        applet.noStroke()
        applet.fill(60f, 60f, 60f, 255f)
        val horizon = (1 - MAX_FOREST_HEIGHT_FACTOR) * height + 10
        applet.rect(0f, 0f, width.toFloat(), horizon)
        plants.forEach(Consumer { plant -> plant.draw(applet) })
    }

    private fun getPath(scaleRatio: Float, width: Int): PVector {
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
