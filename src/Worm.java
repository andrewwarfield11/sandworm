
// Worm.java
// Andrew Davison, April 2005, ad@fivedots.coe.psu.ac.th

/* Contains the worm's internal data structure (a circular buffer)
   and code for deciding on the position and compass direction
   of the next worm move.
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;


public class Worm
{
  private Ellipse2D.Double body;
  private Point2D.Double loc;
  private Point2D.Double dest;
  private double xOffset = 0, yOffset = 0;

  private double width, height;   // panel dimensions
  private double speed;


  public Worm(double x, double y, double w, double h, double s) {
    width = w; height = h;
    loc = new Point2D.Double(x,y);
    dest = new Point2D.Double(x,y);
    body = new Ellipse2D.Double(loc.getX(),loc.getY(), w,h);
    speed = s;


  } // end of Worm()

  public void move() {
    loc.setLocation(loc.getX() + xOffset, loc.getY() + yOffset);
    body.x = (int)loc.getX() + (int)xOffset;
    body.y = (int)loc.getY() + (int)yOffset;
  }  // end of move()

  public void setDest(double x, double y) {
    System.out.println("x: " + x + "y: " + y);
    dest.x = x;
    dest.y = y;

    // determine distance travelled this frame for x and y
    double run = dest.getX() - loc.getX();
    System.out.printf("run: %f\n",run);
    double rise = dest.getY() - loc.getY();
    System.out.printf("rise %f\n",rise);
    double hypotenuse = Math.sqrt((run*run) + (rise*rise));
    System.out.printf("hypotenuse: %f\n",hypotenuse);
    double distance = dest.distance(loc);
    System.out.printf("distance: %f\n",distance);
    if(hypotenuse != distance)
      System.out.println("I suck at math.");
    xOffset = run / hypotenuse * speed;
    yOffset = rise / hypotenuse * speed;
    System.out.println("run/hypotenuse:" + (double)run/hypotenuse);
    System.out.printf("x: %f y: %f xoffset: %f yoffset: %f\n",x, y, xOffset,yOffset);
    System.out.println("speed: " + speed);
  }

  public double getXDest() {
    return dest.x;
  }

  public double getYDest() {
    return dest.y;
  }


  public void draw(Graphics g)
  // draw a black worm with a red head
  {
    g.setColor(Color.darkGray);
    g.fillOval((int)body.x,(int)body.y,(int)body.getWidth(),(int)body.getHeight());
  }  // end of draw()

}  // end of Worm class

