package team.dedica.logo.tree;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

public class TreeSegment {

    public static final double MIN_DIAMETER_TO_GROW = 0.2;

    PVector lastLocation;
    PVector location;
    PVector velocity;
    float diameter;
    boolean isFinished = false;
    boolean bloom = false;
    boolean hasDrawn = false;

    /**
     * Probability of branching, decreased with every iteration
     */
    float branchCapacity = 1;

    /**
     * tree scale
     */
    float scale = 1f;

    /**
     * child branches
     */
    List<TreeSegment> children = new ArrayList<>();

    /**
     * @param seed origin coordinates
     * @param scale scale Factor, influences velocity
     */
    TreeSegment(PVector seed, float scale) {
        lastLocation = seed;
        this.scale = scale;
        velocity = new PVector(0, -10 * scale);
        diameter = ((random(10, 20)) * scale);
        this.grow();
        this.branch();
    }

    private TreeSegment(PVector origin, PVector velocity, float diameter, float scale) {
        this.lastLocation = origin;
        this.velocity = velocity;
        this.diameter = diameter * random(0.7f, 0.9f);
        this.scale = scale;
        this.grow();
        this.branch();
    }

    /**
     * computes the grow destination
     */
    private void grow() {

        location = new PVector(lastLocation.x, lastLocation.y);
        if (location.x > 0 && location.y > 0) {

            if (diameter > MIN_DIAMETER_TO_GROW) {

                velocity.normalize();
                velocity.mult(0.8f); //slower growth

                //slight bump into other direction
                float upwards = -1.0f;
                float downwards = 0.5f;
                PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
                bump.mult(0.4f);
                velocity.add(bump);

                velocity.mult(random(20, 25) * scale);
                location.add(velocity);
            } else {
                //too small
                isFinished = true;
                bloom = true;
            }
        } else {
            //out of bounds
            isFinished = true;
        }
    }

    /**
     * creates child branches
     */
    void branch() {

        if (isFinished)
            return;

        while (branchCapacity > 0.3) { // control length
            if (random(0, 1) < branchCapacity) {
                children.add(
                        new TreeSegment(new PVector(location.x, location.y), new PVector(velocity.x, velocity.y), diameter, scale)
                );
            }
            branchCapacity *= 0.35;
        }

        isFinished = true;
    }
}