package team.dedica.logo.positioners

import processing.core.PApplet
import processing.core.PVector
import team.dedica.logo.items.Drawable
import team.dedica.logo.items.Seeder
import team.dedica.logo.Util

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
