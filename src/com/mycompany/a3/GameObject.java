package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;

public class GameObject
{
	private Point2D location;
	private int color;
	private int size;
	
	public GameObject()
	{
		location = new Point2D(0,0);
		color = 0;
	}
	
	// location getters and setters
	public void setX(double location)
	{
		this.location.setX(location);
	}
	public void setY(double location)
	{
		this.location.setY(location);
	}
	public double getX()
	{
		return location.getX();
	}
	public double getY()
	{
		return location.getY();
	}
	
	// color getter and setters
	public void setColor(int color)
	{
		this.color = color;
	}
	public int getColor()
	{
		return color;
	}
	
	// size getter and setters
	public void setSize(int size)
	{
		this.size = size;
	}
	public int getSize()
	{
		return size;
	}
	
	// toString
	public String toString()
	{
		String desc = "Loc=(" + Math.round(getX()*10.0)/10.0 + ", " 
					+ Math.round(getY()*10.0)/10.0
					+ ") color=[" + ColorUtil.red(color) + ","
					+ ColorUtil.green(color) + "," 
					+ ColorUtil.blue(color) + "]";
		return desc;
	}
}
