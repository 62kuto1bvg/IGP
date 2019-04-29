package Client;

import java.awt.Component;
import java.awt.MediaTracker;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LoadKartenBild{
	String crs;
	double minx, miny, maxx, maxy, verhaeltnis;
	static int width;
	static int height;
	double DeltaX,DeltaY;
	int widthRandlos;
//-------------------------- Konstruktor: ----------------------------------------------------------------------------------	
	public LoadKartenBild(String crs, double minx, double miny, double maxx, double maxy, double verhaeltnis,int width) {
		super();
		this.crs = crs;
		this.minx = minx;
		//this.miny = miny;
		this.maxx = maxx;
		//this.maxy = maxy;
		this.verhaeltnis = verhaeltnis;
		this.width=width;
		
		//Behelf um Voerst den Kartenausschnitt auf DIN Format zu bekommen
		
		this.DeltaX=maxx-minx;
		this.DeltaY=maxy-miny;	
		
		double Verhal=DeltaX/Math.sqrt(2);
		double Abzug=(DeltaX-Verhal)/2;
		
		this.miny=miny+Abzug;
		this.maxy=maxy-Abzug;
	}
//------------------------- Methode: ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		
	public Component showMap() throws IOException 
	{					
		if(crs.equalsIgnoreCase("EPSG:4326")) {
			width=2600;
			widthRandlos=(int)width-40;
			height=(int)(width/(Math.sqrt(2)-40))
					;
			}
			else {
				//width = 1000;
				widthRandlos=((int)width)-40;
				height =(int) (width/(Math.sqrt(2))-40);
				
			}
		
		String urlGetMap = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&VERSION=1.3.0&request=GetMap&BBOX="
		+minx+","+miny+","+maxx+","+maxy+"&CRS="+crs+"&WIDTH="+widthRandlos+"&HEIGHT="+ (int)height+
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
		System.out.println(urlGetMap);
		//double Test=this.DeltaX-this.DeltaY;
		//System.out.println("TESTWERT   "+Test);
//---------------------- return: --------------------------------	
		return map;
	}
}
