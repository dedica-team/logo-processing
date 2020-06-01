package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PVector;


import java.util.Arrays;

/**
 * Tree image generator
 */
public class Tree extends PApplet {

    int num; //number of trees
    PathFinder[] paths;
    boolean svgOutput = false;

    @Override
    public void settings() {
        svgOutput = (args != null && Arrays.asList(args).contains("--svg"));

        if (svgOutput) {
            size(2048, 1024);
        } else {
            fullScreen(SPAN);
        }
    }

    @Override
    public void setup() {
        if (svgOutput) {
            beginRecord(SVG, "tree.svg");
        }

        background(40, 40, 40);
        ellipseMode(CENTER);
        //orange
        stroke(255, 100, 0, 255);
        smooth();
        num = 4;
        paths = new PathFinder[num];
        for (int i = 0; i < num; i++) {
            float centerOffset = (float) (width * 0.1 * random(-3, 3)); //max 50%
            paths[i] = new PathFinder(new PVector(width / 2 + centerOffset, height));
        }
    }

    @Override
    public void draw() {

        if (svgOutput)
            drawSVG();
        else
            drawScreen();

    }

    public static void main(String[] args) {

        // Start the application
        PApplet.main(Tree.class.getName());

    }


    void drawSVG() {

        boolean allFinished  = true;
        for (int i = 0; i < paths.length; i++) {
            if (!paths[i].isFinished)
                allFinished = false;
        }

        //everything must be drawn in the last frame in order to appear in the svg
        if (allFinished) {
            background(40,40, 40);
            for (int i = 0; i < paths.length; i++) {
                paths[i].drawTwig();
                paths[i].drawLeave();
            }

            endRecord();
            exit();
        }
    }

    void drawScreen() {

        boolean allFinished  = true;
        for (int i = 0; i < paths.length; i++) {
            if (!paths[i].isFinished)
                allFinished = false;
        }

        //everything must be drawn in the last frame in order to appear in the svg
        if (allFinished) {
            for (int i = 0; i < paths.length; i++) {
                paths[i].drawTwig();
                paths[i].drawLeave();
            }
        }
    }



    public class PathFinder {

        PVector lastLocation;
        PVector location;
        PVector velocity;
        float diameter;
        boolean isFinished = false;
        boolean bloom = false;
        boolean hasDrawn = false;
        float branchCapacity = 1;
        PathFinder[] children = new PathFinder[0];

        PathFinder(PVector seed) {
            lastLocation = seed;
            velocity = new PVector(0, -10);
            diameter = random(10, 20);
            this.grow();
            this.branch();
        }

        PathFinder(PVector origin, PVector velocity, float diameter) {
            this.lastLocation = origin;
            this.velocity = velocity;
            this.diameter = diameter * random(0.7f, 0.9f);
            this.grow();
            this.branch();
        }

        /**
         * computes the grow destination
         */
        private void grow() {

            location = new PVector(lastLocation.x, lastLocation.y);
            if(location.x > 0 && location.x < width - 10 && location.y > 0 && location.y <= height) {

                if (diameter > 0.2) {

                    velocity.normalize();
                    velocity.mult(0.8f); //slower growth

                    //slight bump into other direction
                    float upwards  = -1.0f;
                    float downwards = 0.5f;
                    PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
                    bump.mult(0.3f);
                    velocity.add(bump);

                    velocity.mult(random(20, 25));
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

            while (branchCapacity > 0.2) { // control length
                if (random(0, 1) < branchCapacity) {
                    children = (PathFinder[]) append(children, new PathFinder(new PVector(location.x, location.y), new PVector(velocity.x, velocity.y), diameter));
                }
                branchCapacity *=0.35;
            }

            isFinished = true;
        }

        /**
         * recurisve drawing of children
         */
        void drawTwig() {
            for (int i = 0; i < children.length; i++) {
                children[i].drawTwig();
            }
            if (hasDrawn)
                return;
            strokeWeight(diameter);
            line(lastLocation.x, lastLocation.y, location.x, location.y);
            hasDrawn = true;
        }

        void drawLeave() {
            for (int i = 0; i < children.length; i++) {
                children[i].drawLeave();
            }
            if (isFinished && bloom) {

                noStroke();
                fill(255, 255, 255, 255);
                ellipse(location.x, location.y, 15, 15);
                //orange
                fill(255, 100, 0, 255);
                ellipse(location.x, location.y, 7, 7);
                //orange
                stroke(255, 100, 0, 200);
            }
        }
    }

}