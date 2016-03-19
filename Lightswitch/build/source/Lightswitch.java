import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Lightswitch extends PApplet {

DebugTool debugger;
Lightbulb lightbulb;
Generator generator;
SwitchBox switchbox;

public void setup() {
  
  rectMode(RADIUS);

  debugger = new DebugTool(5);
  lightbulb = new Lightbulb();
  generator = new Generator();
  switchbox = new SwitchBox();
}

public void draw() {
  background (80);
  lightbulb.draw();
  switchbox.draw();
  generator.draw();
  //debugger.positionDebug();
}

public void mouseClicked() {
  //debugger.printMouseCoordinates();

  if (switchbox.collider.isWithin(mouseX,mouseY)) {
    switchbox.toggleSwitchbox();
  }

  if (generator.boxCollider.isWithin(mouseX,mouseY)) {
    generator.turnGenerator();
  }
}

class CollisionBox {
  int x;
  int y;
  int xRad;
  int yRad;
  public CollisionBox(int x, int y, int xRadius, int yRadius) {
    this.x = x;
    this.y = y;
    xRad = xRadius;
    yRad = yRadius;
  }

  public boolean withinX (int checkX) {
    return checkX <= (x + xRad/2.0f) && checkX >= (x - xRad/2.0f);
  }

  public boolean withinY (int checkY) {
    return checkY <= (y + yRad/2.0f) && checkY >= (y - yRad/2.0f);
  }

  public boolean isWithin(int checkX, int checkY) {
    return withinX(checkX) && withinY(checkY);
  }
}

class DebugTool {

  int pX;
  int pY;
  int sens;

  public DebugTool(int sensitivity) {
    pX = 0;
    pY = 0;
    sens = sensitivity;
  }

  public void positionDebug() {
    line(mouseX,0,mouseX,height);
    line(0,mouseY,width,mouseY);
    if ( pow(pow(abs(mouseX - pX),2)+pow(abs(mouseY - pY),2),0.5f) > sens)
    {
      printMouseCoordinates();
    }
    pX = mouseX;
    pY = mouseY;
  }

  public void printMouseCoordinates() {
    println("(" + mouseX + "," + mouseY + ")");
  }
}

class Lightbulb {

  int maxBrightness;
  int currentBrightness;
  float brightnessDegrade;

  public Lightbulb() {
    maxBrightness = 240;
    currentBrightness = 0;
    brightnessDegrade = 3;
  }

  public void draw() {
    if (switchbox.on && frameCount % 3 == 0) {
      currentBrightness = maxBrightness;
  }
    strokeWeight(1);
    fill(240,240,0,currentBrightness);
    ellipse(250,100,50,90);
    fill(187);
    rect(250,110,30,4,10);
    rect(250,118,30,4,10);
    rect(250,126,25,4,10);
    rect(250,134,20,4,10);
    rect(250,142,15,4,10);
    fill(0);
    rect(250,150,5,4,10);
    line(256,106,261,83);
    line(244,106,239,83);
    strokeWeight(2);
    line(250,150,250,250);
    line(400,250,100,250);
    line(100,250,100,350);
    for (int r = currentBrightness; r > 50; r-=5) {
      noStroke();
      fill(250,250,0,5);
      ellipse(250,94,r,r);
      stroke(0);
    }
    if (currentBrightness > 0) {
      currentBrightness -= brightnessDegrade;
    }
  }

  public void addBrightness(int lumens) {
    if (currentBrightness < 255) {
      currentBrightness += lumens;
    }
  }
}

class Generator {
  int clickedFill;
  float spinSpeed;
  float angleGoTo;
  float originAngle;
  float currentAngle;
  float t;
  CollisionBox boxCollider;

  public Generator() {
    boxCollider = new CollisionBox(300,400,25,25);
    currentAngle = 0;
    clickedFill = color(120);
  }

  public void draw() {
    strokeWeight(2);
    line(400,250,400,350);
    line(300,400,350,400);
    rect(300,400,25,25,10);
    translate(400,400);
    currentAngle = lerp(originAngle,angleGoTo,t);
    rotate(currentAngle);
    ellipse(0,0,100,100);
    line(-50,0,50,0);
    line(0,-50,0,50);
    rotate(40);
    line(-50,0,50,0);
    line(0,-50,0,50);
    translate(-400,-400);
    if (t < 1) {
      t += (2/frameRate)*(1-t);
    }
  }

  public void turnGenerator() {
    t = 0;
    originAngle = currentAngle;
    lightbulb.addBrightness(30);
    angleGoTo += 1;
  }
}

class SwitchBox {
  int onPosition;
  int offPosition;
  int position;
  boolean on;
  CollisionBox collider;

  public SwitchBox() {
    on = false;
    onPosition = 378;
    offPosition = 423;
    position = offPosition;
    collider = new CollisionBox(100,400,20,46);
  }

  public void toggleSwitchbox() {
    if (on) {
      position = offPosition;
    }
    else {
      position = onPosition;
    }
    on = !on;
  }

  public void draw() {
    fill(190);
    rect(100,400,25,50);
    fill(20);             // Off fill
    rect(100,377,20,23);  // Off background
    fill(230,230,0);      // On fill
    rect(100,423,20,23);  // On background
    fill(230);
    rect(100,position,20,23,5);
  }
}
  public void settings() {  size(500,500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Lightswitch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
