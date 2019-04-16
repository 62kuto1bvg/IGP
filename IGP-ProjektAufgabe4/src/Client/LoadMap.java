package Client;

import java.awt.Component;
import java.awt.MediaTracker;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LoadMap{
	String crs;
	double minx, miny, maxx, maxy, verhaeltnis;
	static int width;
	static double height;
	
//-------------------------- Konstruktor: ----------------------------------------------------------------------------------	
	public LoadMap(String crs, double minx, double miny, double maxx, double maxy, double verhaeltnis) {
		super();
		this.crs = crs;
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
		this.verhaeltnis = verhaeltnis;
	}
//------------------------- Methode: ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	public Component showMap() throws IOException 
	{			
		width = 650;
		height = width*verhaeltnis; 
		
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
		System.out.println("Bild geladen");
		System.out.println("Seitenverhältnis: 1:"+verhaeltnis);
		System.out.println("Bildformat: "+width+"x"+(int)height);		
//---------------------- return: --------------------------------	
		return map;
	}
}
