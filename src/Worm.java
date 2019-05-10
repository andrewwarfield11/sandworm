

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.lang.Math;


public class Worm
{
  private Ellipse2D.Double body;
  private Point2D.Double loc;
  private Point2D.Double dest;
  private double xOffset = 0, yOffset = 0;

  private double width, height;   // panel dimensions
  private double speed;
  private int dir; // 0 for not moving, 1 for left, 2 for right


  public Worm(double x, double y, double w, double h, double s) {
    width = w; height = h;
    loc = new Point2D.Double(x,y);
    dest = new Point2D.Double(x,y);
    body = new Ellipse2D.Double(loc.getX(),loc.getY(), w,h);
    speed = s;
    dir = 0;

  } // end of Worm()

  private boolean isAtDest() {
    if( loc.getY() == dest.getY() && loc.getX() == dest.getX() ) {
      return true;
    }
    else {
      return false;
    }
  }

  private boolean isOnScreen() {
    if(loc.getX() < 0 || loc.getY() < 0 || loc.getX() > GameFrame.pWidth
            || loc.getY() > GameFrame.pHeight) {
      return false;
    }
    else
      return true;
  }

  private boolean willPassDest() {
    if( Math.abs(loc.getX()-dest.getX()) <= xOffset || Math.abs(loc.getY() - dest.getY()) <= yOffset) {
      return true;
    }
    else {
      return false;
    }
  }

  public void move() {
    /* if at destination, do nothing
    if worm will pass destination in this move, then stop at the destination
    otherwise, do a normal move
    */
    if(!isAtDest() && isOnScreen()) {
      loc.setLocation(loc.getX() + xOffset, loc.getY() + yOffset);
      body.x = loc.getX() + xOffset;
      body.y = loc.getY() + yOffset;
      if(willPassDest()) {
        loc.setLocation(dest.getX(),dest.getY());
        body.x = dest.getX();
        body.y = dest.getY();
      }
    }
  }  // end of move()

  public void setDest(double x, double y) {
    System.out.println("x: " + x + "y: " + y);
    dest.x = x;
    dest.y = y;

    // determine distance travelled this frame for x and y
    double run = dest.getX() - loc.getX();
    double rise = dest.getY() - loc.getY();
    double hypotenuse = Math.sqrt((run*run) + (rise*rise));

    xOffset = run / hypotenuse * speed;
    yOffset = rise / hypotenuse * speed;
  }

  public double getXDest() {
    return dest.x;
  }

  public double getYDest() {
    return dest.y;
  }


  public void draw(Graphics g)
  {
    g.setColor(Color.darkGray);
    g.fillOval((int)body.x,(int)body.y,(int)body.getWidth(),(int)body.getHeight());
  }  // end of draw()

}  // end of Worm class

