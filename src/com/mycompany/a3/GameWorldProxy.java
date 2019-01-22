package com.mycompany.a3;

import java.util.Observable;

public class GameWorldProxy extends Observable implements IGameWorld
{
	GameWorld gw;
	
	public GameWorldProxy(GameWorld gw)
	{
		this.gw = gw;
	}
	
	public int getPlayerScore()
	{
		return gw.getPlayerScore();
	}
	
	public int getPlayerLives()
	{
		return gw.getPlayerLives();
	}
	
	public int getPlayerMissileCount()
	{
		return gw.getPlayerMissileCount();
	}
	
	public int getPlayerTime()
	{
		return gw.getPlayerTime();
	}
	
	public boolean getPlayerSoundOn()
	{
		return gw.getPlayerSoundOn();
	}
	
}
