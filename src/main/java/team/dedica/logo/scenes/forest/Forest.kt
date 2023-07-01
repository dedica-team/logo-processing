package team.dedica.logo.scenes.forest

import team.dedica.logo.positioners.AreaWithProtectedPathPositioner
import team.dedica.logo.scenes.Scene
import team.dedica.logo.positioners.Positioner
import team.dedica.logo.positioners.SingleItemPositioner
import team.dedica.logo.items.flower.FlowerSeeder
import team.dedica.logo.positioners.HorizonPositioner
import team.dedica.logo.items.tree.TreeSeeder

class Forest : Scene {

    /**
     * The planter responsible for seeding the plants.
     */
    private val positioners: MutableList<Positioner> = mutableListOf()

    override fun setup(width: Int, height: Int) {

        positioners.add(HorizonPositioner(width, height, AreaWithProtectedPathPositioner.MAX_FOREST_HEIGHT_FACTOR))
        positioners.add(AreaWithProtectedPathPositioner(width, height, FlowerSeeder()))
        positioners.add(SingleItemPositioner(width, height, TreeSeeder()))

        val size = positioners.sumOf { positioner -> positioner.calculate() }
        println("Planters planted $size objects.")
    }

    override fun draw(sceneApplet: SceneApplet) {
        positioners.forEach { planter -> planter.draw(sceneApplet) }
    }
}