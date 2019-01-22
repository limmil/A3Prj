package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class LaunchNPSMissileCommand extends Command
{
	private GameWorld gw;
	
	public LaunchNPSMissileCommand(GameWorld gw)
	{
		super("NPS Missile");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1) 
		{
			gw.launchNPSMissile();
			System.out.println("NPS Missile command~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
