package team.dedica.logoart.scenes.forest

import team.dedica.logoart.positioners.AreaWithProtectedPathPositioner
import team.dedica.logoart.scenes.Scene
import team.dedica.logoart.positioners.Positioner
import team.dedica.logoart.positioners.SingleItemPositioner
import team.dedica.logoart.items.flower.FlowerSeeder
import team.dedica.logoart.positioners.HorizonPositioner
import team.dedica.logoart.items.tree.TreeSeeder

private const val HORIZON_HEIGHT_FACTOR = 0.385f

class Forest : Scene {

    private val positioners: MutableList<Positioner> = mutableListOf()

    override fun setup(width: Int, height: Int) {

        positioners.add(HorizonPositioner(width, height, getHorizonHeightFactor()))
        positioners.add(AreaWithProtectedPathPositioner(width, height, getHorizonHeightFactor(), FlowerSeeder()))
        positioners.add(SingleItemPositioner(width, height, TreeSeeder()))

        val size = positioners.sumOf { positioner -> positioner.calculate() }
        println("Planters planted $size objects.")
    }

    override fun draw(sceneApplet: SceneApplet) {
        positioners.forEach { planter -> planter.draw(sceneApplet) }
    }

    override fun getHorizonHeightFactor(): Float {
        return HORIZON_HEIGHT_FACTOR
    }
}