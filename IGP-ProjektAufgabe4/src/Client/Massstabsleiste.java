package Client;

public class Massstabsleiste {

	double maxx;
	double maxy;
	double minx;
	double miny;
	double verhaeltnis;
	String crs;
	double DeltaX, DeltaY;

	public void erstelleMassstabsleiste(String crs, double minx, double miny, double maxx, double maxy,
			double verhaeltnis) {

		this.crs = crs;
		this.minx = minx;
		// this.miny = miny;
		this.maxx = maxx;
		// this.maxy = maxy;
		this.verhaeltnis = verhaeltnis;

		// Behelf um Voerst den Kartenausschnitt auf DIN Format zu bekommen

		DeltaX = maxx - minx;
		DeltaY = maxy - miny;

		double Verhal = DeltaX / Math.sqrt(2);
		double Abzug = (DeltaX - Verhal) / 2;

		this.miny = miny + Abzug;
		this.maxy = maxy - Abzug;

		// Umkreis des Breitengrades

		// Alpha ist der winkel zwischen Aeuquator und den punkt auf der Kugel
		int erdradius = 6371000;
		double alpha = ((maxy + miny) / 2);

		// Umkreis auf Breitengrad
		System.out.println("Alpha  " + alpha);

		double radiusUmkreis = Math.cos(alpha * ((2 * Math.PI) / 360)) * erdradius;
		System.out.println("radiusUmkreis  " + radiusUmkreis);

		// Winkel Bogensegment
		double beta = ((maxx - minx));

		System.out.println("Beta  " + beta);
		// Bogenlaenge

		double Bogenlaenge = ((radiusUmkreis * (Math.PI) * beta) / 180);

		System.out.println("Strecke:   " + Bogenlaenge);

	}
}