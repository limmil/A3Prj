package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer
{
	GameWorld gw;
	
	public MapView(GameWorld gw)
	{
		this.gw = gw;
		
		getAllStyles().setBorder(Border.createLineBorder(3,
				ColorUtil.LTGRAY));
		getStyle().setBgColor(ColorUtil.LTGRAY);
		
	}
	
	// update the image on the container
	public void paint(Graphics g) 
	{
		super.paint (g);
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		//move drawing coordinates back
		gXform.translate(getAbsoluteX(),getAbsoluteY());
		//apply translate associated with display mapping
		gXform.translate(0, getHeight());
		//apply scale associated with display mapping
		gXform.scale(1, -1);
		//move drawing coordinates so that the local origin coincides with the screen origin
		gXform.translate(-getAbsoluteX(),-getAbsoluteY());
		g.setTransform(gXform);
		
		
		// mapview origin
		Point pCmpRelPrnt = new Point();
		pCmpRelPrnt.setX(getX());
		pCmpRelPrnt.setY(getY());
		// iterate through the GameWorld objects
		IIterator it = gw.getGameObjects().getIterator();
		while(it.hasNext())
		{
			Object obj = it.getNext();
			
			if(obj instanceof IDrawable)
			{
				IDrawable drawable = (IDrawable) obj;
				drawable.draw(g, pCmpRelPrnt);
			}
		}
		
		g.resetAffine();
		
	}
	
	public void update(Observable observable, Object data)
	{
		this.repaint();
	}

}
