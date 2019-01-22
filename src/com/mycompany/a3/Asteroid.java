package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Asteroid extends MoveableGameObject implements IDrawable, ICollider, ISelectable
{
	//private int size = Util.randInt(6, 30);
	private double deltaX, deltaY;
	private int direction, speed;
	private int width, height;
	private boolean alive;
	private boolean selected;
	
	public Asteroid(int width, int height)
	{
		super.setSize(Util.randInt(20, 50));
		
		super.setX(Util.randInt(0+getSize(), width-getSize()));
		super.setY(Util.randInt(0+getSize(), height-getSize()));
		this.width = width;
		this.height = height;
		direction = super.getDirection();
		speed = super.getSpeed();
		alive = true;
		
		if(direction != 0)
		{
			deltaX = Math.cos(direction) * speed;
			deltaY = Math.sin(direction) * speed;
		}
		else
		{
			deltaX = speed;
			deltaY = speed;
		}
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		int size = getSize();
		double x = pCmpRelPrnt.getX() + getX();
		double y = pCmpRelPrnt.getY() + getY();
		if(selected)
		{
			g.setColor(ColorUtil.GREEN);
		}
		else
		{
			g.setColor(getColor());
		}
		g.fillRoundRect((int)x-size/2, (int)y-size/2, size, size, 20, 10);
	}
	
	public void move()
	{
		int size = getSize();
		double currentX = super.getX();
		double currentY = super.getY();
		
		if ( (currentX+size/2 >= width) || (currentX-size/2 < 0) ) 
			deltaX = -deltaX ;
		if ( (currentY+size/2 >= height) || (currentY-size/2 < 0) )
			deltaY = -deltaY ;
		
		super.setX(currentX + deltaX);
		super.setY(currentY + deltaY);
	}
	
	public boolean collidesWith(ICollider obj)
	{
		boolean result = false;
		// find center
		double thisCenterX = getX();
		double thisCenterY = getY();
		GameObject gObj = (GameObject) obj;
		double otherCenterX, otherCenterY;
		double thisRadius, otherRadius;
		otherCenterX = gObj.getX();
		otherCenterY = gObj.getY();
		thisRadius = getSize()/2;
		otherRadius = gObj.getSize()/2;
		// find dist between centers
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		double distBetweenCentersSqr = (dx*dx + dy*dy);
		// find square of sum of radii
		double radiiSqr = (thisRadius*thisRadius 
							+ 2*thisRadius*otherRadius
							+ otherRadius*otherRadius);
		if(distBetweenCentersSqr <= radiiSqr)
		{
			result = true;
		}
		return result;
	}

	public void handleCollision(ICollider obj)
	{
		// player missile kills asteroid
		if(obj instanceof PlayerMissile)
		{
			this.alive = false;
		}
		// asteroid collides with asteroid
		if(obj instanceof Asteroid)
		{
			this.alive = false;
		}
		// player's ship collides with asteroid
		if(obj instanceof PlayerShip)
		{
			this.alive = false;
		}
		// non-player ship collides with asteroid
		if(obj instanceof NonPlayerShip)
		{
			this.alive = false;
		}
		
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public boolean isSelected(Point p)
	{
		selected = false;
		// check if test point p is in object
		if( (p.getX() >= getX()-getSize()/2) && (p.getX() <= getX()+getSize()/2) )//between X
		{
			if( (p.getY() >= getY()-getSize()/2) && (p.getY() <= getY()+getSize()/2) )//between Y
			{
				selected = true;
			}
		}
		return selected;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Size=" + getSize();
		return parentDesc + desc;
	}
	
}
