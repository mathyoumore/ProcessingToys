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

WireManager wire_manager;
Battery battery;
public void setup() {
  
  wire_manager = new WireManager();
  battery = new Battery(300,300);
  rectMode(CENTER);
}

public void draw() {
  background(200);
  wire_manager.draw();
  battery.draw();
}

public void mouseClicked() {
  wire_manager.handleClick();
}

public void keyPressed()
{
  if (key == 'r'){
    wire_manager.resetWires();
  }
}

class Conductor {

    boolean live;

    public Conductor()
    {

    }

    public void toggle_active() {

    }

    public void update() {
      if (live)
      {
        fill(255,255,0);
      }
      else {
        fill(100);
      }
    }

    public void activated() {

    }
}

class Battery {

  private int centerX;
  private int centerY;
  private BatteryPole pole;
  public Battery(int x, int y)
  {
    centerX = x;
    centerY = y;
    pole = new BatteryPole(x,y-20,30,10);
  }

  public void draw()
  {
    int fill = g.fillColor;
    fill(0);
    rect(centerX, centerY, 30,50);
    rect(centerX, centerY-10, 10, 50);
    pole.draw();
    fill(fill);
  }
}

class BatteryPole extends Conductor {

  private int centerX;
  private int centerY;
  private int radiusX;
  private int radiusY;
  public BatteryPole(int x, int y, int radX, int radY) {
    centerX = x;
    centerY = y;
    radiusX = radX;
    radiusY = radY;
    live = true;
  }
   public void draw() {
     int s = g.strokeColor;
     noStroke();
     update();
     rect(centerX, centerY, radiusX, radiusY);
     stroke(s);
  }
}

class WireManager {

  Wire w;
  private ArrayList<Wire> wires;
  boolean drawing;

  public WireManager()
  {
    drawing = false;
    wires = new ArrayList<Wire>();
  }

  public void handleClick()
  {
    if (drawing){
      stopDrawing();
    }
    else {
      startDrawing();
    }
    drawing = !drawing;
  }

  private void startDrawing()
  {
    w = new Wire(mouseX,mouseY);
  }

  private void stopDrawing()
  {
    w.createWire(mouseX,mouseY);
    wires.add(w);
  }

  public void draw()
  {
    if (wires.size() > 0) {
      for (Wire w : wires)
      {
        w.draw();
      }
    }
  }

  public void resetWires(){
    wires = new ArrayList<Wire>();
    w = null;
    drawing = false;
  }
}

class Wire {

  private int startingX;
  private int startingY;
  private int endingX;
  private int endingY;
  public Wire(int x,
              int y) {
    startingX = x;
    startingY = y;
  }

  public void createWire(int x, int y) {
      println("Drawing a line at " + startingX + ", " + startingY + " to " + x + ", " + y);
      endingX = x;
      endingY = y;
  }

  public void draw()
  {
    line(startingX,startingY,endingX,endingY);
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
