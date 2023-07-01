package team.dedica.logo.forest.items

import processing.core.PApplet

interface Positioner {

    /**
     * @return the number of planted items
     */
    fun calculate(): Int

    /**
     * Draws the planted items.
     *
     * @param applet applet to draw on
     */
    fun draw(applet: PApplet)
}
