package team.dedica.logoart.scenes.forest

import processing.core.PVector
import team.dedica.logoart.Util
import team.dedica.logoart.items.dedicapath.*
import team.dedica.logoart.positioners.AreaWithProtectedPathPositioner
import team.dedica.logoart.scenes.Scene
import team.dedica.logoart.positioners.Positioner
import team.dedica.logoart.positioners.SingleItemPositioner
import team.dedica.logoart.items.flower.FlowerSeeder
import team.dedica.logoart.items.sky.SkySeeder
import team.dedica.logoart.items.sun.SunSeeder
import team.dedica.logoart.items.tree.TreeSeeder

private const val HORIZON_HEIGHT_FACTOR = 1 - 0.385f

class Forest : Scene {

    private val positioners: MutableList<Positioner> = mutableListOf()

    override fun setup(width: Int, height: Int) {

        val path = listOf(
            CMD_FORWARD,
            CMD_RIGHT,
            CMD_LEFT,
            CMD_FORWARD,
            CMD_LEFT,
            CMD_FORWARD,
            CMD_RIGHT,
            CMD_LEFT,
            CMD_FORWARD,
            CMD_FORWARD,
        )

        positioners.add(SingleItemPositioner(getSkyDimension(width, height), SkySeeder()))
        positioners.add(SingleItemPositioner(getSunPosition(width, height), SunSeeder()))
        positioners.add(AreaWithProtectedPathPositioner(width, height, getHorizonHeightFactor(), FlowerSeeder()))
        positioners.add(SingleItemPositioner(getSingleTreePosition(width, height), TreeSeeder()))
        positioners.add(SingleItemPositioner(getSnakePosition(width, height), PathSeeder(path)))

        val size = positioners.sumOf { positioner -> positioner.calculate() }
        println("Planters planted $size objects.")
    }

    private fun getSkyDimension(width: Int, height: Int) =
        PVector(width.toFloat(), height * getHorizonHeightFactor())

    private fun getSunPosition(width: Int, height: Int): PVector {
        return PVector((width * 0.7305 + Util.random(-10f, 10f)).toFloat(), (height * 0.2).toFloat())
    }

    private fun getSingleTreePosition(width: Int, height: Int): PVector {
        return PVector((width * 0.2305 + Util.random(-10f, 10f)).toFloat(), (height * 0.8).toFloat())
    }

    private fun getSnakePosition(width: Int, height: Int): PVector {
        return PVector((width * 0.5305 + Util.random(-10f, 10f)).toFloat(), (height * 0.6).toFloat())
    }


    override fun draw(sceneApplet: SceneApplet) {
        sceneApplet.smooth()
        positioners.forEach { planter -> planter.draw(sceneApplet) }
    }

    override fun getHorizonHeightFactor(): Float {
        return HORIZON_HEIGHT_FACTOR
    }
}