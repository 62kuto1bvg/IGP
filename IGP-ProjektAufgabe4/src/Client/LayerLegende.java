package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class LayerLegende extends JLayeredPane {

	String crs;
	double minEast, minNorth, maxEast, maxNorth, verhaeltnis;
	static int width;
	static int height;
	double DeltaX, DeltaY;
	int Legendenbreite;
	int BreiteKartenausschnitt;
	int Legendenhoehe;
	int xuntenKoordinate;
	int yuntenKoordinate;
	int xobenKoordinate;
	int yobenKoordinate;
	double verhaeltnissUebersichtDruck;
	double BreiteKarte;
	
	public void fuelleLegende(String crs, double minEast, double minNorth, double maxEast, double maxNorth,
			double verhaeltnis, int width, int Legendenbreite,int Legendenhoehe, int BreiteKartenausschnitt,double verhaeltnissUebersichtDruck,double BreiteKarte) throws IOException {
		
		this.setLayout(null);

		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		this.crs = crs;
		this.minEast = minEast;
		this.minNorth = minNorth;
		this.maxEast = maxEast;
		this.maxNorth = maxNorth;
		this.verhaeltnis = verhaeltnis;
		this.Legendenbreite = Legendenbreite;
		this.BreiteKartenausschnitt = BreiteKartenausschnitt;
		this.Legendenhoehe=Legendenhoehe;
		this.verhaeltnissUebersichtDruck=verhaeltnissUebersichtDruck;
		this.BreiteKarte=BreiteKarte;
		
		// Die legendenKoordinaten sind kleinermasstäblich. damit es übersichtlicher
		// wird.
		double Deltax = maxEast - minEast;
		double Deltay = maxNorth - minNorth;

		double minEastLegende = minEast - Deltax;
		double maxEastLegende = maxEast + Deltax;

		double minNorthLegende = minNorth - Deltay;
		double maxNorthLegende = maxNorth + Deltay;

		
		
		JPanel Hintergrund=new JPanel();
		Hintergrund.setLayout(null);
		Hintergrund.setBounds(0,0,this.getWidth(), this.getHeight());
		Hintergrund.setBorder(BorderFactory.createLineBorder(Color.black));
		Hintergrund.setBackground(Color.WHITE);
		Hintergrund.setVisible(true);
		
		JPanel Kartenbild = new JPanel();
		// int GroesseBild=(int)(Legendenbreite/5)*3;
		LoadKartenLegende newMap = new LoadKartenLegende(crs, minEastLegende, minNorthLegende, maxEastLegende,
				maxNorthLegende, verhaeltnis, BreiteKartenausschnitt);
		JLabel actualMap = (JLabel) newMap.showMap();
		actualMap.setBackground(Color.WHITE);
		Kartenbild.add(actualMap);
		Kartenbild.setBackground(Color.WHITE);		
		Kartenbild.setBounds((Legendenbreite / 5), (Legendenbreite / 5), (Legendenbreite / 5) * 3,
				(Legendenbreite / 5) * 3);
		Kartenbild.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		Kartenbild.setVisible(true);

		RoterKasten rk =new RoterKasten((Legendenbreite / 5) * 3,(Legendenbreite / 5) * 3);
		rk.setBounds((Legendenbreite / 5), (Legendenbreite / 5), (Legendenbreite / 5) * 3,
				(Legendenbreite / 5) * 3);
		rk.setVisible(true);
		this.add(Hintergrund);
		this.add(Kartenbild);
		this.add(rk);
		
		//Bilder einfügen
		
		Icon BildHft = new ImageIcon(getClass().getResource("LogoHFT.png"));	
		((ImageIcon) BildHft).setImage(((ImageIcon) BildHft).getImage().getScaledInstance((int)(Legendenbreite/10)*5,(int)(Legendenbreite/2.5),Image.SCALE_DEFAULT)); 
		JLabel BildHftlabel = new JLabel(BildHft);
		
		Icon BildCopernicus = new ImageIcon(getClass().getResource("copernicusLogo.png"));	
		((ImageIcon) BildCopernicus).setImage(((ImageIcon) BildCopernicus).getImage().getScaledInstance((int)(Legendenbreite/10)*7,(int)(Legendenbreite/2),Image.SCALE_DEFAULT)); 
		JLabel BildCopernicuslabel = new JLabel(BildCopernicus);
		BildCopernicuslabel.setVisible(true);
		
		BildCopernicuslabel.setBounds(Legendenbreite/8,(int) ((Legendenhoehe/10)*7), (int)(Legendenbreite/10)*7,(int)(Legendenbreite/2));
		
		BildHftlabel.setBounds(Legendenbreite/4,(int) ((Legendenhoehe/40)*36), (int)(Legendenbreite/10)*5,(int)(Legendenbreite/2.5));
		
		//////Textfelder/////////////////////////////////////////////////////
		
		//JTextField Titel = new JTextField("Luftbild Projekt Copernicus",Legendenbreite/3, (Legendenhoehe/5));
		JTextField Titel = new JTextField();
		Titel.setText("Luftbild Copernicus");
		Titel.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*25),(int) Legendenbreite-(Legendenbreite/10),(int)Legendenhoehe/30);
		Titel.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*12)));
		Titel.setBorder(new LineBorder(Color.WHITE, 2));
		
		    	
		//Für das unterstrichene
		Font Titelfont = Titel.getFont();
		Map attributes = Titelfont.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		Titel.setFont(Titelfont.deriveFont(attributes));
		
		//////Erzeugt am Textfeld
		
		String Datum = (new SimpleDateFormat("dd.MM.yy HH:mm").format(new java.util.Date()));
		JTextField Datumtext = new JTextField();
		Datumtext.setText("erzeugt am: "+Datum);
		Datumtext.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*73),(int) Legendenbreite-(Legendenbreite/10),(int)Legendenhoehe/30);
		Datumtext.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*8)));
		Datumtext.setBorder(new LineBorder(Color.WHITE, 2));
		
		
		JTextField Benutzer = new JTextField();
		String user = System.getProperty("user.name"); 
		Benutzer.setText("erstellt von: "+user);
		Benutzer.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*68),(int) Legendenbreite-(Legendenbreite/10),(int)Legendenhoehe/30);
		Benutzer.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*8)));
		Benutzer.setBorder(new LineBorder(Color.WHITE, 2));
		
		////Metadaten auslesen und ablegen///////////////////
		String url = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&request=GetCapabilities";
    	GetMetadata Meta = new GetMetadata(url);
    	Meta.getMetafromXML();
		
		JTextArea TTitle = new JTextArea();
		// Für einen mehrzeiligen Text ist eine JTextArea zu benutzen
		String Title = Meta.LayerTitle.toString();
		for (int i = 0; i < Title.length(); i++) {
				if (i % 30 == 0 ) {
					Title= (Title.substring(0, i))+"\n"+(Title.substring(i, Title.length()));
					//System.out.println(Kombi);
				}
							
		}
    	String Ausgabetitle = ("Arbeitstitel: "+Title);
    	TTitle.setText(Ausgabetitle);
    	TTitle.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*28),(int) Legendenbreite-(Legendenbreite/11),(int)Legendenhoehe/(350/100));		// Anpassung der Tiefe für mehrzeiligen Text
    	TTitle.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*8)));		
    	TTitle.setBorder(new LineBorder(Color.WHITE, 2));
    	System.out.println("Titel"+Ausgabetitle);
    																																																			
    	String Contact = Meta.ContactPerson.toString();
    	
    	for (int i = 0; i < Contact.length(); i++) {
			if (i % 30 == 0 ) {
				Contact= (Contact.substring(0, i))+"\n"+(Contact.substring(i, Contact.length()));
				//System.out.println(Kombi);
			}
						
	}	
    	
    	String Ausgabecontact = ("Kontakt: "+ Contact);
		JTextArea CContact = new JTextArea();
		CContact.setText(Ausgabecontact);
		CContact.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*35),(int) Legendenbreite-(Legendenbreite/11),(int)Legendenhoehe/(350/100));		// Anpassung der Tiefe für mehrzeiligen Text
		CContact.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*8)));		
		CContact.setBorder(new LineBorder(Color.WHITE, 2));
		System.out.println("Kontakt" + Ausgabecontact);
		
		
		
		
		
		//Berechnung Massstab///////////////////////////////////////////////////////
		
		
		
		int erdradius = 6371000;
		double alpha = ((maxNorth + minNorth) / 2);

		// Umkreis auf Breitengrad

		double radiusUmkreis = Math.cos(alpha * ((2 * Math.PI) / 360)) * erdradius;

		// Winkel Bogensegment
		double beta = ((maxEast - minEast));

		
		int Bogenlaengegesamt = (int) ((radiusUmkreis * (Math.PI) * beta) / 180);
		
	
		String Massstabszahl;
		
		if(BreiteKarte==0) {
			Massstabszahl="n/A";
		}
		
		
		else {
		long Massstabszahldouble=(long) (Bogenlaengegesamt/(BreiteKarte/100));
		Massstabszahl=String.valueOf(Massstabszahldouble);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////
		


		JTextField Scale = new JTextField();
	
		Scale.setText("Massstab 1:"+Massstabszahl);
		Scale.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*65),(int) Legendenbreite-(Legendenbreite/10),(int)Legendenhoehe/30);
		Scale.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*8)));
		Scale.setBorder(new LineBorder(Color.WHITE, 2));
		
		
		this.add(BildCopernicuslabel);
		this.add(BildHftlabel);
		this.add(Titel);
		this.add(TTitle);
		this.add(CContact);
		this.add(Datumtext);
		this.add(Benutzer);
		this.add(Scale);
		
		

		this.setLayer(Hintergrund,100);
		this.setLayer(Kartenbild, 500);
		this.setLayer(rk,800);
		this.setLayer(BildCopernicuslabel, 800);
		this.setLayer(BildHftlabel, 800);
		this.setLayer(Titel, 800);
		this.setLayer(Datumtext, 803);
		this.setLayer(Benutzer, 802);
		this.setLayer(Scale, 801);
		this.setLayer(TTitle, 800);
		this.setLayer(CContact, 801);
	}
	
	

}
