package team.dedica.logo.forest.items

import processing.core.PApplet
import processing.core.PVector

class SingleTreePositioner(width: Int, height: Int) : Positioner {

    private val x: Float
    private val y: Float

    private lateinit var tree: PlantSegment

    init {
        x = (width * 0.2305 + Util.random(-10f, 10f)).toFloat()
        y = (height * 0.8).toFloat()
    }

    override fun calculate(): Int {
        tree = TreeParameters().getSeed(PVector(x, y), 1f)
        return 1
    }

    override fun draw(applet: PApplet) {
        tree.draw(applet)
    }
}
