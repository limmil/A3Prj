package com.mycompany.a3;

public abstract class MoveableGameObject extends GameObject implements IMoveable
{
	private int speed = Util.randInt(1, 10);
	private int direction = Util.randInt(0, 359);
	private double deltaX, deltaY;
	
	// speed getters and setters
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public int getSpeed()
	{
		return speed;
	}
	
	// direction getters and setters
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	public int getDirection()
	{
		return direction;
	}
	public double getDeltaX()
	{
		return deltaX;
	}
	public double getDeltaY()
	{
		return deltaY;
	}
	
	public void move()
	{
		double dir = Math.toRadians(direction) + Math.PI/2;
		deltaX = Math.cos(dir) * speed;
		deltaY = Math.sin(dir) * speed;
		super.setX(super.getX() + deltaX);
		super.setY(super.getY() + deltaY);
	}
}
