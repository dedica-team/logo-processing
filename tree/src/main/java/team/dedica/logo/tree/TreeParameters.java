package team.dedica.logo.tree;

import processing.core.PVector;

import static team.dedica.logo.tree.Util.random;

public class TreeParameters extends GrowthParameters {

    TreeParameters(float scaleRatio) {
        super(new PVector(0, random(9, 15) * -1 * scaleRatio),
                scaleRatio,
                ((random(10, 25)) * scaleRatio)
        );
    }
}
