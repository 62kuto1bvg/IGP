package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Koordinatengitter extends JPanel {

	double maxEast;
	double maxNorth;
	double minEast;
	double minNorth;
	double verhaeltnis;
	String crs;
	double DeltaX, DeltaY;
	int width;
	ArrayList<Integer> Laengengradetransformiert = new ArrayList<>();
	ArrayList<Double> Laengengrade = new ArrayList<>();

	ArrayList<Double> Breitengradetransformiert = new ArrayList<>();
	ArrayList<Double> Breitengrade = new ArrayList<>();

	public void erzeugeKoordinatengitter(String crs, double minE, double minN, double maxE, double maxN,
			double verhaeltnis, int width) {

		
		
		this.crs = crs;
		this.minEast = minE;
		// this.minNorth = minNorth;
		this.maxEast = maxE;
		// this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
		this.width = (int) width;

		// Behelf, um voerst den Kartenausschnitt auf DIN Format zu bekommen:
		DeltaX = maxEast - minEast;
		DeltaY = maxNorth - minNorth;

		double Verhal = DeltaX / Math.sqrt(2);
		double Abzug = (DeltaX - Verhal) / 2;

		this.minNorth = minN + Abzug;
		this.maxNorth = maxN - Abzug;

	// Rechnen der Winkel:
		// Alpha ist der Winkel zwischen Äuquator und dem Punkt auf der Kugel
		// Umkreis auf Breitengrad:

		// Winkel Bogensegment:
		double beta = ((maxEast - minEast));
		// Winkel zwischen den Breitengraden:
		System.out.println("TEEEEEEESTTTmaxNorth" +maxNorth);
		System.out.println("TEEEEEEESTTTminNorth" +minNorth);
		double alpha = (maxNorth - minNorth);
		// Iterationswerte, um Winkel welche kleiner sind als 1 zu vereinfachen:
		double betaVereinfacht = 0;
		double betaiteration = beta;
		int betaiterationcounter = 0;

		double alphaVereinfacht = 0;
		double alphaiteration = alpha;
		int alphaiterationcounter = 0;

		double KoordinatengitterStartxiteration = minEast;
		double KoordinatengitterStartxVereinfacht = 0;

		double KoordinatengitterStartyiteration = minNorth;
		double KoordinatengitterStartyVereinfacht = 0;

		
		// der Winkel Beta muss ähnlich wie bei der Massstabsleiste vereinfacht werden.
		// Hierfür muss erst einmal überprüft werden, ob diese größer oder kleiner als 1 Grad ist.
		// Für Beta muss ein Iterationshilfswert benutzt werden,
		// Zusätzlich wird ein Counter gestartet:
		///////////////////////////////////////////////////////////////////////////////////////////////////
		if (betaiteration < 1.00) {

			do {
				betaiteration = betaiteration * 10;

				betaiterationcounter = betaiterationcounter + 1;

				// Zur Vereinfachung wird die Zahl gerundet:
				double betaiterationVereinfacht = Math.round(betaiteration);

				// ...und nun wieder in die ursprüngliche Dimension umgewandelt:
				if (betaiterationcounter == 0) {
					betaVereinfacht = betaiterationVereinfacht;
				}

				else {
					betaVereinfacht = betaiterationVereinfacht / Math.pow(10, betaiterationcounter);
				}

				// ...somit wurden zum Beispiel aus 0,337854 Grad jetzt 0.3 Grad generiert

			} while (betaiteration <= 1);

			// Ausserdem wird noch ein Startwert benötigt, um von diesem die Koordinatenwerte abzutragen:
			KoordinatengitterStartxiteration = KoordinatengitterStartxiteration * Math.pow(10, betaiterationcounter);

			double iterationKoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);

			KoordinatengitterStartxVereinfacht = iterationKoordinatengitterStartxVereinfacht / Math.pow(10, betaiterationcounter);

		////////////////////////////////////////////////////////////////////////////////////////////////////////
		} else if (betaiteration > 1) {
			betaVereinfacht = Math.round(betaiteration);
			KoordinatengitterStartxVereinfacht = Math.round(KoordinatengitterStartxiteration);
		}
		// Um sicherzugehen, dass zwischen dem gewählten (und gerundetem) Wert und dem
		// Startwert keine zu große Lücke ist, wird manuell ein Breitengrad davorgeschoben
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (alphaiteration < 1.00) {
			do {
				alphaiteration = alphaiteration * 10;
				alphaiterationcounter = alphaiterationcounter + 1;

				// Zur Vereinfachung wird die Zahl gerundet:
				double alphaiterationVereinfacht = Math.round(alphaiteration);

				// ...und nun wieder in die ursprungliche Dimension umgewandelt:
				if (alphaiterationcounter == 0) {
					alphaVereinfacht = alphaiterationVereinfacht;
				}

				else {
					alphaVereinfacht = alphaiterationVereinfacht / Math.pow(10, alphaiterationcounter);
				}				
			} while (alphaiteration <= 1);

			// Ausserdem wird noch ein Startwert benötigt, um von diesem die Koordinatenwerte abzutragen:
			KoordinatengitterStartyiteration = KoordinatengitterStartyiteration * Math.pow(10, alphaiterationcounter);

			double iterationKoordinatengitterStartyVereinfacht = Math.round(KoordinatengitterStartyiteration);

			KoordinatengitterStartyVereinfacht = iterationKoordinatengitterStartyVereinfacht/ Math.pow(10, alphaiterationcounter);
			
			System.out.println("KoordinatengitterStartyVereinfacht"+KoordinatengitterStartyVereinfacht);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		} else if (alphaiteration > 1) {
			System.out.println("ALPHA  "+alpha);
			alphaVereinfacht = Math.round(alphaiteration);
			
			KoordinatengitterStartyVereinfacht = Math.round(KoordinatengitterStartyiteration);
			System.out.println("KoordinatengitterStartyVereinfachtGross"+KoordinatengitterStartyVereinfacht);			
		}

		// Wieder manuell Breitengrad davorschieben:
		double ersterKoordinatengitterwertx = KoordinatengitterStartxVereinfacht - (betaVereinfacht / 6);
		Laengengrade.add(ersterKoordinatengitterwertx);

		double ersterKoordinatengitterwerty = KoordinatengitterStartyVereinfacht - (alphaVereinfacht / 4);
		Breitengrade.add(KoordinatengitterStartyVereinfacht);
		
		
		System.out.println("AAAAAAA+  "+ ersterKoordinatengitterwerty);
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		// nun werden die anderen Linien erzeugt:
		for (int i = 1; i < 5; i++) {
			
			double Koordinatengitterwerty = KoordinatengitterStartyVereinfacht + ((alphaVereinfacht / 4) * i);
			Breitengrade.add(Koordinatengitterwerty);
		
			System.out.println("AAAAAAA+  "+ Koordinatengitterwerty);
		}

		for (int i = 1; i < 7; i++) {
			double Koordinatengitterwertx = KoordinatengitterStartxVereinfacht + ((betaVereinfacht / 6) * i);
			Laengengrade.add(Koordinatengitterwertx);
		}

		// Jetzt muss allerdings noch die Lage auf dem Bildschirm berechnet werden:
		/////////// Transformieren //////////
		for (int j = 0; j < Laengengrade.size(); j++) {

			double Laenge = Laengengrade.get(j);
			int LaengeBildschirm = (int) (((Laenge - minEast) * (width)) / beta);
	
			Laengengradetransformiert.add(LaengeBildschirm);		
		}
		
		for (int j = 0; j < Breitengrade.size(); j++) {
			
			double Breiten = Breitengrade.get(j);
			
			
			double deltaBreite=(Breiten-minNorth);
			double hight=(((width/Math.sqrt(2))-40));
			double breiteBildschirm=(((deltaBreite)*(hight))/alpha);
			Breitengradetransformiert.add(breiteBildschirm);
			
			
		
			}
		

	}

