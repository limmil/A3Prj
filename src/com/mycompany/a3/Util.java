package com.mycompany.a3;

import java.util.Random;

public class Util
{
	// creates random int
	public static int randInt(int min, int max)
	{
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
