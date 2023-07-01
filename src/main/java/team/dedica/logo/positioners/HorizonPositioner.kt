package team.dedica.logo.positioners

import processing.core.PApplet
import team.dedica.logo.positioners.Positioner

class HorizonPositioner(
    private val width: Int,
    private val height: Int,
    private val horizonHeightFactor: Float
) : Positioner {

    override fun calculate(): Int {
        return 1
    }

    override fun draw(applet: PApplet) {
        applet.noStroke()
        applet.fill(60f, 60f, 60f, 255f)
        val horizon = height * horizonHeightFactor
        applet.rect(0f, 0f, width.toFloat(), horizon)
    }
}
