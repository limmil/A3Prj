package com.mycompany.a3.commands;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class NewGameCommand extends Command
{
	private GameWorld gw;
	
	public NewGameCommand(GameWorld gw)
	{
		super("NEW");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.newGame();
		System.out.println("NEW command~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
