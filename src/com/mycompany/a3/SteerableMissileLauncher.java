package com.mycompany.a3;

public class SteerableMissileLauncher extends MissileLauncher implements ISteerable
{
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
				System.out.println("Steerable missile direction is 'r' or 'l', turn range 0-359");
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
}
