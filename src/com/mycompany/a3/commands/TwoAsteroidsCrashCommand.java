package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class TwoAsteroidsCrashCommand extends Command
{
	private GameWorld gw;
	
	public TwoAsteroidsCrashCommand(GameWorld gw)
	{
		super("Asteroids Collision");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.twoAsteroidsCrash();
			System.out.println("Asteroids Collision command~~~~~~~~~~~~~");
		}
	}
}
