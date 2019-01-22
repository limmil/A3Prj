package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

public class PointsView extends Container implements Observer
{
	private Label pointsValueLabel;
	private Label livesValueLabel;
	private Label ammoValueLabel;
	private Label soundOnValueLabel;
	private Label timeValueLabel;
	
	public PointsView(GameWorld gw)
	{
		int score = gw.getPlayerScore();
		int lives = gw.getPlayerLives();
		int ammo = gw.getPlayerMissileCount();
		int time = gw.getPlayerTime();
		boolean soundOn = gw.getPlayerSoundOn();
		
		// set background color and border
		getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.WHITE));
		getStyle().setBgColor(ColorUtil.rgb(255, 255, 255));
		// initializing text labels
		Label pointsTextLabel = new Label("Points:");
		pointsValueLabel = new Label("XXX");
		setLabelFormat(pointsValueLabel, score);
		
		Label livesTextLabel = new Label("Lives:");
		livesTextLabel.getAllStyles().setPadding(LEFT, 10);
		livesValueLabel = new Label("XXX");
		setLabelFormat(livesValueLabel, lives);
		
		Label ammoTextLabel = new Label("Ammo:");
		ammoTextLabel.getAllStyles().setPadding(LEFT, 10);
		ammoValueLabel = new Label("XXX");
		setLabelFormat(ammoValueLabel, ammo);
		
		Label soundOnTextLabel = new Label("Sound:");
		soundOnTextLabel.getAllStyles().setPadding(LEFT, 10);
		soundOnValueLabel = new Label("XXX");
		setLabelFormat(soundOnValueLabel, soundOn);
		
		Label timeTextLabel = new Label("Elapsed Time:");
		timeTextLabel.getAllStyles().setPadding(LEFT, 10);
		timeValueLabel = new Label("XXX");
		setLabelFormat(timeValueLabel, time);
		
		
		// set color
		pointsTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		livesTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		ammoTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		soundOnTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		timeTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		
		// adding boxlayout
		Container myContainer = new Container();
		myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		// adding labels in container
		myContainer.add(pointsTextLabel);
		myContainer.add(pointsValueLabel);
		myContainer.add(livesTextLabel);
		myContainer.add(livesValueLabel);
		myContainer.add(ammoTextLabel);
		myContainer.add(ammoValueLabel);
		myContainer.add(soundOnTextLabel);
		myContainer.add(soundOnValueLabel);
		myContainer.add(timeTextLabel);
		myContainer.add(timeValueLabel);
		this.add(myContainer);
	}
	
	public void update(Observable observable, Object data)
	{
		IGameWorld gw = (IGameWorld) data;
		
		int score = gw.getPlayerScore();
		int lives = gw.getPlayerLives();
		int ammo = gw.getPlayerMissileCount();
		int time = gw.getPlayerTime();
		boolean soundOn = gw.getPlayerSoundOn();
		
		setLabelFormat(pointsValueLabel, score);
		setLabelFormat(livesValueLabel, lives);
		setLabelFormat(ammoValueLabel, ammo);
		setLabelFormat(timeValueLabel, time);
		setLabelFormat(soundOnValueLabel, soundOn);
		
		this.repaint();
		
	}
	
	
	
	
	private void setLabelFormat(Label label, int value)
	{
		label.setText("" + (value > 99 ? "" : "0") 
                + (value > 9 ? "" : "0") 
                + value);
	}
	
	private void setLabelFormat(Label label, boolean value)
	{
		if(value) 
		{
			label.setText("ON");
			label.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 0));
		}
		else 
		{
			label.setText("OFF");
			label.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));
		}
	}

}
