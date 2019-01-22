package com.mycompany.a3;

public class Missile extends MoveableGameObject
{
	private int fuelLevel = 10;
	private final int MISSILE_SPEED = 12;
	private double newDelX, newDelY;
	private int speed;
	private double deltaX, deltaY;
	
	public Missile(int direction, double x, double y, double delX, double delY)
	{
		// match launcher direction when created
		super.setDirection(direction);
		// set speed
		super.setSpeed(MISSILE_SPEED);
		// set starting location
		super.setX(x);
		super.setY(y);
		newDelX = delX;
		newDelY = delY;
	}
	
	public Missile(GameWorld gw, int direction, double x, double y, double delX, double delY)
	{
		// match launcher direction when created
		super.setDirection(direction);
		// set speed
		super.setSpeed(MISSILE_SPEED);
		// set starting location
		super.setX(x);
		super.setY(y);
		newDelX = delX;
		newDelY = delY;
	}
	
	public Missile(int direction, double x, double y)
	{
		// match launcher direction when created
		super.setDirection(direction);
		// set speed
		super.setSpeed(MISSILE_SPEED);
		// set starting location
		super.setX(x);
		super.setY(y);
		newDelX = 0;
		newDelY = 0;
	}
	
	//fuelLevel getters and setters
	public void setFuelLevel(int fuelLevel)
	{
		// fuelLevel is a positive number only
		if(fuelLevel>=-1 && fuelLevel<=100)
		{
			this.fuelLevel = fuelLevel;
		}
	}
	
	public int getFuelLevel()
	{
		return fuelLevel;
	}
	
	public void setMaxFuel()
	{
		setFuelLevel(100);
	}
	
	// blocking parent method
	public void setSpeed(int speed)
	{
		System.out.println("nice try guy");
	}
	public void setDirection(int direction)
	{
		System.out.println("oops, don't do that");
	}
	
	public void move()
	{
		double dir = Math.toRadians(super.getDirection()) + Math.PI/2;
		speed = super.getSpeed();
		deltaX = (Math.cos(dir) * speed) + newDelX;
		deltaY = (Math.sin(dir) * speed) + newDelY;
		super.setX(super.getX() + deltaX);
		super.setY(super.getY() + deltaY);
	}
	
	public String toString()
	{
		String parentDesc = super.toString();
		String desc = " Speed=" + super.getSpeed() + " Direction="
				    + super.getDirection() + " Fuel=" + fuelLevel;
		return parentDesc + desc;
	}
}
