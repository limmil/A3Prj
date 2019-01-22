package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.commands.*;


public class Game extends Form implements Runnable
{
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private boolean checkBoxState;
	private boolean paused;
	private CheckBox soundCheckBox;
	private BGSound bgSound;
	private UITimer timer;
	private Toolbar myToolbar;
	private Container myContainer;
	private Label commandTextLabel;
	private Command myAddAsteroid, myAddSpaceStation, myAddPlayerShip, myIncSpeed, 
					myDecSpeed, myTurnLeft, myTurnRight, myAddMissile, myAddJump, 
					myAddRotateGunLeft, myAddRotateGunRight, myAddRefuel, 
					myPauseCommand, myAddQuit;
	private Button addAsteroid, addSpaceStation, addPlayerShip, increaseSpeed, 
					decreaseSpeed, turnLeft, turnRight, addMissile, addJump,
					addRotateGunLeft, addRotateGunRight, addRefuel, addPause, addQuit;
	
	
	
	
	public Game()
	{
		init();
	}
	
	private void init()
	{
		// make game world
		gw = new GameWorld(this);
		// add toolbar
		addToolBar();
		// set layout
		setLayout(new BorderLayout());
		// add buttons
		addButtons();
		// add views
		mv = new MapView(gw);
		pv = new PointsView(gw);
		// set observer
		gw.addObserver(mv);
		gw.addObserver(pv);
		// setup layout
		add(BorderLayout.NORTH, pv);
		add(BorderLayout.CENTER, mv);
		//create timer and provide a runnable
		timer = new UITimer(this);
		//make the timer tick 29 times/second and bind it to this form
		startTimer();
		
		this.show();
		
		// set width and height
		int width = mv.getWidth();
		int height = mv.getHeight();
		gw.setWidth(width);
		gw.setHeight(height);
		System.out.println("map width:" + width);
		System.out.println("map height:" + height);
		
		// sounds
		bgSound = new BGSound("BG_Automation.wav");
		checkBoxState = true;
		bgSound.play();
		
		// select object function
		select();
		
	}
	
	private void addToolBar()
	{
		myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.setTitle("Asteroids");
		
		
		// overflow menu==================================================
		myToolbar.addCommandToOverflowMenu(new NewGameCommand(gw));
		myToolbar.addCommandToOverflowMenu(new SaveGameCommand(gw));
		myToolbar.addCommandToOverflowMenu(new UndoGameCommand(gw));
		myToolbar.addCommandToOverflowMenu(new AboutGameCommand(gw));
		myToolbar.addCommandToOverflowMenu(new QuitGameCommand(gw));
		
		// side menu======================================================
		// other commands
		myToolbar.addCommandToSideMenu(new AddAsteroidCommand(gw));
		myToolbar.addCommandToSideMenu(new AddSpaceStationCommand(gw));
		myToolbar.addCommandToSideMenu(new AddPlayerShipCommand(gw));
		myToolbar.addCommandToSideMenu(new AddNPSCommand(gw));
		myToolbar.addCommandToSideMenu(new ReloadCommand(gw));
		myToolbar.addCommandToSideMenu(new KillAsteroidCommand(gw));
		myToolbar.addCommandToSideMenu(new AsteroidCrashedPSCommand(gw));
		myToolbar.addCommandToSideMenu(new TwoAsteroidsCrashCommand(gw));
		
		// sound checkbox
		soundCheckBox = new CheckBox("SOUND");
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.getUnselectedStyle().setBgColor(ColorUtil.rgb(51,153,255));
		soundCheckBox.setSelected(gw.getPlayerSoundOn());
		myToolbar.addComponentToSideMenu(soundCheckBox);
		ToggleSoundCommand myAddSound = new ToggleSoundCommand(gw, this);
		soundCheckBox.setCommand(myAddSound);
		
		// quit command
		myToolbar.addCommandToSideMenu(new QuitCommand(gw));
	}
	
