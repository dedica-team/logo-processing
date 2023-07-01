package team.dedica.logoart.positioners

import processing.core.PApplet
import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder
import team.dedica.logoart.Util

class SingleItemPositioner(width: Int, height: Int, private val seeder: Seeder) : Positioner {

    private val x: Float
    private val y: Float

    private lateinit var tree: Drawable

    init {
        x = (width * 0.2305 + Util.random(-10f, 10f)).toFloat()
        y = (height * 0.8).toFloat()
    }

    override fun calculate(): Int {
        tree = seeder.getSeed(PVector(x, y), 1f)
        return 1
    }

    override fun draw(applet: PApplet) {
        tree.draw(applet)
    }
}
