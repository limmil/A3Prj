package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class NPSKillsPSCommand extends Command
{
	private GameWorld gw;
	
	public NPSKillsPSCommand(GameWorld gw)
	{
		super("Kill by Non-PlayerShip");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1) 
		{
			gw.npsKillsPS();
			System.out.println("Kill by Non-PlayerShip command~~~~~~~~~~");
		}
	}
}
