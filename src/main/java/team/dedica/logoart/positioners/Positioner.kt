package team.dedica.logoart.positioners

import processing.core.PApplet

interface Positioner {

    /**
     * Perform necessary calculates before drawing.
     *
     * @return the number of planted items
     */
    fun calculate(): Int

    /**
     * Draws the Drawables
     *
     * @param applet applet to draw on
     */
    fun draw(applet: PApplet)
}
