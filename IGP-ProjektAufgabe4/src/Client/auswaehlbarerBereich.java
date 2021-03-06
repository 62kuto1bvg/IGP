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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class auswaehlbarerBereich extends JPanel implements MouseListener, MouseMotionListener {

	int startX = 0;
	int startY = 0;
	int endeX = 0;
	int endeY = 0;

	int mitteX = 0;
	int mitteY = 0;

	double breiteansichtsfenster = 0;

	
	//Hier wird die gr��e des Fenster in dem gezoomt wird �bergebe, damit mit den Massen gerechnet werden kann

	public auswaehlbarerBereich(Rectangle border) {
		//negative if-abfrage
		if (!ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
			this.setOpaque(false);

			this.setBounds(border);
			this.repaint();
			this.addMouseListener(this);
			this.addMouseMotionListener(this);

		}
	}

	public void paint(Graphics g) {
	//Zeichnung
		
		if (!ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
			Graphics2D g2 = (Graphics2D) g;
			
			//Zeichnen des Navigationsfenster, das Schwarze
			g2.setColor(Color.BLACK);
			if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Navigation")) {
				int deltax = startX - endeX;
				endeY = startY + deltax;
				
				
			} else if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Kartenauswahl")) {
				int deltax = startX - endeX;
				endeY = (int) (startY + (deltax / Math.sqrt(2)));
				g2.setColor(Color.BLUE);

			}
			//Zeichen  des blauen Kartenauswahlfensters
			//Das fenster kann nur in eine richtung aufgezogen werden
			if ((startX < endeX)) {
				g2.drawLine(startX, startY, endeX, startY);
				g2.drawLine(startX, startY, startX, endeY);
				g2.drawLine(startX, endeY, endeX, endeY);
				g2.drawLine(endeX, endeY, endeX, startY);
			}
		}

		if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht"))
			//Falls ein Masstab ausgew�hlt worden ist, kann wird hiermit das Vorlagenfenster erzeugt
		{
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(Color.BLACK);
			g2.drawLine((int) (mitteX - (breiteansichtsfenster / 2)),
					(int) (mitteY - (breiteansichtsfenster / 2) / Math.sqrt(2)),
					(int) (mitteX - (breiteansichtsfenster / 2)),
					(int) (mitteY + (breiteansichtsfenster / 2) / Math.sqrt(2)));
			g2.drawLine((int) (mitteX + (breiteansichtsfenster / 2)),
					(int) (mitteY - (breiteansichtsfenster / 2) / Math.sqrt(2)),
					(int) (mitteX + (breiteansichtsfenster / 2)),
					(int) (mitteY + (breiteansichtsfenster / 2) / Math.sqrt(2)));
			g2.drawLine((int) (mitteX - (breiteansichtsfenster / 2)),
					(int) (mitteY + (breiteansichtsfenster / 2) / Math.sqrt(2)),
					(int) (mitteX + (breiteansichtsfenster / 2)),
					(int) (mitteY + (breiteansichtsfenster / 2) / Math.sqrt(2)));
			g2.drawLine((int) (mitteX - (breiteansichtsfenster / 2)),
					(int) (mitteY - (breiteansichtsfenster / 2) / Math.sqrt(2)),
					(int) (mitteX + (breiteansichtsfenster / 2)),
					(int) (mitteY - (breiteansichtsfenster / 2) / Math.sqrt(2)));

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {
			//Abgreifen der Koordinaten der Maus
			mitteY = e.getY();
			mitteX = e.getX();
			//Geometrie des Zeichenfensters
			Rectangle ab = getBounds();
			int Hoehe = ab.height;
			int breite = ab.width;
			
			//getter der BBoxkoordinaten
			double MaxNorth = ActionListenerMap.getMaxNorth();
			double MaxEast = ActionListenerMap.getMaxEast();
			double MinNorth = ActionListenerMap.getMinNorth();
			double MinEast = ActionListenerMap.getMinEast();

			double auswahlminNorth;
			double auswahlmaxNorth;
			double auswahlminEast;
			double auswahlmaxEast;

			
			//Umrechnen des Ausgew�hltenPunktes in Koordinaten
			double deltaY = ((MaxNorth - MinNorth) * (Hoehe - mitteY)) / Hoehe;
			double Yausgew�hlt = (ActionListenerMap.minNorth + deltaY);
			double deltaX = ((mitteX * (MaxEast - MinEast)) / breite);
			double Xausgew�hlt = (ActionListenerMap.minEast + deltaX);

			//////////////////// F�r den Ausw�hlbarebereich der Massstabsgr�sse wird die
			//////////////////// Papierbreite Ben�tigt

			double breitenDinFormate[] = { 29.7, 42.0, 59.4, 84.1, 118, 9 };

			int index = ActionListenerMap.AuswahlFormatIndex;
			double DinFormatLaenge = breitenDinFormate[index];
			double laengeEcht = (DinFormatLaenge * ActionListenerMap.Massstabszahl) / 100;

			// Die Breite des bildes ist somit berechnet. was jetzt fehlt ist die
			// geomtrische umrechnung der Koordinaten
			// Dies erfolgt �ber bogenberechnungen

			int erdradius = 6371000;
			double alpha = Yausgew�hlt;
			double alphaDelta = MaxNorth - MinNorth;

			// Umkreis auf Breitengrad

			double radiusUmkreis = Math.cos(alpha * ((2 * Math.PI) / 360)) * erdradius;

			double beta = (laengeEcht * 180) / (radiusUmkreis * Math.PI);
		
			/// zur Darstellung des Ansichtsfensters des formates wird jetzt allerdings die
			/// "echte breite ben�tigt"

			double betagesamt = ((ActionListenerMap.maxEast - ActionListenerMap.minEast));

			int Bogenlaengegesamt = (int) ((radiusUmkreis * (Math.PI) * betagesamt) / 180);

			double FaktormassstabAnsicht = (Bogenlaengegesamt / laengeEcht);

			breiteansichtsfenster = (breite / FaktormassstabAnsicht);

			
			
			
			
			//Wenn der bereich des Blattes angezeigt ist und ich doppelclicke wird die Druck�bersicht ge�ffnet
			
			if (e.getClickCount() == 2) {

				Druckuebersicht druckuebersicht2 = new Druckuebersicht();
				
				
				
				//Umrechen der Auswahlmaskeneckpunte in Koordinaten
				auswahlminEast = Xausgew�hlt - (beta / 2);
				auswahlmaxEast = Xausgew�hlt + (beta / 2);
				auswahlminNorth = Yausgew�hlt - (((breiteansichtsfenster / Math.sqrt(2)) / 2) * alphaDelta) / Hoehe;
				auswahlmaxNorth = Yausgew�hlt + (((breiteansichtsfenster / Math.sqrt(2)) / 2) * alphaDelta) / Hoehe;

				ActionListenerMap.setMinNorth((auswahlminNorth));
				ActionListenerMap.setMaxNorth((auswahlmaxNorth));
				ActionListenerMap.setMinEast((auswahlminEast));
				ActionListenerMap.setMaxEast((auswahlmaxEast));

				System.out.println("breiteansichtsfenster" + breiteansichtsfenster);

				
				//warnfenster, falls der Masstab zu gross oder zu klein sein sollte
				if (breiteansichtsfenster < 20) {

					JFrame FensterWarnungGrossmasstaeblich = new JFrame();

					FensterWarnungGrossmasstaeblich.setBounds(200, 200, 400, 100);
					JTextField Warntext = new JTextField("Ausschnitt zu gro�, bitte reinzoomen");
					Warntext.setText("Ausschnitt zu gro�, bitte reinzoomen");
					Warntext.setBounds(50, 250, 400, 400);
					FensterWarnungGrossmasstaeblich.add(Warntext);
					Warntext.setVisible(true);
					FensterWarnungGrossmasstaeblich.setVisible(true);
				}

				else if (breiteansichtsfenster > 1000) {

					JFrame FensterWarnungKleinmasstaeblich = new JFrame();

					FensterWarnungKleinmasstaeblich.setBounds(200, 200, 400,100);
					JTextField Warntext = new JTextField("Ausschnitt zu Klein, bitte rauszoomen");
					Warntext.setText("Ausschnitt zu gro�, bitte reinzoomen");
					Warntext.setBounds(50, 250, 400, 400);
					FensterWarnungKleinmasstaeblich.add(Warntext);
					Warntext.setVisible(true);
					FensterWarnungKleinmasstaeblich.setVisible(true);
				}

				else {
					try {
						druckuebersicht2.Oeffne�bersicht(ActionListenerMap.crs, ActionListenerMap.minEast,
								ActionListenerMap.minNorth, ActionListenerMap.maxEast, ActionListenerMap.maxNorth,
								ActionListenerMap.verhaeltnis);

					} catch (IOException en) {
						// TODO Auto-generated catch block
						en.printStackTrace();
					}
				}

			}

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
		if ((startX < endeX)) {
			Rectangle ab = getBounds();
			int Hoehe = ab.height;
			int breite = ab.width;
			double MaxNorth = ActionListenerMap.getMaxNorth();
			double MaxEast = ActionListenerMap.getMaxEast();
			double MinNorth = ActionListenerMap.getMinNorth();
			double MinEast = ActionListenerMap.getMinEast();

/////////////um sicherzugehen, das auch mit einem ansichtsk�stchen gearbeitet werden , welches falscherum aufgezogen ist, muss dieses �berpr�ft werden///////

////////////////////////////////////////////	

			//Umrechnen de Koordinaten im Navigationsmodus
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
				
				
				//Umrechnen de Koordinaten im Kartenauswahlmodus
			} else if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Kartenauswahl")) {

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
					druckuebersicht.Oeffne�bersicht(ActionListenerMap.crs, ActionListenerMap.minEast,
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

		} else {

		}
	};

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

		if (ActionListenerMap.auswaehlbarerBereichStatus.contains("Masstabsansicht")) {

			mitteY = e.getY();
			mitteX = e.getX();

			this.repaint();

		}
	}
}
