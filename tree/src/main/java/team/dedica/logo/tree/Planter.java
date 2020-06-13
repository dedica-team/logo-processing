package team.dedica.logo.tree;

import java.util.List;

public interface Planter {

    float GOLDEN_CUT = 1.618033988749f;

    /**
     * @param height height of the area
     * @param width width of the area
     * @return the planted plants
     */
    List<TreeSegment> plant(final int height, final int width);
}
