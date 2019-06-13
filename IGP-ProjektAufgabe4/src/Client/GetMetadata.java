package Client;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetMetadata {

	String url;
	String LayerTitle;
	String Abstract;
	String ContactPerson;
	
	public GetMetadata (String url) {
		super();
		this.url = url;
	}
	
public void getMetafromXML() {
		
		MetaDList Meta = new MetaDList();
		Metadata Legende = new Metadata();
		try {
			 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.parse(url);
			 doc.getDocumentElement().normalize();
			
//-----------------------------------------------------------------------------------------------Beschreibender Text und Titel auslesen------------------------------------------------------------------------			 
			 NodeList layerList = doc.getElementsByTagName("Service");
			 for (int i = 0; i < layerList.getLength(); i++) {
				 Node layerNode = layerList.item(i);
				 if (layerNode.getNodeType() == Node.ELEMENT_NODE) {					 
					 // verf�gbare Raumbezugsysteme und BoundingBoxes auslesen:
					 NodeList childList = layerNode.getChildNodes();
					 for (int j = 0; j < childList.getLength(); j++) {
//						 System.out.println(childList.item(j));
						 if(childList.item(j).getNodeName().equalsIgnoreCase("Abstract")) {
							Abstract=(childList.item(j).getTextContent());
//							System.out.println(Abstract);
							Legende.setAbstract(Abstract);
							}
						 if(childList.item(j).getNodeName().equalsIgnoreCase("Title")) {
							 LayerTitle=(childList.item(j).getTextContent());
//							 System.out.println(LayerTitle);
							 Legende.setLayerTitle(LayerTitle);
						 }
					 }		 
		        }
			 }
//-----------------------------------------------------------------------------------------------Kontaktdaten auslesen----------------------------------------------------------------------------------------------			 
			 NodeList contactList = doc.getElementsByTagName("ContactPersonPrimary");
			 for (int i = 0; i < contactList.getLength(); i++) {
				 Node contactNode = contactList.item(i);
				 if (contactNode.getNodeType() == Node.ELEMENT_NODE) {					 
					  NodeList childList = contactNode.getChildNodes();
					  for (int j = 0; j < childList.getLength(); j++) {
						  if(childList.item(j).getNodeName().equalsIgnoreCase("ContactPerson")) {
						  	ContactPerson=(childList.item(j).getTextContent());
//						  	System.out.println(ContactPerson);
						  	Legende.setContactPerson(ContactPerson); 
						  }
					  }
					}
			 	}
			 Meta.add(Legende);
			 System.out.println(Legende);
		} catch (Exception e) {
			e.printStackTrace();
		}
			 
	}
}
