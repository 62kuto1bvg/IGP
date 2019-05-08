package Client;

import java.awt.Component;
import java.awt.MediaTracker;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LoadMap{
	String crs;
	double minEast, minNorth, maxEast, maxNorth, verhaeltnis;
	static int width;
	static double height;
	
//-------------------------- Konstruktor: ----------------------------------------------------------------------------------	
	public LoadMap(String crs, double minEast, double minNorth, double maxEast, double maxNorth, double verhaeltnis) {
		super();
		this.crs = crs;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
	}
//------------------------- Methode: ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	public Component showMap() throws IOException 
	{		
		double minx=0, miny=0, maxx=0, maxy=0;
		if(crs.equalsIgnoreCase("CRS:84")) {
			width = 650;
			height = width;
			
			minx = minEast;
			miny = minNorth;
			maxx = maxEast;
			maxy = maxNorth;			
		}
		else {
			minx = minNorth;
			miny = minEast;
			maxx = maxNorth;
			maxy = maxEast;
			
			width = 1300;
			height = width*0.5;
		}
		
		String urlGetMap = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&VERSION=1.3.0&request=GetMap&BBOX="
				+minx+","+miny+","+maxx+","+maxy+"&CRS="+crs+"&WIDTH="+width+"&HEIGHT="+ (int)height+
				"&LAYERS=OI.Mosaic.NaturalColor.Feathering&FORMAT=image/png";
		
		ImageIcon i = null;
		i = new ImageIcon(new URL(urlGetMap));
		while ( i.getImageLoadStatus() == MediaTracker.LOADING );
//		System.out.println(urlGetMap);
		JLabel map = new JLabel(i);
		i.setImageObserver(map);
//-------------------------------------------------------------		
//		System.out.println("Bild geladen");
	//	System.out.println("Seitenverhältnis: 1:"+verhaeltnis);
	//	System.out.println("Bildformat: "+width+"x"+(int)height);
		System.out.println("AAAAAAAAAAAA"+urlGetMap);
//---------------------- return: --------------------------------	
		return map;
	}
}
