package Client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class Massstabsleiste extends JPanel {

	double maxEast;
	double maxNorth;
	double minEast;
	double minNorth;
	double verhaeltnis;
	String crs;
	double DeltaX, DeltaY;
	double width;
	public int anteiligeBildschirmstrecke;
	public int vereinfachteverkleinerteBogenlänge;
	public int Skalierung;
	
	
	
	public void erstelleMassstabsleiste(String crs, double minEast, double minNorth, double maxEast, double maxNorth,
			double verhaeltnis, int width, int Skalierung) {

		this.crs = crs;
		this.minEast = minEast;
		// this.minNorth = minNorth;
		this.maxEast = maxEast;
		// this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
		this.width = width-40;
		this.Skalierung=Skalierung;
		// Behelf um Voerst den Kartenausschnitt auf DIN Format zu bekommen

		DeltaX = maxEast - minEast;
		DeltaY = maxNorth - minNorth;
		double Verhal = DeltaX / Math.sqrt(2);
		double Abzug = (DeltaX - Verhal) / 2;

		
		this.minNorth = minNorth + Abzug;
		this.maxNorth = maxNorth - Abzug;

		// Umkreis des Breitengrades

		// Alpha ist der winkel zwischen Aeuquator und den punkt auf der Kugel
		int erdradius = 6371000;
		double alpha = ((maxNorth + minNorth) / 2);

		// Umkreis auf Breitengrad

		double radiusUmkreis = Math.cos(alpha * ((2 * Math.PI) / 360)) * erdradius;

		// Winkel Bogensegment
		double beta = ((maxEast - minEast));

		// Bogenlaenge

		int Bogenlaengegesamt = (int) ((radiusUmkreis * (Math.PI) * beta) / 180);
		// Damit die Massstabsleiste nicht zu gross wird, muss diese verkleinert werden
		int Bogenlaenge = (int) (Bogenlaengegesamt / 3);

		// es muss eine gerade zahl erzeugt werden, welche im groben mit der Strecke
		// übereinstimmt. Dies ist notwendig, um eine gerade ZaHl auf die
		// Massstabsleiste zu bringen

		// es wird die Anzahl der Ziffer benötigt
		String StreckeBogen = String.valueOf(Bogenlaenge);
		int Ziffernanzahl = StreckeBogen.length();
		
		
		// Die strecke wird geteilt und dann gerundet

		double gekurzteverkleinerteBogenlaenge = Bogenlaenge / (Math.pow(10, (Ziffernanzahl - 1)));
		double gekurzteverkleinerteBogenlaengerundet = Math.round(gekurzteverkleinerteBogenlaenge);

		
		
		// Anschliessend wieder multipliziert, so das die Dimension der Strecke wieder
		// ähnlich ist wie der auf der Karte
		// So wurde zumbeispiel aus 22430 die Zahl 20000 erzeugt. Dies war notwendig, um
		// eine runde zahl für die Massstabsleiste zu erzeugen

		vereinfachteverkleinerteBogenlänge = (int) (gekurzteverkleinerteBogenlaengerundet
				* (Math.pow(10, (Ziffernanzahl - 1))));

		// es muss ausgrechntet werden, was die echte Strecke anteilig auf dem
		// bildschirm ist

		anteiligeBildschirmstrecke = (int) ((vereinfachteverkleinerteBogenlänge * width) / Bogenlaengegesamt);
		System.out.println(anteiligeBildschirmstrecke);

	}



	public void setWidth(double width) {
		this.width = width;
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke((int)Skalierung*1));
		
		g2.setColor(Client.Druckuebersicht.ausgewaehlteFarbeMassstabsleiste);
		g2.setFont(new Font("ARIAL", Font.PLAIN,(int) 10*Skalierung));
		//ANTIALIASING hilft gegen Kantenflimmern
		//g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		// horizontale Linie		
		g2.drawLine(0, (anteiligeBildschirmstrecke / 20), anteiligeBildschirmstrecke,
				(anteiligeBildschirmstrecke / 20));
		// Linke Abschlusslinie
		g2.drawLine(0, 0, 0, anteiligeBildschirmstrecke / 10);
		// Rechte Abschlusslinie
		g2.drawLine(anteiligeBildschirmstrecke, 0, anteiligeBildschirmstrecke, anteiligeBildschirmstrecke / 10);
		// Mittellinie
		g2.drawLine(anteiligeBildschirmstrecke / 2, (int) ((anteiligeBildschirmstrecke / 10) * 0.25),
				anteiligeBildschirmstrecke / 2, (int) ((anteiligeBildschirmstrecke / 10) * 0.75));
		// Zwischenlinien
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 1, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 1, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 2, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 2, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 3, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 3, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 4, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 4, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 6, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 6, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 7, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 7, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 8, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 8, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		g2.drawLine((anteiligeBildschirmstrecke / 10) * 9, (int) ((anteiligeBildschirmstrecke / 10) * 0.45),
				(anteiligeBildschirmstrecke / 10) * 9, (int) ((anteiligeBildschirmstrecke / 10) * 0.55));
		String Massstabstext;
		String Massstabstexthalbestrecke;

		if (vereinfachteverkleinerteBogenlänge < 10000) {
			Massstabstext = String.valueOf(vereinfachteverkleinerteBogenlänge) + " m";
			Massstabstexthalbestrecke = String.valueOf(vereinfachteverkleinerteBogenlänge / 2) + " m";
		} else {

			Massstabstext = String.valueOf(vereinfachteverkleinerteBogenlänge / 1000) + " km";
			Massstabstexthalbestrecke = String.valueOf((vereinfachteverkleinerteBogenlänge / 1000) / 2) + " km";
		}
		g2.drawString(Massstabstext, anteiligeBildschirmstrecke, (anteiligeBildschirmstrecke / 10) + 10);
		g2.drawString("0", 0, (anteiligeBildschirmstrecke / 10) + 10);
		g2.drawString(Massstabstexthalbestrecke, anteiligeBildschirmstrecke / 2,
				(anteiligeBildschirmstrecke / 10) + 10);
	}

}