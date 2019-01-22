package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class SpaceStation extends FixGameObject implements IDrawable, ICollider
{
	private int blinkRate;
	private boolean lightOn;
	private int size;
	
	public SpaceStation(int width, int height)
	{
		super(width,height);
		
		blinkRate = Util.randInt(1, 4);
		lightOn = true;
		setSize(80);
	}
	
	// lightOn getters and setters
	public void setLightOn(boolean lightOn)
	{
		this.lightOn = lightOn;
	}
	
	public boolean getLightOn()
	{
		return lightOn;
	}
	
	// blinkRate getter
	public int getBlinkRate()
	{
		return blinkRate;
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt)
	{
		size = getSize();
		int x = (int) (pCmpRelPrnt.getX() + getX());
		int y = (int) (pCmpRelPrnt.getY() + getY());
		
		g.setColor(super.getColor());
		g.fillRoundRect(x-size/2, y-size/2, size, size, 2, 2);
		
		if(lightOn)
		{
			g.setColor(ColorUtil.YELLOW);
			g.fillRoundRect(x-20, y-20, getSize()-40, getSize()-40, 2, 2);
		}
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
		// it does nothing
	}
	
	public boolean isAlive()
	{
		// always alive
		return true;
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Blink Rate=" + blinkRate + " LightOn=" + lightOn;
		return parentDesc + desc;
	}
}
