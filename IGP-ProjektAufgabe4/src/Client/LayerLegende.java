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
	public void fuelleLegende(String crs, double minEast, double minNorth, double maxEast, double maxNorth,
			double verhaeltnis, int width, int Legendenbreite,int Legendenhoehe, int BreiteKartenausschnitt,double verhaeltnissUebersichtDruck) throws IOException {
		
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
		
		String Datum = (new SimpleDateFormat("yy.MM.dd").format(new java.util.Date()));
		JTextField Datumtext = new JTextField();
		Datumtext.setText("erzeugt am: "+Datum);
		Datumtext.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*73),(int) Legendenbreite-(Legendenbreite/10),(int)Legendenhoehe/30);
		Datumtext.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*12)));
		Datumtext.setBorder(new LineBorder(Color.WHITE, 2));
		
		
		JTextField Benutzer = new JTextField();
		String user = System.getProperty("user.name"); 
		Benutzer.setText("erstellt von: "+user);
		Benutzer.setBounds((int)Legendenbreite/10,(int) ((Legendenhoehe/100)*68),(int) Legendenbreite-(Legendenbreite/10),(int)Legendenhoehe/30);
		Benutzer.setFont(new Font("Courier", Font.BOLD,(int)(verhaeltnissUebersichtDruck*12)));
		Benutzer.setBorder(new LineBorder(Color.WHITE, 2));
		
		
				
	
		
		
		
		this.add(BildCopernicuslabel);
		this.add(BildHftlabel);
		this.add(Titel);
		this.add(Datumtext);
		this.add(Benutzer);
		
		
		
		

		
		
		this.setLayer(Hintergrund,100);
		this.setLayer(Kartenbild, 500);
		this.setLayer(rk,800);
		this.setLayer(BildCopernicuslabel, 800);
		this.setLayer(BildHftlabel, 800);
		this.setLayer(Titel, 800);
		this.setLayer(Datumtext, 800);
		this.setLayer(Benutzer, 800);
		
	}
	
	

}
