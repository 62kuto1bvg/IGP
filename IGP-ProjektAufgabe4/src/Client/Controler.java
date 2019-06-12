package Client;

import java.io.IOException;

public class Controler {
    public static void main(String[] args) throws IOException
    {
//    	// Fenster öffnen:
    	CreateWindow window = new CreateWindow();
    	window.openWindow();
    	
    	
    	String url = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&request=GetCapabilities";
    	GetMetadata test = new GetMetadata(url);
    	test.getMetafromXML();
    }
}