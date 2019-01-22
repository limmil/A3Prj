package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public class NonPlayerShip extends Ship implements IDrawable, ICollider
{
	private final int MAX_MISSILE = 2;
	private MissileLauncher launcher;
	private boolean fireReady = false;
	private boolean alive;
	private int level;
	// random size 20 or 40
	private int size = 2*( 20*Util.randInt(1, 2) );
	private int width, height;
	private double deltaX, deltaY;
	private int direction, speed;
	
	public NonPlayerShip(int width, int height)
	{
		// location
		super.setSize(size);
		super.setX(Util.randInt(0+size, width-size));
		super.setY(Util.randInt(0+size, height-size));
		
		launcher = new MissileLauncher();
		// max 2 missiles
		super.setMissileCount(MAX_MISSILE);
		// same direction and speed as missile launcher
		super.setDirection(launcher.getDirection());
		//super.setSpeed(launcher.getSpeed());
		super.setSpeed(Util.randInt(0, 6));
		alive = true;
		level = 100;
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
	
	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}
	
	public int getLauncherDirection()
	{
		return super.getDirection();
	}
	
	public void fire()
	{
		if (hasMissile())
		{
			super.setMissileCount(super.getMissileCount()-1);
			super.setDirection(Util.randInt(0, 359));
			fireReady = false;
		}
	}
	
	public boolean hasMissile()
	{
		if (super.getMissileCount() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean fireReady()
	{
		return fireReady;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		double x = pCmpRelPrnt.getX() + getX();
		double y = pCmpRelPrnt.getY() + getY();
		double theta = Math.toRadians(super.getDirection());
		
		// rotate triangle at origin
		double r1x = -(size/2)*Math.sin(theta);
		double r1y = (size/2)*Math.cos(theta);
		double r2x = ((-size/4)*Math.cos(theta)) + ((size/2)*Math.sin(theta));
		double r2y = ((-size/4)*Math.sin(theta)) - ((size/2)*Math.cos(theta));
		double r3x = ((size/4)*Math.cos(theta)) + ((size/2)*Math.sin(theta));
		double r3y = ((size/4)*Math.sin(theta)) - ((size/2)*Math.cos(theta));
		// translate to object position
		int x1 = (int) (x + r1x);
		int y1 = (int) (y + r1y);
		int x2 = (int) (x + r2x);
		int y2 = (int) (y + r2y);
		int x3 = (int) (x + r3x);
		int y3 = (int) (y + r3y);
		g.setColor(super.getColor());
		g.fillTriangle(x1, y1, x2, y2, x3, y3);
		
	}
	
	public void move()
	{
		if (Util.randInt(0, level) == 1 && hasMissile())
		{
			fireReady = true;
		}
		
		double currentX = super.getX();
		double currentY = super.getY();
		
		if ( (currentX+getSize()/2 >= width) || (currentX-getSize()/2 < 0) ) 
			deltaX = -deltaX ;
		if ( (currentY+getSize()/2 >= height) || (currentY-getSize()/2 < 0) )
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
		// asteroid hits non-player ship
		if(obj instanceof Asteroid)
		{
			this.alive = false;
		}
		// player missile hits non-player ship
		if(obj instanceof PlayerMissile)
		{
			this.alive = false;
		}
		// player collides with non-player ship
		if(obj instanceof PlayerShip)
		{
			this.alive = false;
		}
		
	}
	
	public boolean isAlive()
	{
		return this.alive;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Size=" + size;
		return parentDesc + desc;
	}
	
	
	
	
}
