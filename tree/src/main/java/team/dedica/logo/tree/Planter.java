package team.dedica.logo.tree;

import processing.core.PApplet;

import java.util.List;

public interface Planter {

    float GOLDEN_CUT = 1.618033988749f;

    /**
     * @return the planted plants
     */
    List<PlantSegment> plant();

    void draw(PApplet applet, List<PlantSegment> plants);
}
