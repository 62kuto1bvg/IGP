package Client;

public class BBoxGetKoord {
	
	String crs;
	double minEast, minNorth, maxEast, maxNorth;
	
	
	public BBoxGetKoord(String crs, double minEast, double minNorth, double maxEast, double maxNorth) {
		super();
		this.crs = crs;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
	}

	public BBoxGetKoord() {
		// TODO Auto-generated constructor stub
	}

	public String getCrs() {
		return crs;
	}

	public void setCrs(String crs) {
		this.crs = crs;
	}

	public double getminEast() {
		return minEast;
	}

	public void setminEast(double minEast) {
		this.minEast = minEast;
	}

	public double getminNorth() {
		return minNorth;
	}

	public void setminNorth(double minNorth) {
		this.minNorth = minNorth;
	}

	public double getmaxEast() {
		return maxEast;
	}

	public void setmaxEast(double maxEast) {
		this.maxEast = maxEast;
	}

	public double getmaxNorth() {
		return maxNorth;
	}

	public void setmaxNorth(double maxNorth) {
		this.maxNorth = maxNorth;
	}

	@Override
	public String toString() {
		return "\nBoundingBox [crs=" + crs + ", minEast=" + minEast + ", minNorth=" + minNorth + ", maxEast=" + maxEast + ", maxNorth=" + maxNorth + "]";
	}
}