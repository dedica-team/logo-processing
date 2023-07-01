package team.dedica.logo.forest.items

import team.dedica.logo.forest.SceneApplet

class Forest(private val width: Int, private val height: Int) {

    /**
     * The planter responsible for seeding the plants.
     */
    private val positioners: MutableList<Positioner> = mutableListOf()

    fun setup() {
        positioners.add(HorizonPositioner(width, height, ForestPositioner.MAX_FOREST_HEIGHT_FACTOR))
        positioners.add(ForestPositioner(width, height, FlowerParams()))
        positioners.add(SingleTreePositioner(width, height))
        val size = positioners.sumOf { planter -> planter.calculate() }
        println("Planters planted $size objects.")
    }

    fun draw(sceneApplet: SceneApplet) {
        positioners.forEach { planter -> planter.draw(sceneApplet) }
    }
}