	private void addButtons()
	{
		myContainer = new Container(new GridLayout(1,1));
		
		commandTextLabel = new Label("Commands");
		myContainer.add(commandTextLabel);
		
		addAsteroid = new Button("Add Asteroid");
		setButton(addAsteroid, myContainer);
		myAddAsteroid = new AddAsteroidCommand(gw);
		addAsteroid.setCommand(myAddAsteroid);
		this.addKeyListener('a', myAddAsteroid);
		
		addSpaceStation = new Button("Add Space Station");
		setButton(addSpaceStation, myContainer);
		myAddSpaceStation = new AddSpaceStationCommand(gw);
		addSpaceStation.setCommand(myAddSpaceStation);
		this.addKeyListener('b', myAddSpaceStation);
		
		addPlayerShip = new Button ("Add Player Ship");
		setButton(addPlayerShip, myContainer);
		myAddPlayerShip = new AddPlayerShipCommand(gw);
		addPlayerShip.setCommand(myAddPlayerShip);
		this.addKeyListener('s', myAddPlayerShip);
		
		increaseSpeed = new Button ("Increase Speed");
		setButton(increaseSpeed, myContainer);
		myIncSpeed = new IncreaseSpeedCommand(gw);
		increaseSpeed.setCommand(myIncSpeed);
		this.addKeyListener(-91, myIncSpeed);
		
		decreaseSpeed = new Button ("Decrease Speed");
		setButton(decreaseSpeed, myContainer);
		myDecSpeed = new DecreaseSpeedCommand(gw);
		decreaseSpeed.setCommand(myDecSpeed);
		this.addKeyListener(-92, myDecSpeed);
		
		turnLeft = new Button ("Turn Left");
		setButton(turnLeft, myContainer);
		myTurnLeft = new ShipTurnLeftCommand(gw);
		turnLeft.setCommand(myTurnLeft);
		this.addKeyListener(-93, myTurnLeft);
		
		turnRight = new Button ("Turn Right");
		setButton(turnRight, myContainer);
		myTurnRight = new ShipTurnRightCommand(gw);
		turnRight.setCommand(myTurnRight);
		this.addKeyListener(-94, myTurnRight);
		
		addMissile = new Button ("Shoot Missile");
		setButton(addMissile, myContainer);
		myAddMissile = new PlayerShipFireCommand(gw);
		addMissile.setCommand(myAddMissile);
		this.addKeyListener('f', myAddMissile);
		
		addJump = new Button("Jump HyperSpace");
		setButton(addJump, myContainer);
		myAddJump = new JumpCommand(gw);
		addJump.setCommand(myAddJump);
		this.addKeyListener('j', myAddJump);
		
		addRotateGunLeft = new Button("Roatate Gun Left");
		setButton(addRotateGunLeft, myContainer);
		myAddRotateGunLeft = new ShipGunTurnLeftCommand(gw);
		addRotateGunLeft.setCommand(myAddRotateGunLeft);
		this.addKeyListener('d', myAddRotateGunLeft);
		
		addRotateGunRight = new Button("Roatate Gun Right");
		setButton(addRotateGunRight, myContainer);
		myAddRotateGunRight = new ShipGunTurnRightCommand(gw);
		addRotateGunRight.setCommand(myAddRotateGunRight);
		this.addKeyListener('g', myAddRotateGunRight);
		
		addRefuel = new Button("Refuel");
		setButton(addRefuel, myContainer);
		myAddRefuel = new RefuelCommand(gw);
		addRefuel.setCommand(myAddRefuel);
		//this.addKeyListener('n', myAddRefuel);
		addRefuel.setEnabled(false);
		addRefuel.getAllStyles().setBgTransparency(127);
		
		addPause = new Button("PAUSE");
		setButton(addPause, myContainer);
		myPauseCommand = new PauseCommand(this);
		addPause.setCommand(myPauseCommand);
		this.addKeyListener('x', myPauseCommand);
		
		addQuit = new Button("Quit");
		setButton(addQuit, myContainer);
		myAddQuit = new QuitCommand(gw);
		addQuit.setCommand(myAddQuit);
		this.addKeyListener('Q', myAddQuit);
		
		
		add(BorderLayout.WEST, myContainer);
	}
	
