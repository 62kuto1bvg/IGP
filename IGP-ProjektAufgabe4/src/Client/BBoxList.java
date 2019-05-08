package Client;

import java.util.ArrayList;

import Client.BBoxGetKoord;

public class BBoxList
{
	ArrayList<BBoxGetKoord> BBoxList;

	// Standardkonstruktor:
	public BBoxList() {
		super();
		this.BBoxList = new ArrayList<BBoxGetKoord>();
	}
	
	// add BoundingBox:
	public void add(BBoxGetKoord bbox) {
		this.BBoxList.add(bbox);
	}

	// toString-Methode:	
	@Override
	public String toString() {
		return "BBoxList [BBoxList=" + BBoxList + "]";
	}
	
	public BBoxGetKoord giveback (int i) {
		BBoxGetKoord getBB = new BBoxGetKoord();
		getBB = BBoxList.get(i);
		return getBB;
	}
	
	public int amountBBoxes () {
		int sizeBBoxList = BBoxList.size();
		return sizeBBoxList;
	}
}