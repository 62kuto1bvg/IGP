package Client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

import org.omg.CORBA.Bounds;

public class ActionListenerMap extends CreateWindow implements ActionListener {
	static String crs;
	static double minEast;
	static double minNorth;
	static double maxEast;
	static double maxNorth;
	static double verhaeltnis;

	 static String auswaehlbarerBereichStatus = "Navigation";

	static JPanel panelMap;
	JFrame frame;
	BasicArrowButton buttonOst, buttonWest, buttonNord, buttonSued;
	final JTextField Massstab;
	JButton zoomIn, zoomOut, printToPDF, BereichAuswaehlen, MassstabOK;
	JComboBox<?> comboBoxCRS;
	JComboBox<String> AuswahlFormat;
	JLabel infoBBOXaenderung;
	JTextField bBoxX1Feld, bBoxY1Feld, bBoxX2Feld, bBoxY2Feld;
	JScrollPane display;
	JLabel txtErkl�rung;
	static int AuswahlFormatIndex;
	ArrayList<String> exportListe = new ArrayList<String>(); // Anlegen einer Exportliste (wird sp�ter "gef�ttert" und
																// ausgegeben)
	static String exportFormat;
	String masstabstext;
	static int Massstabszahl = 0;

	// wird im ActionListener initialisiert und sp�ter ben�tigt f�r das schreiben
	// der Ausgabedatei
//--------------------------------------------- Konstruktor: -----------------------------------------------------------------------------------------------------	
	public ActionListenerMap(JPanel panelMap, JFrame frame, BasicArrowButton buttonOst, BasicArrowButton buttonWest,
			BasicArrowButton buttonNord, BasicArrowButton buttonSued, JButton zoomIn, JButton zoomOut,
			JButton printToPDF, JComboBox<String> comboBoxCRS, JButton BereichAuswaehlen, JTextField bBoxX1Feld,
			JTextField bBoxY1Feld, JTextField bBoxX2Feld, JTextField bBoxY2Feld, JLabel infoBBOXaenderung,
			JComboBox<String> AuswahlFormat, JButton MassstabOK, JTextField Massstab, JLabel txtErkl�rung) {
		// TODO Auto-generated constructor stub

		super();
		this.panelMap = panelMap;
		this.frame = frame;
		this.buttonOst = buttonOst;
		this.buttonWest = buttonWest;
		this.buttonNord = buttonNord;
		this.buttonSued = buttonSued;
		this.zoomIn = zoomIn;
		this.zoomOut = zoomOut;
		this.printToPDF = printToPDF;
		this.comboBoxCRS = comboBoxCRS;
		this.bBoxX1Feld = bBoxX1Feld;
		this.bBoxY1Feld = bBoxY1Feld;
		this.bBoxX2Feld = bBoxX2Feld;
		this.bBoxY2Feld = bBoxY2Feld;
		this.BereichAuswaehlen = BereichAuswaehlen;
		this.infoBBOXaenderung = infoBBOXaenderung;
		this.txtErkl�rung = txtErkl�rung;

		this.AuswahlFormat = AuswahlFormat;
		this.MassstabOK = MassstabOK;
		this.Massstab = Massstab;
	}

