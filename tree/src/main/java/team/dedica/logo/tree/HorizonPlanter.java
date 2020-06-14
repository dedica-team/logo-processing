package team.dedica.logo.tree;

import processing.core.PApplet;

public class HorizonPlanter implements Planter {
    private final int width;
    private final int height;

    public HorizonPlanter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int plant() {
        return 1;
    }

    @Override
    public void draw(PApplet applet) {
        applet.noStroke();
        applet.fill(60, 60, 60, 255);
        float horizon = (float) (height * 0.4);
        applet.rect(0, 0, width, horizon);
    }
}
