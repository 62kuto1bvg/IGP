package Client;

import java.awt.Component;
import java.awt.MediaTracker;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LoadKartenBild {
	String crs;
	double minEast, minNorth, maxEast, maxNorth, verhaeltnis;
	static int width;
	static int height;
	double DeltaX, DeltaY;
	int widthRandlos;

//-------------------------- Konstruktor: ----------------------------------------------------------------------------------	
	public LoadKartenBild(String crs, double minEast, double minNorth, double maxEast, double maxNorth,
			double verhaeltnis, int width) {
		super();
		this.crs = crs;
		this.minEast = minEast;
		// this.minNorth = minNorth;
		this.maxEast = maxEast;
		// this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
		this.width = width;

		// Behelf um Voerst den Kartenausschnitt auf DIN Format zu bekommen

		this.DeltaX = maxEast - minEast;
		this.DeltaY = maxNorth - minNorth;

		double Verhal = DeltaX / Math.sqrt(2);
		double Abzug = (DeltaX - Verhal) / 2;

		this.minNorth = minNorth + Abzug;
		this.maxNorth = maxNorth - Abzug;
	}

//------------------------- Methode: ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	public Component showMap() throws IOException {
		String urlGetMap = new String();

		if (crs.equalsIgnoreCase("CRS:84")) {
			// width = 1000;
			widthRandlos = ((int) width) - 40;
			height = (int) (width / (Math.sqrt(2)) - 40);

			urlGetMap = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&VERSION=1.3.0&request=GetMap&BBOX="
					+ minEast + "," + minNorth + "," + maxEast + "," + maxNorth + "&CRS=" + crs + "&WIDTH=" + width
					+ "&HEIGHT=" + (int) height + "&LAYERS=OI.Mosaic.NaturalColor.Feathering&FORMAT=image/png";
		} else {
//				width=2600;
			widthRandlos = (int) width - 40;
			height = (int) (width / (Math.sqrt(2)) - 40);

			urlGetMap = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&VERSION=1.3.0&request=GetMap&BBOX="
					+ minNorth + "," + minEast + "," + maxNorth + "," + maxEast + "&CRS=" + crs + "&WIDTH=" + width
					+ "&HEIGHT=" + (int) height + "&LAYERS=OI.Mosaic.NaturalColor.Feathering&FORMAT=image/png";
		}

		ImageIcon i = null;
		i = new ImageIcon(new URL(urlGetMap));
		while (i.getImageLoadStatus() == MediaTracker.LOADING)
			;
//		System.out.println(urlGetMap);
		JLabel map = new JLabel(i);
		i.setImageObserver(map);
//-------------------------------------------------------------		
		System.out.println("Bild geladen");
		System.out.println("Seitenverhältnis: 1:" + verhaeltnis);
		System.out.println("Bildformat: " + width + "x" + (int) height);
		System.out.println(urlGetMap);
		// double Test=this.DeltaX-this.DeltaY;
		// System.out.println("TESTWERT "+Test);
//---------------------- return: --------------------------------	
		return map;
	}
}