	private void setButton(Button button, Container container)
	{
		button.getAllStyles().setBgTransparency(255);
		button.getUnselectedStyle().setBgColor(ColorUtil.rgb(51,153,255));
		button.getAllStyles().setFgColor(ColorUtil.BLACK);
		button.getAllStyles().setPadding(RIGHT, 10);
		button.getAllStyles().setPadding(LEFT, 10);
		button.getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.WHITE));
		container.add(button);
	}
	
	public boolean getCheckBox()
	{
		return checkBoxState;
	}
	
	public void setCheckBox()
	{
		checkBoxState = soundCheckBox.isSelected();
		if(checkBoxState)
		{
			bgSound.play();
		}
		else
		{
			bgSound.pause();
		}
	}
	
	public void pauseTimer()
	{
		timer.cancel();
		paused = true;
		addPause.setText("PLAY");
		// enable refuel
		addRefuel.setEnabled(true);
		addRefuel.getAllStyles().setBgTransparency(255);
		// disable buttons
		addAsteroid.setEnabled(false);
		addAsteroid.getAllStyles().setBgTransparency(127);
		
		addSpaceStation.setEnabled(false);
		addSpaceStation.getAllStyles().setBgTransparency(127);
		
		addPlayerShip.setEnabled(false);
		addPlayerShip.getAllStyles().setBgTransparency(127);
		
		increaseSpeed.setEnabled(false);
		increaseSpeed.getAllStyles().setBgTransparency(127);
		
		decreaseSpeed.setEnabled(false);
		decreaseSpeed.getAllStyles().setBgTransparency(127);
		
		turnLeft.setEnabled(false);
		turnLeft.getAllStyles().setBgTransparency(127);
		
		turnRight.setEnabled(false);
		turnRight.getAllStyles().setBgTransparency(127);
		
		addMissile.setEnabled(false);
		addMissile.getAllStyles().setBgTransparency(127);
		
		addJump.setEnabled(false);
		addJump.getAllStyles().setBgTransparency(127);
		
		addRotateGunLeft.setEnabled(false);
		addRotateGunLeft.getAllStyles().setBgTransparency(127);
		
		addRotateGunRight.setEnabled(false);
		addRotateGunRight.getAllStyles().setBgTransparency(127);
		
		addQuit.setEnabled(false);
		addQuit.getAllStyles().setBgTransparency(127);
		
		// disable key bindings
		this.removeKeyListener('a', myAddAsteroid);
		this.removeKeyListener('b', myAddSpaceStation);
		this.removeKeyListener('s', myAddPlayerShip);
		this.removeKeyListener(-91, myIncSpeed);
		this.removeKeyListener(-92, myDecSpeed);
		this.removeKeyListener(-93, myTurnLeft);
		this.removeKeyListener(-94, myTurnRight);
		this.removeKeyListener('f', myAddMissile);
		this.removeKeyListener('j', myAddJump);
		this.removeKeyListener('d', myAddRotateGunLeft);
		this.removeKeyListener('g', myAddRotateGunRight);
		this.removeKeyListener('Q', myAddQuit);
		
		// disable toolbar
		myToolbar.setEnabled(false);
		myToolbar.getDisabledStyle().setBgColor(ColorUtil.GRAY);
		
	}
	
	public void startTimer()
	{
		timer.schedule(34, true, this);
		paused = false;
		addPause.setText("PAUSE");
		// disable refuel
		addRefuel.setEnabled(false);
		addRefuel.getAllStyles().setBgTransparency(127);
		// enable buttons
		addAsteroid.setEnabled(true);
		addAsteroid.getAllStyles().setBgTransparency(255);
		
		addSpaceStation.setEnabled(true);
		addSpaceStation.getAllStyles().setBgTransparency(255);
		
		addPlayerShip.setEnabled(true);
		addPlayerShip.getAllStyles().setBgTransparency(255);
		
		increaseSpeed.setEnabled(true);
		increaseSpeed.getAllStyles().setBgTransparency(255);
		
		decreaseSpeed.setEnabled(true);
		decreaseSpeed.getAllStyles().setBgTransparency(255);
		
		turnLeft.setEnabled(true);
		turnLeft.getAllStyles().setBgTransparency(255);
		
		turnRight.setEnabled(true);
		turnRight.getAllStyles().setBgTransparency(255);
		
		addMissile.setEnabled(true);
		addMissile.getAllStyles().setBgTransparency(255);
		
		addJump.setEnabled(true);
		addJump.getAllStyles().setBgTransparency(255);
		
		addRotateGunLeft.setEnabled(true);
		addRotateGunLeft.getAllStyles().setBgTransparency(255);
		
		addRotateGunRight.setEnabled(true);
		addRotateGunRight.getAllStyles().setBgTransparency(255);
		
		addQuit.setEnabled(true);
		addQuit.getAllStyles().setBgTransparency(255);
		
		// disable key bindings
		this.addKeyListener('a', myAddAsteroid);
		this.addKeyListener('b', myAddSpaceStation);
		this.addKeyListener('s', myAddPlayerShip);
		this.addKeyListener(-91, myIncSpeed);
		this.addKeyListener(-92, myDecSpeed);
		this.addKeyListener(-93, myTurnLeft);
		this.addKeyListener(-94, myTurnRight);
		this.addKeyListener('f', myAddMissile);
		this.addKeyListener('j', myAddJump);
		this.addKeyListener('d', myAddRotateGunLeft);
		this.addKeyListener('g', myAddRotateGunRight);
		this.addKeyListener('Q', myAddQuit);
		
		// enable toolbar
		myToolbar.setEnabled(true);
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public void select()
	{
		// action listener
		mv.addPointerPressedListener(new ActionListener() 
		{
			public void actionPerformed (ActionEvent evt)
			{
				Point p = new Point();
				p.setX(evt.getX()-mv.getX());
				p.setY(-(evt.getY()-mv.getAbsoluteY()-mv.getHeight()));
				
				if(paused)
				{
					IIterator it = gw.getGameObjects().getIterator();
					while(it.hasNext())
					{
						Object obj = it.getNext();
						
						if(obj instanceof ISelectable)
						{
							ISelectable selected = (ISelectable) obj;
							selected.isSelected(p);
							mv.repaint();
						}
						
					}
				}
			}
		});
	}

	public void run()
	{
		gw.tick();
	}
	
	
	
}
