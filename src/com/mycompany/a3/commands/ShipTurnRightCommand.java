package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class ShipTurnRightCommand extends Command
{
	private GameWorld gw;
	
	public ShipTurnRightCommand(GameWorld gw)
	{
		super("Turn Right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.turnPlayerShipRight(30);
			System.out.println("Turn Right command~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
