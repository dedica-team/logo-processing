package team.dedica.logoart.items.sky

import processing.core.PApplet
import team.dedica.logoart.items.Drawable

class Sky (
    private val width: Float,
    private val height: Float,
) : Drawable {

    override fun draw(applet: PApplet) {
        applet.noStroke()
        applet.fill(60f, 60f, 60f, 255f)
        applet.rect(0f, 0f, width, height)
    }
}
