package team.dedica.logo.tree;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

public class TreeSegment {

    public static final double MIN_DIAMETER_TO_GROW = 0.2;
    public static final double MIN_BRANCH_CAPACITY = 0.3;
    public static final double BRANCH_CAPACITY_REDUCTION = 0.35;

    /**
     * branch growth velocition reduction per cycle
     */
    public static final float VELOCITY_REDUCTION = 0.8f;

    PVector lastLocation;
    PVector location;
    PVector velocity;
    float diameter;
    boolean isFinished = false;
    boolean bloom = false;

    //paint only once
    boolean hasDrawnTwig = false;
    boolean hasDrawnLeave = false;

    /**
     * Probability of branching, decreased with every iteration
     */
    float branchCapacity = 1;

    /**
     * tree scale ratio (0-1)
     */
    float scaleRatio;

    /**
     * child branches
     */
    List<TreeSegment> children = new ArrayList<>();

    /**
     * Seeding.
     *
     * @param seed       origin coordinates
     * @param scaleRatio scale Factor, influences velocity
     */
    TreeSegment(PVector seed, float scaleRatio) {
        lastLocation = seed;
        this.scaleRatio = scaleRatio;

        velocity = new PVector(0, random(9, 12) * -1 * scaleRatio);
        diameter = ((random(10, 20)) * scaleRatio);
        this.grow();
        this.branch();
    }

    private TreeSegment(PVector origin, PVector velocity, float diameter, float scaleRatio) {
        this.lastLocation = origin;
        this.velocity = velocity;
        this.diameter = diameter;
        this.scaleRatio = scaleRatio;
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
                velocity.mult(VELOCITY_REDUCTION); //slower growth

                //slight bump into other direction
                float upwards = -1.0f;
                float downwards = 0.5f;
                PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
                bump.mult(0.4f);
                velocity.add(bump);

                velocity.mult(random(20, 30) * scaleRatio);
                if (scaleRatio > 0.8) {
                    velocity.mult(1.5f);
                }
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

        while (branchCapacity > MIN_BRANCH_CAPACITY) { // control length
            if (random(0, 1) < branchCapacity) {
                float diameterScale = random(0.7f, 0.9f);
                if (scaleRatio > 0.8) {
                    diameterScale *= 1.05;
                }
                children.add(
                        new TreeSegment(new PVector(location.x, location.y), new PVector(velocity.x, velocity.y), diameter * diameterScale, scaleRatio)
                );
            }
            branchCapacity *= BRANCH_CAPACITY_REDUCTION;
        }

        isFinished = true;
    }
}