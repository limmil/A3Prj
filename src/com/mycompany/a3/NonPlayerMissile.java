package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class NonPlayerMissile extends Missile implements IDrawable, ICollider, ISelectable
{
	private boolean alive;
	private boolean selected;

	public NonPlayerMissile(int direction, double x, double y)
	{
		super(direction, x, y);
		super.setFuelLevel(100);
		alive = true;
		setSize(10);
	}
	
	public void setFuelLevel(int level)
	{
		super.setFuelLevel(level);
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
		g.fillRoundRect((int)x-size/2, (int)y-size/2, size, size, 10, 10);
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
		// find square of sum of radii;
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
		// non-player missile kills player's ship
		if(obj instanceof PlayerShip)
		{
			this.alive = false;
		}
		// non-player missile collides with player missile
		if(obj instanceof PlayerMissile)
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

}
