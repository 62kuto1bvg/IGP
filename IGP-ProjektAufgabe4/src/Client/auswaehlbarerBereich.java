package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class auswaehlbarerBereich extends JPanel implements MouseListener, MouseMotionListener {

	int startX = 0;
	int startY = 0;
	int endeX = 0;
	int endeY = 0;

	int mitteX=0;
	int mitteY=0;
		
	public auswaehlbarerBereich(Rectangle border) {
if(!ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
		this.setOpaque(false);

		this.setBounds(border);
		this.repaint();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}
	}
	public void paint(Graphics g) {
		if(!ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Navigation")) {
			int deltax = startX - endeX;
			endeY = startY + deltax;
		} else if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Kartenauswahl")) {
			int deltax = startX - endeX;
			endeY = (int) (startY + (deltax / Math.sqrt(2)));
			g2.setColor(Color.BLUE);

		}
		

		
		g2.drawLine(startX, startY, endeX, startY);
		g2.drawLine(startX, startY, startX, endeY);
		g2.drawLine(startX, endeY, endeX, endeY);
		g2.drawLine(endeX, endeY, endeX, startY);
	
		
		}
		
		if(ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		
		
		g2.drawLine(mitteX, mitteY, mitteX+50, mitteY+50);
//		g2.drawLine(startX, startY, startX, endeY);
//		g2.drawLine(startX, endeY, endeX, endeY);
//		g2.drawLine(endeX, endeY, endeX, startY);
	
		
		}
		
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
			
		startX = e.getX();
		startY = e.getY();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		Rectangle ab = getBounds();
		int Hoehe = ab.height;
		int breite = ab.width;
		double MaxNorth = ActionListenerMap.getMaxNorth();
		double MaxEast = ActionListenerMap.getMaxEast();
		double MinNorth = ActionListenerMap.getMinNorth();
		double MinEast = ActionListenerMap.getMinEast();

		
/////////////um sicherzugehen, das auch mit einem ansichtsk‰stchen gearbeitet werden , welches falscherum aufgezogen ist, muss dieses ¸berpr¸ft werden///////
		
		
		if ((startX>endeX)) {
			
			int TauschStart=startX;
			startX=startY;
			startY = TauschStart;
		
			int TauschEnde=endeX;
			endeX=endeY;
			endeY = TauschEnde;
		
			
		}
	
		
		
		
////////////////////////////////////////////	
		
		if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Navigation")) {
			
			
			
		double deltaY = (((MaxNorth - MinNorth)) * endeY) / Hoehe;
		ActionListenerMap.setMaxNorth((ActionListenerMap.maxNorth - deltaY));

		double deltaY2 = ((MaxNorth - MinNorth) * (Hoehe - startY)) / Hoehe;
		ActionListenerMap.setMinNorth((ActionListenerMap.minNorth + deltaY2));

		double deltaX = (startX * (MaxEast - MinEast) / breite);
		ActionListenerMap.setMinEast((ActionListenerMap.minEast + deltaX));

		double deltaX2 = ((breite - endeX) * (MaxEast - MinEast) / breite);
		ActionListenerMap.setMaxEast((ActionListenerMap.maxEast - deltaX2));
		

			CreateWindow.loadMap.doClick();
		} else if(ActionListenerMap.auswaehlbarerBereichStatus.contains("Kartenauswahl")) {
			

			
			double deltaY = (((MaxNorth - MinNorth)) * endeY) / Hoehe;
			ActionListenerMap.setMaxNorth((ActionListenerMap.maxNorth - deltaY));

			double deltaY2 = ((MaxNorth - MinNorth) * (Hoehe - startY)) / Hoehe;
			ActionListenerMap.setMinNorth((ActionListenerMap.minNorth + deltaY2));

			double deltaX = (startX * (MaxEast - MinEast) / breite);
			ActionListenerMap.setMinEast((ActionListenerMap.minEast + deltaX));

			double deltaX2 = ((breite - endeX) * (MaxEast - MinEast) / breite);
			ActionListenerMap.setMaxEast((ActionListenerMap.maxEast - deltaX2));
			
		

			Druckuebersicht druckuebersicht = new Druckuebersicht();

			try {
				druckuebersicht.Oeffne‹bersicht(ActionListenerMap.crs, ActionListenerMap.minEast,
						ActionListenerMap.minNorth, ActionListenerMap.maxEast, ActionListenerMap.maxNorth,
						ActionListenerMap.verhaeltnis);
				
			} catch (IOException en) {
				// TODO Auto-generated catch block
				en.printStackTrace();
			}
		}

		startX = 0;
		startY = 0;
		endeX = 0;
		endeY = 0;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		endeX = e.getX();
		endeY = e.getY();

		this.repaint();
		// umrechnen des Kleinen fensters in neue BBboxkordinaten

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mitteY=e.getY();
		mitteX=e.getX();

		this.repaint();
//	int eX=e.getX();
//		int eY=e.getY();
//		System.out.println(eX);
//		System.out.println(eY);
	}
}
