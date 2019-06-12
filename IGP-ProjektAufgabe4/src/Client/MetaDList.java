package Client;

import java.util.ArrayList;

public class MetaDList {

	ArrayList<Metadata> MetaDList;

	public MetaDList() {
		super();
		this.MetaDList = new ArrayList<Metadata>();
	}
	
	public void add(Metadata bbox) {
		this.MetaDList.add(bbox);
	}

	@Override
	public String toString() {
		return "MetaDList [MetaDList=" + MetaDList + "]";
	}
}
