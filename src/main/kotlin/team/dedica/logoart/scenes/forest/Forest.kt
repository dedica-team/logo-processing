package team.dedica.logoart.scenes.forest

import processing.core.PVector
import team.dedica.logoart.Util
import team.dedica.logoart.positioners.AreaWithProtectedPathPositioner
import team.dedica.logoart.scenes.Scene
import team.dedica.logoart.positioners.Positioner
import team.dedica.logoart.positioners.SingleItemPositioner
import team.dedica.logoart.items.flower.FlowerSeeder
import team.dedica.logoart.items.sky.SkySeeder
import team.dedica.logoart.items.tree.TreeSeeder

private const val HORIZON_HEIGHT_FACTOR = 0.385f

class Forest : Scene {

    private val positioners: MutableList<Positioner> = mutableListOf()

    override fun setup(width: Int, height: Int) {

        positioners.add(SingleItemPositioner(getSkyDimension(width, height), SkySeeder()))
        positioners.add(AreaWithProtectedPathPositioner(width, height, getHorizonHeightFactor(), FlowerSeeder()))
        positioners.add(SingleItemPositioner(getSingleTreePosition(width, height), TreeSeeder()))

        val size = positioners.sumOf { positioner -> positioner.calculate() }
        println("Planters planted $size objects.")
    }

    private fun getSkyDimension(width: Int, height: Int) =
        PVector(width.toFloat(), height * getHorizonHeightFactor())

    private fun getSingleTreePosition(width: Int, height: Int): PVector {
        return PVector((width * 0.2305 + Util.random(-10f, 10f)).toFloat(), (height * 0.8).toFloat())
    }

    override fun draw(sceneApplet: SceneApplet) {
        positioners.forEach { planter -> planter.draw(sceneApplet) }
    }

    override fun getHorizonHeightFactor(): Float {
        return HORIZON_HEIGHT_FACTOR
    }
}