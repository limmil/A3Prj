package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class QuitCommand extends Command
{
	private GameWorld gw;
	
	public QuitCommand(GameWorld gw)
	{
		super("QUIT");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getKeyEvent() != -1)
		{
			gw.quitGame();
			System.out.println("Quit Game command~~~~~~~~~~~~~~~~~~~~~~~");
		}
	}
}
