package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class ShipTurnLeftCommand extends Command
{
	private GameWorld gw;
	
	public ShipTurnLeftCommand(GameWorld gw)
	{
		super("Turn Left");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.turnPlayerShipLeft(30);
			System.out.println("Rotate Gun Left command~~~~~~~~~~~~~~~~~");
		}
	}
}
