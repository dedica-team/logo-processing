package team.dedica.logoart.items.sun

import processing.core.PApplet
import team.dedica.logoart.items.Drawable

class Sun (
    private val x: Float,
    private val y: Float,
    private val diameter: Float,
) : Drawable {

    override fun draw(applet: PApplet) {
        applet.noStroke()
        applet.fill(255f, 100f, 0f, 255f) //orange
        applet.circle(x, y, diameter)
    }
}
