/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/144159*@* */
/* !do not delete the line above, required for linking your tweak if you upload again */
import processing.svg.*;

class pathfinder {
  PVector lastLocation;
  PVector location;
  PVector velocity;
  float diameter;
  boolean isFinished = false;
  boolean bloom = false;
  boolean hasDrawn = false;
  float branchCapacity = 1;
  pathfinder[] children = new pathfinder[0];
  
  pathfinder(PVector seed) {
    lastLocation = seed; 
    velocity = new PVector(0, -10);
    diameter = random(10, 20);
    this.grow();
    this.branch();
  }

  pathfinder(PVector origin, PVector velocity, float diameter) {
    this.lastLocation = origin;
    this.velocity = velocity;
    this.diameter = diameter * random(0.7, 0.9);
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
        velocity.mult(0.8); //slower growth
        
        //slight bump into other direction
        float upwards  = -1.0;
        float downwards = 0.5;
        PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
        bump.mult(0.2);
        velocity.add(bump);
        
        velocity.mult(random(19, 20));
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
        children = (pathfinder[]) append(children, new pathfinder(new PVector(location.x, location.y), new PVector(velocity.x, velocity.y), diameter));
      }
      branchCapacity *=0.4;
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

int num; //number of trees
pathfinder[] paths;

void setup() {
  beginRecord(SVG, "everything.svg");
  size(2048, 1024);
  
  background(40,40, 40);
  ellipseMode(CENTER);
  //orange
  stroke(255, 100, 0, 255);
  smooth();
  num = 4;
  paths = new pathfinder[num];
  for(int i = 0; i < num; i++) {
    float centerOffset = width *0.1 * random(-3,3); //max 50%
    paths[i] = new pathfinder(new PVector(width/2 + centerOffset, height));
  }
}

void draw() {

  boolean allFinished  = true;
  for (int i = 0; i < paths.length; i++) {
    paths[i].branch();
    if (!paths[i].isFinished)
      allFinished = false;
  }
  
  //everything must be drawn in the last frame in order to appear in the svg
  if (allFinished) {
    for (int i = 0; i < paths.length; i++) {
      paths[i].drawTwig();
      paths[i].drawLeave();
    }
    endRecord();
    exit();
  }
}
