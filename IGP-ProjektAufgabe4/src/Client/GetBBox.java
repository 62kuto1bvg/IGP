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
	double minNorth=0, maxNorth=0, minEast=0, maxEast=0;
	 
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
									if (crs.equalsIgnoreCase("CRS:84")){
										maxNorth = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {maxEast = Double.parseDouble(bbAttrList.item(z).getTextContent());
										maxEast = 90;
									}
								}
									
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("miny")) {
									if (crs.equalsIgnoreCase("CRS:84")){
										minNorth = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {minEast = Double.parseDouble(bbAttrList.item(z).getTextContent());
										minEast = -80;
									}
								}
								
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("maxx")) {
									if (crs.equalsIgnoreCase("CRS:84")){
										maxEast = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {maxNorth = Double.parseDouble(bbAttrList.item(z).getTextContent());
										maxNorth = 80;
									}
								}
								
								if(bbAttrList.item(z).getNodeName().equalsIgnoreCase("minx")) {
									if (crs.equalsIgnoreCase("CRS:84")){
										minEast = Double.parseDouble(bbAttrList.item(z).getTextContent());}
									else {minNorth = Double.parseDouble(bbAttrList.item(z).getTextContent());
										minNorth = 20;
									}
								}																	
							}
														
							BBoxGetKoord bbox = new BBoxGetKoord(crs, minEast, minNorth, maxEast, maxNorth);							
							BBoxes.add(bbox);
						}
					 }
				 }
			 }
		
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		System.out.println(BBoxes); // Testausgabe
		return BBoxes;
	}
}