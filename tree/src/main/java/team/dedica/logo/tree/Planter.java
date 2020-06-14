package team.dedica.logo.tree;

import processing.core.PApplet;

public interface Planter {

    float GOLDEN_CUT = 1.618033988749f;

    /**
     * @return the number of planted items
     */
    int plant();

    /**
     * Draws the planted items.
     * @param applet aplpet to draw on
     */
    void draw(PApplet applet);
}
