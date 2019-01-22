package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class PlayerShip extends Ship implements ISteerable, IDrawable, ICollider
{
	private SteerableMissileLauncher launcher;
	private final int MAX_MISSILE = 10;
	private double deltaX, deltaY;
	private int direction, speed;
	private int width, height;
	private int size;
	private boolean alive;
	GameWorld gw;
	
	public PlayerShip(GameWorld gw, int width, int height)
	{
		this.gw = gw;
		setSize(2*20);
		size = getSize();
		this.width = width;
		this.height = height;
		launcher = new SteerableMissileLauncher();
		// center of world
		super.setX(width/2);
		super.setY(height/2);
		// 0 speed
		super.setSpeed(0);
		// head north
		super.setDirection(0);
		// starting missile count
		super.setMissileCount(MAX_MISSILE);
		// set launcher direction at north
		launcher.setDirection(0);
		this.alive = true;
	}
	
	public int getLauncherDir()
	{
		return this.launcher.getDirection();
	}
	
	public void fire()
	{
		if (hasMissile())
		{
			super.setMissileCount(super.getMissileCount()-1);
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
	
	public void reload()
	{
		super.setMissileCount(MAX_MISSILE);
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public double getDeltaX()
	{
		return deltaX;
	}
	
	public double getDeltaY()
	{
		return deltaY;
	}
	
	public void steer(char direction, int turnAmt)
	{
		switch (direction)
		{
			case 'r':
			case 'R':
				steerRight(turnAmt);
				break;
				
			case 'l':
			case 'L':
				steerLeft(turnAmt);
				break;
				
			default:
				System.out.println("Steerable Ship direction is 'r' or 'l', turn range 0-359");
				break;
		}
		
	}
	
	public void steerLeft(int turnAmt)
	{
		int dir = (super.getDirection() + turnAmt) % 360;
		super.setDirection(dir);
	}
	
	public void steerRight(int turnAmt)
	{
		int dir = (super.getDirection() 
				+ 360 - (turnAmt%360)) % 360;
		super.setDirection(dir);
	}
	
	public void steerLauncherLeft(int turnAmt)
	{
		this.launcher.steerLeft(turnAmt);
	}
	
	public void steerLauncherRight(int turnAmt)
	{
		this.launcher.steerRight(turnAmt);
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		double x = pCmpRelPrnt.getX() + getX();
		double y = pCmpRelPrnt.getY() + getY();
		double theta = Math.toRadians(super.getDirection());
		double phi = Math.toRadians(launcher.getDirection());
		
		// rotate ship at origin
		double r1x = -(size/2)*Math.sin(theta);
		double r1y = (size/2)*Math.cos(theta);
		double r2x = ((-size/4)*Math.cos(theta)) + ((size/2)*Math.sin(theta));
		double r2y = ((-size/4)*Math.sin(theta)) - ((size/2)*Math.cos(theta));
		double r3x = ((size/4)*Math.cos(theta)) + ((size/2)*Math.sin(theta));
		double r3y = ((size/4)*Math.sin(theta)) - ((size/2)*Math.cos(theta));
		// translate to Ship's position
		int x1 = (int) (x + r1x);
		int y1 = (int) (y + r1y);
		int x2 = (int) (x + r2x);
		int y2 = (int) (y + r2y);
		int x3 = (int) (x + r3x);
		int y3 = (int) (y + r3y);
		// draw ship
		g.setColor(super.getColor());
		g.fillTriangle(x1, y1, x2, y2, x3, y3);
		
		// rotate gun at origin
		double recWidth = 6;
		double recLength = 30;
		// p1(x,y)
		double rr1x = rotatePointX(-recWidth/2, recLength, phi);
		double rr1y = rotatePointY(-recWidth/2, recLength, phi);
		// p2(x,y)
		double rr2x = rotatePointX(recWidth/2, recLength, phi);
		double rr2y = rotatePointY(recWidth/2, recLength, phi);
		// p3(x,y)
		double rr3x = rotatePointX(-recWidth/2, 0, phi);
		double rr3y = rotatePointY(-recWidth/2, 0, phi);
		// p4(x,y)
		double rr4x = rotatePointX(recWidth/2, 0, phi);
		double rr4y = rotatePointY(recWidth/2, 0, phi);
		// translate to ship's position
		// p1
		int rx1 = (int) (x + rr1x);
		int ry1 = (int) (y + rr1y);
		// p2
		int rx2 = (int) (x + rr2x);
		int ry2 = (int) (y + rr2y);
		// p3
		int rx3 = (int) (x + rr3x);
		int ry3 = (int) (y + rr3y);
		// p4
		int rx4 = (int) (x + rr4x);
		int ry4 = (int) (y + rr4y);
		// draw gun
		g.setColor(ColorUtil.BLACK);
		g.drawLine(rx1, ry1, rx2, ry2);
		g.drawLine(rx2, ry2, rx4, ry4);
		g.drawLine(rx4, ry4, rx3, ry3);
		g.drawLine(rx3, ry3, rx1, ry1);
		
		
	}
	
	public void move()
	{
		double currentX = super.getX();
		double currentY = super.getY();
		speed = super.getSpeed();
		direction = super.getDirection();
		double dir = Math.toRadians(direction) + Math.PI/2;
		
		deltaX = Math.cos(dir) * speed;
		deltaY = Math.sin(dir) * speed;
		
		if ( (currentX+size >= width) ) 
			currentX = 0 + size;
		if ( (currentX < 0) )
			currentX = width - size;
		if ( (currentY+size >= height) )
			currentY = 0 + size;
		if ( (currentY < 0) )
			currentY = height - size;
		
		super.setX(currentX + deltaX);
		super.setY(currentY + deltaY);
	}
	
	public double rotatePointX(double x, double y, double theta)
	{
		return (x*Math.cos(theta)) - (y*Math.sin(theta));
	}
	
	public double rotatePointY(double x, double y, double theta)
	{
		return (x*Math.sin(theta)) + (y*Math.cos(theta));
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
		// player ship collides with asteroid
		if(obj instanceof Asteroid)
		{
			this.alive = false;
			gw.deadShip();
		}
		// player ship collides with non-player ship
		else if(obj instanceof NonPlayerShip)
		{
			this.alive = false;
			gw.deadShip();
		}
		// player ship collides with non-player missile
		else if(obj instanceof NonPlayerMissile)
		{
			this.alive = false;
			gw.deadShip();
		}
		// player ship collides with non-player ship
		else if(obj instanceof NonPlayerShip)
		{
			this.alive = false;
			gw.deadShip();
		}
		// player ship gets ammo from space station
		else if(obj instanceof SpaceStation)
		{
			reload();
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
				    + super.getDirection() + " Missiles=" + super.getMissileCount()
				    + " Missile_Launcher_Dir=" + launcher.getDirection();
		return parentDesc + desc;
	}
	
}
