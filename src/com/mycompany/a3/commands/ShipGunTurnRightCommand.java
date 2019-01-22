package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class ShipGunTurnRightCommand extends Command
{
	private GameWorld gw;
	
	public ShipGunTurnRightCommand(GameWorld gw)
	{
		super("Rotate Gun Right");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.turnMissileLauncherRight(15);
			System.out.println("Rotate Gun Left command~~~~~~~~~~~~~~~~~");
		}
	}
}
