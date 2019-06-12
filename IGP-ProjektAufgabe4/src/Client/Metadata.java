package Client;

public class Metadata {
	
	
	String LayerTitle;
	String Abstract;
	String ContactPerson;

public Metadata (String LayerTitle, String Abstract,String ContactPerson) {
	 super();
	this.Abstract=Abstract;
	this.ContactPerson=ContactPerson;
	this.LayerTitle=LayerTitle;
}

public String getLayerTitle() {
	return LayerTitle;
}

public void setLayerTitle(String layerTitle) {
	LayerTitle = layerTitle;
}

public String getAbstract() {
	return Abstract;
}

public void setAbstract(String abstract1) {
	Abstract = abstract1;
}

public String getContactPerson() {
	return ContactPerson;
}

public void setContactPerson(String contactPerson) {
	ContactPerson = contactPerson;
}

@Override
public String toString() {
	return "Metadata [LayerTitle=" + LayerTitle + ", Abstract=" + Abstract + ", ContactPerson=" + ContactPerson + "]";
}


}
