package com.mycompany.a3;

import java.util.Observable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;

public class GameWorld extends Observable implements IGameWorld
{
	private boolean psExist;
	private boolean soundOn;
	private boolean gameOver;
	private int score;
	private int lives;
	private int time;
	private int width;
	private int height;
	private boolean spaceStation;
	
	private Game game;
	private Sound shoot, explode, gameoverSound;
	
	//All state variables are stored here
	private GameCollection store;
	
	public GameWorld(Game game)
	{
		this.game = game;
		init();
		soundOn = true;
		shoot = new Sound("shoot.wav");
		explode = new Sound("asteroid.wav");
		gameoverSound = new Sound("loser.wav");
	}
	
	public void init() 
	{
		game.setTitle("Asteroids");
		store = new GameCollection();
		psExist = false;
		gameOver = false;
		score = 0;
		lives = 3;
		time = 0;
		// spawn 3 stations then switch to false;
		spaceStation = true;
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public int getPlayerScore()
	{
		return score;
	}
	
	public int getPlayerLives()
	{
		return lives;
	}

	public int getPlayerMissileCount()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				return ((PlayerShip) obj).getMissileCount();
			}
		}
		return 0;
	}

	public boolean getPlayerSoundOn()
	{
		return soundOn;
	}
	
	public int getPlayerTime()
	{
		return time;
	}
	
	public GameCollection getGameObjects()
	{
		return store;
	}
	
	public void toggleSound(boolean state)
	{
		soundOn = state;
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
		System.out.println("state: "+ soundOn);
	}
	
	public void addNewAsteroid()
	{
		Asteroid asteroid = new Asteroid(width, height);
		asteroid.setColor(ColorUtil.GRAY);
		store.add(asteroid);
		System.out.println("A new ASTEROID has been created.");
		
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void addNPS()
	{
		NonPlayerShip nps = new NonPlayerShip(width, height);
		nps.setColor(ColorUtil.rgb(255,0,0));
		store.add(nps);
		System.out.println("A new NONPLAYERSHIP has been created.");
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void addSpaceStation()
	{
		SpaceStation ss = new SpaceStation(width, height);
		ss.setColor(ColorUtil.GREEN);
		store.add(ss);
		System.out.println("A new SPACESTATION has been created.");
	}
	
	public void addPlayerShip()
	{
		if (psExist && !gameOver)
		{
			System.out.println("PLAYERSHIP already exists.");
		}
		else
		{
			if(!gameOver)
			{
				PlayerShip ps = new PlayerShip(this, width, height);
				ps.setColor(ColorUtil.BLUE);
				store.add(ps);
				psExist = true;
				System.out.println("A new PLAYERSHIP has been created.");
			}
			else
			{
				System.out.println("GAME OVER.");
			}
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void increaseSpeed()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				// increase speed by 1
				psObj.setSpeed(psObj.getSpeed() + 1);
				System.out.println("PlayerShip speed increased.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void decreaseSpeed()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				// decrease speed by 1
				psObj.setSpeed(psObj.getSpeed() - 1);
				System.out.println("PlayerShip speed decreased.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void turnPlayerShipLeft(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				psObj.steerLeft(turnAmt);
				System.out.println("PlayerShip turned left.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void turnPlayerShipRight(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.steerRight(turnAmt);
				System.out.println("PlayerShip turned right.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void turnMissileLauncherLeft(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.steerLauncherLeft(turnAmt);
				System.out.println("Missile Launcher turned left.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
	}
	
	public void turnMissileLauncherRight(int turnAmt)
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.steerLauncherRight(turnAmt);
				System.out.println("Missile Launcher turned left.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
	}
	
	public void launchPSMissile()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				if (psObj.hasMissile())
				{
					psObj.fire();
					int launcherDirection = psObj.getLauncherDir();
					PlayerMissile missile = new PlayerMissile(this, launcherDirection, psObj.getX(), psObj.getY(), psObj.getDeltaX(), psObj.getDeltaY());
					missile.setColor(ColorUtil.BLUE);
					missile.setFuelLevel(25);
					store.add(missile);
					if(soundOn)
					{
						shoot.play();
					}
					System.out.println("Launching PlayerShip missile.");
					break;
				}
				else
				{
					System.out.println("Player ship out of missile.");
					break;
				}
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void launchNPSMissile()
	{
		IIterator it = store.getIterator();
		Boolean npsExist = false;
		
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof NonPlayerShip)
			{
				npsExist = true;
				NonPlayerShip npsObj= (NonPlayerShip) obj;
				
				if (npsObj.hasMissile())
				{
					npsObj.fire();
					int launcherDirection = npsObj.getLauncher().getDirection();
					NonPlayerMissile missile = new NonPlayerMissile(launcherDirection, npsObj.getX(), npsObj.getY());
					missile.setColor(ColorUtil.rgb(255, 0, 0));
					store.add(missile);
					System.out.println("Launching Non-PlayerShip missile.");
					break;
				}
				else
				{
					System.out.println("Non-player ship out of missile.");
					break;
				}
			}
		}
		if (!npsExist)
		{
			System.out.println("NonPlayerShip does not exist.");
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void jump()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.setX(width/2);
				psObj.setY(height/2);
				System.out.println("PlayerShip jumped back to center.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void reloadMissiles()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj = (PlayerShip) obj;
				psObj.reload();
				System.out.println("Reloading PlayerShip missile.");
				break;
			}
		}
		if (!psExist)
		{
			System.out.println("PlayerShip does not exist.");
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void killAsteroid()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Missile)
			{
				store.remove(it.getIndex());
				
				it = store.getIterator();
				while (it.hasNext())
				{
					Object o = it.getNext();
					if (o instanceof Asteroid)
					{
						store.remove(it.getIndex());
						score+=1;
						System.out.println("Asteroid is destoried by missile.");
						break;
					}
				}
				break;
			}
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void killNPS()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Missile)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof NonPlayerShip)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		
		System.out.println("NonPlayerShip is destoried by missile.");
		score += 1;
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void npsKillsPS()
	{
		if (lives > 1)
		{
			IIterator it = store.getIterator();
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof Missile)
				{
					store.remove(it.getIndex());
					break;
				}
			}
			it = store.getIterator();
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof PlayerShip)
				{
					store.remove(it.getIndex());
					psExist = false;
					lives -= 1;
					break;
					
				}
			}
			System.out.println("Non-PlayerShip killed PlayerShip.");
		}
		else
		{
			this.notifyObservers(new GameWorldProxy(this));
			this.setChanged();
			gameOver();
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void asteroidCrashedPS()
	{
		if(lives > 1)
		{
			int asteroidLoc = 0;
			int asteroidCount = 0;
			
			IIterator it = store.getIterator();
			if (!psExist)
			{
				System.out.println("PlayerShip does not exist.");
			}
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof Asteroid)
				{
					// get first asteroid's location
					asteroidLoc = it.getIndex();
					asteroidCount++;
				}
				if (psExist && (asteroidCount > 0))
				{
					// remove first asteroid found
					store.remove(asteroidLoc);
					// start a new while loop to remove playership before break
					it = store.getIterator();
					while (it.hasNext())
					{
						obj = it.getNext();
						if (obj instanceof PlayerShip)
						{
							store.remove(it.getIndex());
							psExist = false;
							lives -= 1;
							System.out.println("PlayerShip crashed into Asteroid.");
							// finish inside loop
							break;
						}
					}
					// finish outside loop
					break;
				}
			}
		}
		else
		{
			/*
			this.setChanged();
			this.notifyObservers(new GameWorldProxy(this));
			*/
			gameOver();
			return;
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
		
	}
	
	public void playerShipHitsNPS()
	{
		if (lives > 1)
		{
			IIterator it = store.getIterator();
			while (it.hasNext())
			{
				Object obj = it.getNext();
				if (obj instanceof NonPlayerShip)
				{
					store.remove(it.getIndex());
					lives -= 1;
					System.out.println("PlayerShip crashed into Non-PlayerShip.");
					break;
				}
			}
		}
		else
		{
			System.out.println("PlayerShip crashed into Non-PlayerShip.");
			this.notifyObservers(new GameWorldProxy(this));
			this.setChanged();
			gameOver();
			return;
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void twoAsteroidsCrash()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Asteroid)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		
		it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Asteroid)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		System.out.println("Two Asteroids crashed into each other.");
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void asteroidHitsNPS()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof Asteroid)
			{
				store.remove(it.getIndex());
				break;
			}
		}
		it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof NonPlayerShip)
			{
				store.remove(it.getIndex());
				break;
				
			}
		}
		System.out.println("Asteroid destoried a Non-PlayerShip.");
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void tick()
	{
		logic();
		
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof MoveableGameObject)
			{
				MoveableGameObject mgObj = (MoveableGameObject) obj;
				mgObj.move();
				if(obj instanceof NonPlayerShip)
				{
					NonPlayerShip npsObj = (NonPlayerShip) obj;
					if(npsObj.fireReady())
					{
						npsObj.fire();
						int launcherDirection = npsObj.getDirection();
						NonPlayerMissile missile = new NonPlayerMissile(launcherDirection, npsObj.getX(), npsObj.getY());
						missile.setColor(ColorUtil.rgb(255, 0, 0));
						store.add(missile);
					}
				}
			
			}
			if (obj instanceof SpaceStation)
			{
				SpaceStation ssObj = (SpaceStation) obj;
				if ( (time % ssObj.getBlinkRate()) == 0 )
				{
					ssObj.setLightOn(true);
				}
				else
				{
					ssObj.setLightOn(false);
				}
			}
			
			if (obj instanceof Missile)
			{
				Missile mObj = (Missile) obj;
				mObj.setFuelLevel(mObj.getFuelLevel() - 2);
				if (mObj.getFuelLevel() <= 0)
				{
					store.remove(it.getIndex());
					// changing pointer after removing
					it.setIndex(it.getIndex()-1);
				}
			}
			
			if (obj instanceof ICollider)
			{
				ICollider curObj = (ICollider) obj;
				if(!curObj.isAlive())
				{
					store.remove(it.getIndex());
					it.setIndex(it.getIndex()-1);
				}
			}
		}
		
		time += 1;
		
		// check for collision after moving
		IIterator iter = store.getIterator();
		while(iter.hasNext())
		{
			ICollider curObj = (ICollider) iter.getNext();
			// check if this object collides with other objects
			IIterator iter2 = store.getIterator();
			iter2.setIndex(iter.getIndex());
			while(iter2.hasNext())
			{
				ICollider otherObj = (ICollider) iter2.getNext();
				if(otherObj != curObj)
				{
					if(curObj.collidesWith(otherObj))
					{
						curObj.handleCollision(otherObj);
						otherObj.handleCollision(curObj);
					}
				}
			}
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void printDisplay()
	{
		int missile = 0;
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				missile = psObj.getMissileCount();
			}
		}
		System.out.println("Score point: "+ score + "\n"
				+ "Missile count: " + missile + "\n"
				+ "Elaspsed time: " + time + "\n"
				+ "Lives left: " + lives);
	}
	
	public void printMap()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if (obj instanceof PlayerShip)
			{
				PlayerShip psObj= (PlayerShip) obj;
				System.out.println( "Player Ship: " + psObj.toString() );
			}
			if (obj instanceof PlayerMissile)
			{
				PlayerMissile pmObj= (PlayerMissile) obj;
				System.out.println( "PlayerMissile: " + pmObj.toString() );
			}
			if (obj instanceof NonPlayerMissile)
			{
				NonPlayerMissile npmObj= (NonPlayerMissile) obj;
				System.out.println( "NonPlayerMissile: " + npmObj.toString() );
			}
			if (obj instanceof NonPlayerShip)
			{
				NonPlayerShip npsObj= (NonPlayerShip) obj;
				System.out.println( "Non-Player Ship: " + npsObj.toString());
			}
			if (obj instanceof Asteroid)
			{
				Asteroid aObj= (Asteroid) obj;
				System.out.println( "Asteroid: " + aObj.toString() );
			}
			if (obj instanceof SpaceStation)
			{
				SpaceStation ssObj= (SpaceStation) obj;
				System.out.println("Station: " + ssObj.toString() );
			}
		}
		System.out.println();
	}
	
	public void quitGame()
	{
		game.setTitle("Closing Game");
		boolean quit = Dialog.show("Confirm Quit",
				"Are you sure you want to quit?", 
				"YES", "NO");
		if (quit)
		{
			Display.getInstance().exitApplication();
		}
		else
		{
			game.setTitle("Asteroids");
		}
		game.show();
	}
	
	public void gameOver()
	{
		game.pauseTimer();
		if(soundOn)
		{
			gameoverSound.play();
		}
		game.setTitle("Game Over");
		boolean flag = Dialog.show("OUT OF LIVES",
				"Do you want to try again?\nScore: "+ score, 
				"YES", "NO");
		if (flag)
		{
			init();
			System.out.println("====================================");
			System.out.println("============= NEW GAME =============");
			System.out.println("====================================");
			game.startTimer();
		}
		else
		{
			game.startTimer();
			game.setTitle("Asteroids");
			lives = 0;
			gameOver = true;
		}
		
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
		game.show();
	}
	
	public void newGame()
	{
		boolean flag = Dialog.show("New Game",
				"Do you want to start a new game?", 
				"YES", "NO");
		if (flag)
		{
			init();
			System.out.println("====================================");
			System.out.println("============= NEW GAME =============");
			System.out.println("====================================");
		}
	}
	
	public void about()
	{
		Dialog.show("About",
		"Creator: Minquan Li\n" + "Course: CSC133\n" 
		+ "Fall 2018\n" + "Asteroids v1.0",
		"Ok",null);
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public void notifyAndChange()
	{
		this.notifyObservers(new GameWorldProxy(this));
		this.setChanged();
	}
	
	public void deadShip()
	{
		psExist = false;
		lives = lives - 1;
		if(lives < 1)
		{
			gameOver();
		}
	}
	
	public void scorePoint()
	{
		if(soundOn)
		{
			explode.play();
		}
		score += 1;
	}
	
	public boolean psExist()
	{
		return psExist;
	}
	
	public void reFuel()
	{
		IIterator it = store.getIterator();
		while (it.hasNext())
		{
			Object obj = it.getNext();
			if(obj instanceof ISelectable && obj instanceof Missile)
			{
				ISelectable selectable = (ISelectable) obj;
				if(selectable.isSelected())
				{
					Missile mObj = (Missile) obj;
					mObj.setMaxFuel();
				}
			}
		}
	}
	
	public void logic()
	{
		if(spaceStation)
		{
			addSpaceStation();
			addSpaceStation();
			addSpaceStation();
			spaceStation = false;
		}
		if(Util.randInt(1, 100)==3)
		{
			addNPS();
		}
		if(Util.randInt(1, 20)==3)
		{
			addNewAsteroid();
		}
		if(!psExist && !gameOver)
		{
			addPlayerShip();
		}
	}

}
