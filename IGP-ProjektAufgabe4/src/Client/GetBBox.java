package Client;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetBBox {
	
	String url;
	
	String crs="";
	double minx=0,miny=0,maxx=0,maxy=0;
	 
	public GetBBox(String url) {
		super();
		this.url = url;
	}
	
	public BBoxList getBBoxfromXML() {
		
		BBoxList BBoxes = new BBoxList();		
		try {
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.parse(url);
			 doc.getDocumentElement().normalize();
			 
			 //
			 NodeList layerList = doc.getElementsByTagName("Layer");
			 for (int i = 0; i < layerList.getLength(); i++) {
				 Node layerNode = layerList.item(i);
				 if (layerNode.getNodeType() == Node.ELEMENT_NODE) {					 
					 // verfügbare Raumbezugsysteme und BoundingBoxes auslesen:
					 NodeList childList = layerNode.getChildNodes();
					 for (int j = 0; j < childList.getLength(); j++) {
						 
						if(childList.item(j).getNodeName().equalsIgnoreCase("CRS")) {
//							System.out.println(childList.item(j).getTextContent());
						}
						 
						if(childList.item(j).getNodeName().equalsIgnoreCase("BoundingBox")) {
							NamedNodeMap bbAttrList = childList.item(j).getAttributes();								
							for (int z = 0; z < 5; z++) {
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("CRS")) {
									crs = bbAttrList.item(z).getTextContent();}
								
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("maxy")) {
									if (crs.equalsIgnoreCase("EPSG:4326")){
										maxx = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {maxy = Double.parseDouble(bbAttrList.item(z).getTextContent());}
								}								
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("maxx")) {
									if (crs.equalsIgnoreCase("EPSG:4326")){
										maxy = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {maxx = Double.parseDouble(bbAttrList.item(z).getTextContent());}
								}								
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("miny")) {
									if (crs.equalsIgnoreCase("EPSG:4326")){
										minx = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {miny = Double.parseDouble(bbAttrList.item(z).getTextContent());}
								}																
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("minx")) {
									if (crs.equalsIgnoreCase("EPSG:4326")){
										miny = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {minx = Double.parseDouble(bbAttrList.item(z).getTextContent());}
								}																	
							}
							System.out.println("BoundingBox "+crs+": "+" "+minx+"/"+miny+"  "+maxx+"/"+maxy); // Testausgabe
							BBoxGetKoord bbox = new BBoxGetKoord(crs, minx, miny, maxx, maxy);							
							BBoxes.add(bbox);
						}
					 }				 
				 }
			 }
		
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		return BBoxes;
	} 
}