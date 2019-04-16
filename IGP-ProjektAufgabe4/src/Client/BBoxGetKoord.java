package Client;

public class BBoxGetKoord {
	
	String crs;
	double minx, miny, maxx, maxy;
	
	
	public BBoxGetKoord(String crs, double minx, double miny, double maxx, double maxy) {
		super();
		this.crs = crs;
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
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

	public double getminx() {
		return minx;
	}

	public void setminx(double minx) {
		this.minx = minx;
	}

	public double getminy() {
		return miny;
	}

	public void setminy(double miny) {
		this.miny = miny;
	}

	public double getmaxx() {
		return maxx;
	}

	public void setmaxx(double maxx) {
		this.maxx = maxx;
	}

	public double getmaxy() {
		return maxy;
	}

	public void setmaxy(double maxy) {
		this.maxy = maxy;
	}

	@Override
	public String toString() {
		return "\nBoundingBox [crs=" + crs + ", minx=" + minx + ", miny=" + miny + ", maxx=" + maxx + ", maxy=" + maxy + "]";
	}
}