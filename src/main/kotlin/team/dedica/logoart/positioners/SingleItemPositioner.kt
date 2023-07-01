package team.dedica.logoart.positioners

import processing.core.PApplet
import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder
import team.dedica.logoart.Util

class SingleItemPositioner(private val pos: PVector, private val seeder: Seeder) : Positioner {

    private lateinit var drawable: Drawable

    override fun calculate(): Int {
        drawable = seeder.getSeed(PVector(pos.x, pos.y), 1f)
        return 1
    }

    override fun draw(applet: PApplet) {
        drawable.draw(applet)
    }
}