	// --------------------------------------- Action-Command:
	public void actionPerformed(ActionEvent actionEvent) { // Ein ActionListenerMap muss IMMER "actionPerformed"
															// implementieren !

		String actionCommand = actionEvent.getActionCommand();
		

//------------------------------------------------------ Drop-Down-Men�s: --------------------------------------------------------------------------------------
		// CRS w�hlen und passende Bounding Box abgreifen:
		if (actionCommand.equals("Drop-Down CRS")) {
			crs = (String) ((JComboBox<?>) actionEvent.getSource()).getSelectedItem();
			

			// Koordinaten der BBox zum gew�hlten CRS:
			GetBBox newBBoxList = new GetBBox(url);
			BBoxList koordList = new BBoxList();
			koordList = newBBoxList.getBBoxfromXML();

			for (int k = 0; k < koordList.amountBBoxes(); k++)
				if (koordList.giveback(k).crs.equals(crs)) {
					minEast = koordList.giveback(k).minEast;
					minNorth = koordList.giveback(k).minNorth;
					maxEast = koordList.giveback(k).maxEast;
					maxNorth = koordList.giveback(k).maxNorth;

					

					if (minEast < 0 && minNorth < 0) {
						verhaeltnis = (maxEast + (0 - minEast)) / (maxNorth + (0 - minNorth));
					}
					if (minEast > 0 && minNorth < 0) {
						verhaeltnis = (maxEast - minEast) / (maxNorth + (0 - minNorth));
					}
					if (minEast < 0 && minNorth > 0) {
						verhaeltnis = (maxEast + (0 - minEast)) / (maxNorth - minNorth);
					} else
						verhaeltnis = (maxEast - minEast) / (maxNorth - minNorth);
				}
		}
//--------------------------------------------------------- Karte laden: --------------------------------------------------------------------------------------		  

		Rectangle border = panelMap.getBounds();
		
		auswaehlbarerBereich ab = new auswaehlbarerBereich(border);

		// Wenn Karte Laden-Button gedr�ckt wurde:

		if (actionCommand.equals("lade Karte")) {

			// L�schen der aktuellen Karte:
			panelMap.removeAll();

			// Laden der neuen Karte:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Karte auf Fenster setzen:

			frame.setLayout(null);
			frame.add(ab);
		
			frame.add(panelMap);

			frame.add(panelNavigation);
			frame.setVisible(true);

			// Navigations- und DRUCKEN-Buttons sichtbar setzen:
			Massstab.setVisible(true);
		//	MassstabOK.setVisible(true);
			buttonOst.setVisible(true);
			buttonWest.setVisible(true);
			buttonNord.setVisible(true);
			buttonSued.setVisible(true);
			zoomIn.setVisible(true);
			zoomOut.setVisible(true);
			printToPDF.setVisible(true);
			BereichAuswaehlen.setVisible(true);
			AuswahlFormat.setVisible(true);
			// Bounding-Box-Felder sichtbar setzten und aktuelle BBox-Koordinaten ausgeben:
			bBoxX1Feld.setVisible(true);
			bBoxY1Feld.setVisible(true);
			bBoxX2Feld.setVisible(true);
			bBoxY2Feld.setVisible(true);
			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
			infoBBOXaenderung.setVisible(true);
			txtErkl�rung.setVisible(true);

		}

		// Benutzerdefiniertes Anpassen der Bounding Box:
		if (actionCommand.equals("BBox Xmin ge�ndert")) {
			minEast = Integer.parseInt(bBoxX1Feld.getText());
		}
		if (actionCommand.equals("BBox Ymin ge�ndert")) {
			minNorth = Integer.parseInt(bBoxY1Feld.getText());
		}
		if (actionCommand.equals("BBox Xmax ge�ndert")) {
			maxEast = Integer.parseInt(bBoxX2Feld.getText());
		}
		if (actionCommand.equals("BBox Ymax ge�ndert")) {
			maxNorth = Integer.parseInt(bBoxY2Feld.getText());
		}
//--------------------------------------------------------- Navigation: ----------------------------------------------------------------------------------------		 
		// Wenn Ostpfeil-Button gedr�ckt wurde:
		if (actionCommand.equals("Nach Osten")) {
			// Befehl an Konsole ausgeben

			// Berechnen der neuen BBox-Koord.:
			double a = maxEast - minEast;
			minEast = minEast + a / 3;
			maxEast = maxEast + a / 3;

			// L�schen der aktuellen Karte:
			panelMap.removeAll();
			// neue Karte (mit neuer BBox) laden:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);

				frame.repaint();
				frame.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// BBox-Koord.-Feld aktualisieren:
			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
		}

		// Wenn Westpfeil gedr�ckt wurde:
		if (actionCommand.equals("Nach Westen")) {
		
			// Berechnen der neuen BBox-Koord.:
			double a = maxEast - minEast;
			minEast = minEast - a / 3;
			maxEast = maxEast - a / 3;

			// L�schen der aktuellen Karte:
			panelMap.removeAll();
			// neue Karte (mit neuer BBox) laden:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);

				frame.repaint();
				frame.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
		}

		// Wenn Nordpfeil gedr�ckt wurde:
		if (actionCommand.equals("Nach Norden")) {
			

			// Berechnen der neuen BBox-Koord.:
			double a = maxNorth - minNorth;
			minNorth = minNorth + a / 3;
			maxNorth = maxNorth + a / 3;

			// L�schen der aktuellen Karte:
			panelMap.removeAll();
			// neue Karte (mit neuer BBox) laden:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);

				frame.repaint();
				frame.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
		}

		// Wenn S�dpfeil gedr�ckt wurde:
		if (actionCommand.equals("Nach S�den")) {
		
			// Berechnen der neuen BBox-Koord.:
			double a = maxNorth - minNorth;
			minNorth = minNorth - a / 3;
			maxNorth = maxNorth - a / 3;

			// L�schen der aktuellen Karte:
			panelMap.removeAll();
			// neue Karte (mit neuer BBox) laden:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);

				frame.repaint();
				frame.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
		}

		// Wenn Zoom-In-Button (+) gedr�ckt wurde:
		if (actionCommand.equals("zoom+")) {
			

			// Berechnen der neuen BBox-Koord.:
			double a = maxEast - minEast;
			minEast = minEast + (a / 5 * verhaeltnis);
			minNorth = minNorth + a / 5;
			maxEast = maxEast - (a / 5 * verhaeltnis);
			maxNorth = maxNorth - a / 5;

			// L�schen der aktuellen Karte:
			panelMap.removeAll();
			// neue Karte (mit neuer BBox) laden:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);

				frame.repaint();
				frame.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
		}

		// Wenn Zoom-Out (-) gedr�ckt wurde:
		if (actionCommand.equals("zoom-")) {
			

			// Berechnen der neuen BBox-Koord.:
			double a = maxEast - minEast;
			minEast = minEast - (a / 3 * verhaeltnis);
			minNorth = minNorth - a / 3;
			maxEast = maxEast + (a / 3 * verhaeltnis);
			maxNorth = maxNorth + a / 3;

			// L�schen der aktuellen Karte:
			panelMap.removeAll();
			// neue Karte (mit neuer BBox) laden:
			try {
				LoadMap newMap = new LoadMap(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
				JLabel actualMap = (JLabel) newMap.showMap();
				panelMap.add(actualMap);

				frame.repaint();
				frame.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			bBoxX1Feld.setText("" + Math.rint(minEast * 1000) / 1000);
			bBoxY1Feld.setText("" + Math.rint(minNorth * 1000) / 1000);
			bBoxX2Feld.setText("" + Math.rint(maxEast * 1000) / 1000);
			bBoxY2Feld.setText("" + Math.rint(maxNorth * 1000) / 1000);
		}

		// Wenn DRUCKEN gedr�ckt wurde:
		if (actionCommand.equals("druck")) {
		
			Druckuebersicht druckuebersicht = new Druckuebersicht();

			try {
				druckuebersicht.Oeffne�bersicht(crs, minEast, minNorth, maxEast, maxNorth, verhaeltnis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (actionCommand.equals("Fenster waehlen")) {

			auswaehlbarerBereichStatus = "Kartenauswahl";

		}
		if (actionCommand.equals("AuswaehlenDINFormat")) {
			AuswahlFormatIndex = AuswahlFormat.getSelectedIndex();
			

		}

		if (actionCommand.equals("Massstabszahl")) {

			masstabstext = Massstab.getText();
			Massstabszahl = Integer.parseInt(masstabstext);
			auswaehlbarerBereichStatus = "Masstabsansicht";
		

		}

//			   

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------		 			   
	} // Ende der Action-Command

	public static double getMinEast() {
		return minEast;
	}

	public static void setMinEast(double minEast) {
		ActionListenerMap.minEast = minEast;
	}

	public static double getMinNorth() {
		return minNorth;
	}

	public static void setMinNorth(double minNorth) {
		ActionListenerMap.minNorth = minNorth;
	}

	public static double getMaxEast() {
		return maxEast;
	}

	public static void setMaxEast(double maxEast) {
		ActionListenerMap.maxEast = maxEast;
	}

	public static double getMaxNorth() {
		return maxNorth;
	}

	public static void setMaxNorth(double maxNorth) {
		ActionListenerMap.maxNorth = maxNorth;
	}

}