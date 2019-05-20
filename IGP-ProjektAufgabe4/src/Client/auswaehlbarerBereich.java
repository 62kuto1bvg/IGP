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
		
	double breiteansichtsfenster=0;
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
		
		
		g2.drawLine((int)(mitteX-(breiteansichtsfenster/2)),(int)( mitteY-(breiteansichtsfenster/2)/Math.sqrt(2)), (int)(mitteX-(breiteansichtsfenster/2)),(int)( mitteY+(breiteansichtsfenster/2)/Math.sqrt(2)));
		g2.drawLine((int)(mitteX+(breiteansichtsfenster/2)),(int)( mitteY-(breiteansichtsfenster/2)/Math.sqrt(2)), (int)(mitteX+(breiteansichtsfenster/2)),(int)( mitteY+(breiteansichtsfenster/2)/Math.sqrt(2)));

		g2.drawLine((int)(mitteX-(breiteansichtsfenster/2)),(int)( mitteY+(breiteansichtsfenster/2)/Math.sqrt(2)), (int)(mitteX+(breiteansichtsfenster/2)),(int)( mitteY+(breiteansichtsfenster/2)/Math.sqrt(2)));
		
		g2.drawLine((int)(mitteX-(breiteansichtsfenster/2)),(int)( mitteY-(breiteansichtsfenster/2)/Math.sqrt(2)), (int)(mitteX+(breiteansichtsfenster/2)),(int)( mitteY-(breiteansichtsfenster/2)/Math.sqrt(2)));

		
		}
		
		
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
			
		mitteY=e.getY();
		mitteX=e.getX();

		Rectangle ab = getBounds();
		int Hoehe = ab.height;
		int breite = ab.width;
		double MaxNorth = ActionListenerMap.getMaxNorth();
		double MaxEast = ActionListenerMap.getMaxEast();
		double MinNorth = ActionListenerMap.getMinNorth();
		double MinEast = ActionListenerMap.getMinEast();

		
		
		double deltaY = ((MaxNorth - MinNorth) * (Hoehe - mitteY)) / Hoehe;
	    double Yausgew‰hlt=(ActionListenerMap.minNorth + deltaY);
		double deltaX = ((mitteX * (MaxEast - MinEast)) / breite);
		double Xausgew‰hlt=(ActionListenerMap.minEast + deltaX);
		
		
		////////////////////F¸r den Ausw‰hlbarebereich der Massstabsgrˆsse wird die Papierbreite Benˆtigt
		
		double breitenDinFormate[]={29.7,42.0,59.4,84.1,118,9};
		
		int index=ActionListenerMap.AuswahlFormatIndex;
		double DinFormatLaenge = breitenDinFormate[index];
		double laengeEcht=(DinFormatLaenge*ActionListenerMap.Massstabszahl)/100;

		
		//Die Breite des bildes ist somit berechnet. was jetzt fehlt ist die geomtrische umrechnung der Koordinaten
		//Dies erfolgt ¸ber bogenberechnungen
		
		int erdradius = 6371000;
		double alpha=Yausgew‰hlt;
		// Umkreis auf Breitengrad

		double radiusUmkreis = Math.cos(alpha * ((2 * Math.PI) / 360)) * erdradius;

		double beta=(laengeEcht*180)/(radiusUmkreis*Math.PI);	
		//Umrechnen in neue BBoxkoordinaten
		double auswahlminNorth=Xausgew‰hlt-(beta/2);
		double auswahlmaxNorth=Xausgew‰hlt+(beta/2);
		
		double auswahlminEast=Yausgew‰hlt-((beta/2)/Math.sqrt(2));
		double auswahlmaxEast=Yausgew‰hlt+((beta/2)/Math.sqrt(2));
		
		///zur Darstellung des Ansichtsfensters des formates wird jetzt allerdings die "echte breite benˆtigt"
		
		double betagesamt = ((ActionListenerMap.maxEast - ActionListenerMap.minEast));
		
		int Bogenlaengegesamt = (int) ((radiusUmkreis * (Math.PI) * betagesamt) / 180);
		
		
		double FaktormassstabAnsicht=(Bogenlaengegesamt/laengeEcht);
		
		breiteansichtsfenster=(breite/FaktormassstabAnsicht);
		
		System.out.println("breiteansichtsfenster "+breiteansichtsfenster);
		System.out.println("FaktormassstabAnsicht "+FaktormassstabAnsicht);
		System.out.println("Bogenlaengegesamt "+Bogenlaengegesamt);
		
		System.out.println("radiusUmkreis "+radiusUmkreis);
		System.out.println("betagesamt "+betagesamt);
		
	
		
		
		
		this.repaint();
		
		
}
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
		
		if(ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
		
		mitteY=e.getY();
		mitteX=e.getX();

		this.repaint();
		
		
}
	}
}
