package com.mycompany.a3;


public abstract class FixGameObject extends GameObject
{
	private static int uniqueId = 1;
	private int id;
	
	public FixGameObject(int width, int height)
	{
		id = uniqueId;
		uniqueId++;
		
		// random location in map view
		super.setX(Util.randInt(0, width-80));
		super.setY(Util.randInt(0, height-80));
	}
	
	public int getId()
	{
		return id;
	}
	
	// blocking parent methods
	public void setX(double location)
	{
		System.out.println("stop it");
	}
	public void setY(double location)
	{
		System.out.println("impossible");
	}
	
}
