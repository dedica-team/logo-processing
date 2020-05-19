/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/144159*@* */
/* !do not delete the line above, required for linking your tweak if you upload again */
import processing.svg.*;

class pathfinder {
  PVector lastLocation;
  PVector location;
  PVector velocity;
  float diameter;
  boolean isFinished = false;
  boolean hasDrawn = false;
  float branchCapacity = 1;
  
  pathfinder() {
    lastLocation = new PVector(width/2 + width/random(-10,10), height); 
    velocity = new PVector(0, -10);
    diameter = random(10, 20);
  }

  pathfinder(PVector origin, PVector velocity, float diameter) {
    this.lastLocation = origin;
    this.velocity = velocity;
    this.diameter = diameter * random(0.7, 0.9);
  }

  void update() {
    
    if (isFinished || hasDrawn)
      return;
  
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
        isFinished = true;
      }
    } else {
      isFinished = true;
    }
  }
  
  void branch() {
    
    if (isFinished)
      return;
  
    if (branchCapacity > 0.2) { // control length
      if (random(0, 1) < branchCapacity) {
        paths = (pathfinder[]) append(paths, new pathfinder(new PVector(location.x, location.y), new PVector(velocity.x, velocity.y), diameter));
      }
      branchCapacity *=0.3;
    } else {
      if (hasDrawn)
        isFinished = true; //else retries branching forever
    }
    
  }
  
  void drawTwig() {
    if (hasDrawn)
      return;
    strokeWeight(diameter);
    line(lastLocation.x, lastLocation.y, location.x, location.y);
    hasDrawn = true;
  }
  
  void drawLeave() {
    if (isFinished) {
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
pathfinder[] paths;
int num; //number of trees
static int count;


void setup() {
  beginRecord(SVG, "everything.svg");
  size(2048, 1024);
  
  background(40,40, 40);
  ellipseMode(CENTER);
  //orange
  stroke(255, 100, 0, 255);
  smooth();
  num = 1;
  count = 0;
  paths = new pathfinder[num];
  for(int i = 0; i < num; i++) paths[i] = new pathfinder();
}

void draw() {

  boolean allFinished  = true;
  for (int i = 0; i < paths.length; i++) {
    
    paths[i].update();
    paths[i].drawTwig();
    paths[i].branch();
    paths[i].drawLeave();
    if (!paths[i].isFinished)
      allFinished = false;
  }
  
  if (allFinished) {
    endRecord();
    exit();
  }
}
