int seed[];
float current_x_positions[];
float current_y_positions[];
color colors[];
color dot_colors[];
int count;
float radius;
float speed;
int line_offset;
void setup() {
    size(500,200);
    speed = 600;
    count = 100;
    radius = float(width)/count;
    seed = new int[count];
    colors = new color[3];
    colors[0] = color(0,0,0);               // Safe to change
    colors[1] = color(255,0,0);             // Safe to change 
    colors[2] = color(255,255,255);         // Safe to change
    dot_colors = new color[count];
    current_x_positions = new float[count];
    current_y_positions = new float[count];
    line_offset = 5;
    float seed_multiplier = 15;
    for (int i = 0; i < count; i++)
    {
      seed[i] = int(seed_multiplier*i);      // Safe to change
      dot_colors[i] = colors[i%3];          // Safe to change
    }
    fill(255);
}

void draw() {
  background(200);                   // Safe to change
      if (frameCount > 1) { 
      for (int i = 0; i < count - line_offset; i++)
      {  
        stroke(100);
        
       line(current_x_positions[i],current_y_positions[i],current_x_positions[i+line_offset],current_y_positions[i+line_offset]);
      }
    }
    for (int i = 0; i < count; i++)
    {
      noStroke();
      fill(dot_colors[i]);
       current_x_positions[i] = i*radius+2;
       current_y_positions[i] = height/2.0 + 50*sin(seed[i] + millis()/speed);
       ellipse(current_x_positions[i],current_y_positions[i],radius,radius);
      
    }

}