public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	//-------------------------------------------- Selbe Vorgehensweise für die Breitengrade: -------------------------------------------------------
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Client.Druckuebersicht.ausgewaehlteFarbeKoordinatengitter);
		
		// Zeichnen der senkrechten Linien:
		for (int j = 0; j < Laengengradetransformiert.size(); j++) {

			int laenge = Laengengradetransformiert.get(j);
			if (laenge < 0 || laenge > width) {

			} else {
				g2.drawLine(laenge, 0, laenge, (int) (width / Math.sqrt(2)));

				// Beschriften der senkrechten Linien:
				String Breitetext = String.valueOf(Math.round(Laengengrade.get(j) * 100) / 100.0);
				g2.drawString(Breitetext, laenge + 10, 10);
			}
		}
	
		for (int j = 0; j < Breitengradetransformiert.size(); j++) {
				
		Double breite = Breitengradetransformiert.get(j);
			if (breite < 0 || breite > (width/Math.sqrt(2))-20) {

			} else {		
				g2.drawLine(0,(int) ((((width/Math.sqrt(2))-(breite)))-20), width,(int) (((width/Math.sqrt(2))-(breite)))-20);
			
				// Beschriften der senkrechten Linien:
				String Breitetext = String.valueOf(Math.round(Breitengrade.get(j) * 100) / 100.0);
				g2.drawString(Breitetext, 10, (int) ((width/Math.sqrt(2))-(breite))+10);
				g2.drawString(String.valueOf(minNorth), 0, (int)((width/Math.sqrt(2))-10));
				g2.drawString(String.valueOf(maxNorth), 0, 10);	
				
			}
		}
	}
}