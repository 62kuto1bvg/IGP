package Client;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;

public class CreateWindow {
	String url = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&request=GetCapabilities";
	
	static JFrame frame = new JFrame();
	JPanel panelTextfields = new JPanel();
	JPanel panelLogo = new JPanel();	//Panel f�r das Logo links oben
	JPanel panelMap = new JPanel(); 
	JPanel panelNavigation = new JPanel();
	static JTextArea cadDisplay = new JTextArea();
    static JButton PrintToPDF = new JButton("PDF drucken");
	static JButton loadMap = new JButton("Lade Karte");
	
	
	public void openWindow() throws IOException {
//------------------------------------------Ausgabefenster---------------------------------------------------------------------------------------------------		
// Fenstereigenschaften:
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setTitle("WMS Client");
//     frame.setSize(1000, 700);
//     frame.setLocation(230, 70);
     frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Vollbild

     panelMap.setBounds(350, 50, 650, 650);	//Position und Gr��e des Map-Panels
//--------------------------------------------------------------------------------------------------------     
    /*
     //Hintergrundbild
     ImageIcon imgLinkBackgr = new ImageIcon("C:/Users/timon/Documents/Studium/6. Semester/IGP/Logo/background.jpg");
	 JLabel bckgrbild = new JLabel("",imgLinkBackgr, JLabel.CENTER);
	 bckgrbild.setBounds(0,0,1200,700);
     frame.getContentPane().add(bckgrbild);
     */
     
     // Logo links oben
     panelLogo.setBounds(10, 10, 240, 180); //Position und Gr��e des Logo-Panels
     ImageIcon imgLink = new ImageIcon(getClass().getResource("FertigesLogo.png"));
	 JLabel logo = new JLabel(imgLink);
	 //logo.setVisible(true);
	 panelLogo.add(logo);
     frame.getContentPane().add(panelLogo);
     
   //--------------------------------------------------------------------------------------------------------     

     
     // Drop-Down-Menu Koord.System:
     GetBBox bBoxChoice = new GetBBox(url);												// z�hlen der verf�gbaren CRS (hier 7)
     String [] auswahlCRS = new String [bBoxChoice.getBBoxfromXML().amountBBoxes()];	// Anlegen des Arrays
     for (int j=0; j<bBoxChoice.getBBoxfromXML().amountBBoxes(); j++) {					// F�ttern des Arrays...
    	 auswahlCRS[j] = bBoxChoice.getBBoxfromXML().giveback(j).crs;
     }
     
     JComboBox<String> comboBoxCRS = new JComboBox<String>(auswahlCRS);   				// Anlegen des Drop-Down-Men�s...
     comboBoxCRS.setActionCommand("Drop-Down CRS");
     comboBoxCRS.setBounds(230, 200, 100, 20);
     frame.add(comboBoxCRS);
     	JLabel tfCRS = new JLabel("gew�nschtes Koordinatensystem:");						// Textfeld vor dem Drop-Down-Men�...
     	tfCRS.setBounds(10, 200, 200, 20);
     	frame.add(tfCRS);
     
     // Anzeige f�r die Bounding Box Koordinaten:
  
     JTextField bBoxX1Feld = new JTextField();
     JTextField bBoxY1Feld = new JTextField();
     JTextField bBoxX2Feld = new JTextField();
     JTextField bBoxY2Feld = new JTextField();
     JTextField Massstab = new JTextField();
     Massstab.setBounds(1150, 300, 150, 40);
     bBoxX1Feld.setBounds(30, 390, 100, 20);
     bBoxY1Feld.setBounds(30, 410, 100, 20);
     bBoxX2Feld.setBounds(140, 390, 100, 20);
     bBoxY2Feld.setBounds(140, 410, 100, 20);
     Massstab.setActionCommand("Massstabszahl");
     bBoxX1Feld.setActionCommand("BBox Xmin ge�ndert");
     bBoxY1Feld.setActionCommand("BBox Ymin ge�ndert");
     bBoxX2Feld.setActionCommand("BBox Xmax ge�ndert");
     bBoxY2Feld.setActionCommand("BBox Ymax ge�ndert");
     bBoxX1Feld.setVisible(false);
     bBoxY1Feld.setVisible(false);
     bBoxX2Feld.setVisible(false);
     bBoxY2Feld.setVisible(false);
     Massstab.setVisible(false);
     frame.add(bBoxX1Feld);
     frame.add(bBoxY1Feld);
     frame.add(bBoxX2Feld);
     frame.add(bBoxY2Feld);
     frame.add(Massstab);
     	JLabel infoBBOXaenderung = new JLabel();
     	infoBBOXaenderung.setText("<html><body>Beachte: Alle �nderungen der BBox-Koordinaten m�ssen mit Enter best�tigt werden. Anschlie�end muss die Karte neu geladen werden <br><br> Links Oben: min Ost; Rechts Oben: max Ost; Links Unten: min Nord; Rechts Oben: max Nord</body></html>");
     	infoBBOXaenderung.setBounds(30, 280, 300, 100);
     	frame.add(infoBBOXaenderung);
     	infoBBOXaenderung.setVisible(false);
     
     	
     
     	
     // Karte-Laden-Button:
 
     loadMap.setActionCommand("lade Karte");
     loadMap.setBounds(900, 10, 100, 40);
     frame.add(loadMap);
          
     // Navigations-Buttons:    
     BasicArrowButton buttonOst = new BasicArrowButton(BasicArrowButton.EAST);	// Pfeil-Butto erstellen (hier Pfeil nach rechts)
     buttonOst.setActionCommand("Nach Osten");									// Kommando setzen
     buttonOst.setBounds(1000, 350, 30, 100);  									// B utton dimensionieren und positionieren
     buttonOst.setBackground(Color.LIGHT_GRAY);									// Farbe
     buttonOst.setVisible(false);												// Button unsichtbar setzen
     frame.add(buttonOst);														// dem Panel hinzuf�gen    
     
     
     BasicArrowButton buttonWest = new BasicArrowButton(BasicArrowButton.WEST);
     buttonWest.setActionCommand("Nach Westen");
     buttonWest.setBounds(320, 350, 30, 100);
     buttonWest.setBackground(Color.LIGHT_GRAY);
     buttonWest.setVisible(false);
     frame.add(buttonWest);
     
     BasicArrowButton buttonNord = new BasicArrowButton(BasicArrowButton.NORTH);
     buttonNord.setActionCommand("Nach Norden");
     
     
     buttonNord.setBounds(650, 23, 100, 30);
     buttonNord.setBackground(Color.LIGHT_GRAY);
     buttonNord.setVisible(false);
     frame.add(buttonNord);
     
     BasicArrowButton buttonSued = new BasicArrowButton(BasicArrowButton.SOUTH);
     buttonSued.setActionCommand("Nach S�den");
     buttonSued.setBounds(650, 700, 100, 30);
     buttonSued.setBackground(Color.LIGHT_GRAY);
     buttonSued.setVisible(false);
     frame.add(buttonSued);
     
     // Zoom-Buttons:
     JButton zoomIn = new JButton("+");
     zoomIn.setActionCommand("zoom+");
     zoomIn.setBounds(1000, 600, 60, 40);
     zoomIn.setBackground(Color.LIGHT_GRAY);
     zoomIn.setVisible(false);
     frame.add(zoomIn);
     
     JButton zoomOut = new JButton("-");
     zoomOut.setActionCommand("zoom-");
     zoomOut.setBounds(1000, 642, 60, 40);
     zoomOut.setBackground(Color.LIGHT_GRAY);
     zoomOut.setVisible(false);
     frame.add(zoomOut); 
     
         // Auswahl-Button:
     JButton BereichAuswaehlen = new JButton(">fenster w�hlen ");
     BereichAuswaehlen.setActionCommand("Fenster waehlen");
     BereichAuswaehlen.setBounds(1150, 500, 150, 40);
     BereichAuswaehlen.setBackground(Color.LIGHT_GRAY);
     BereichAuswaehlen.setVisible(false);
     frame.add(BereichAuswaehlen);
    
     String comboBoxListe[] = {"DIN-A4","DIN-A3","DIN-A2","DIN-A1","DIN-A0"};
     JComboBox<String> AuswahlFormat = new JComboBox<String>(comboBoxListe);
     AuswahlFormat.setVisible(false);
     AuswahlFormat.setBounds(1150, 100, 150, 40);
     AuswahlFormat.setBackground(Color.LIGHT_GRAY);
     AuswahlFormat.setActionCommand("AuswaehlenDINFormat");
     frame.add(AuswahlFormat);
     TitledBorder title; //Umrandung ab hier
     title = BorderFactory.createTitledBorder("DIN-Format Auswahl");
     title.setTitleColor(Color.black);
     title.setBorder(BorderFactory.createRaisedBevelBorder());
//     title.setBorder(BorderFactory.createLoweredBevelBorder());
     AuswahlFormat.setBorder(title);             // Umrandung mit �berschrift
     
     // Textfeld Funktion erkl�ren
  	JLabel txtErkl�rung = new JLabel(
  			"<html><h3>Navigation:</h3> �ber Buttons oder mit festgehaltener Maus Fenster im Bild aufziehen<br><h3>DIN-Format Auswahl:</h3>Ausw�hlen des gew�nschten Druck-Formates"
  			+ "<br><h3>Ma�stab-Eingabe:</h3> Gew�nschten Ma�stab eingeben => Enter => Klick in das Bild <br>=> Kartenfenster wird angezeigt=> Doppelklick = gew�nschte Position festhalten</html>");						// Textfeld
  	txtErkl�rung.setBounds(1350, 100, 500, 220);
  	frame.add(txtErkl�rung);
    TitledBorder titleerkl;  // Umrandung ab hier
    titleerkl = BorderFactory.createTitledBorder("Benutzer-Erkl�rung");
    titleerkl.setTitleColor(Color.black);
    titleerkl.setBorder(BorderFactory.createRaisedBevelBorder());
//    titlemas.setBorder(BorderFactory.createLoweredBevelBorder());
    txtErkl�rung.setBorder(titleerkl);             // Umrandung mit �berschrift
    txtErkl�rung.setVisible(false);

     
     
     // printToPDF-Button:
     JButton PrintToPDF = new JButton("PDF drucken");
     PrintToPDF.setActionCommand("druck");
     PrintToPDF.setBounds(1150,800, 150, 40);
     PrintToPDF.setBackground(Color.LIGHT_GRAY);
     PrintToPDF.setVisible(false);
     frame.add(PrintToPDF);
     
     
     JButton MassstabOK = new JButton("Massstab bestaetigen");
     MassstabOK.setBounds(1300, 300, 200, 40);
     MassstabOK.setActionCommand("MassstabEingabe");
     MassstabOK.setBackground(Color.LIGHT_GRAY);
     MassstabOK.setVisible(false);
     frame.add(MassstabOK);
     TitledBorder titlemas;  // Umrandung ab hier
     titlemas = BorderFactory.createTitledBorder("Ma�stab Eingabe");
     titlemas.setTitleColor(Color.black);
     titlemas.setBorder(BorderFactory.createRaisedBevelBorder());
//     titlemas.setBorder(BorderFactory.createLoweredBevelBorder());
     Massstab.setBorder(titlemas);             // Umrandung mit �berschrift
          
//------------------------------------------------    
// Fenster �ffnen:
   frame.setVisible(true); 
   
//---------------------------------------------------ActionListener------------------------------------------------------------------------------------------------------------------------------------------------------
   	 //Listener anlegen:        
     ActionListenerMap mapActionListener = new ActionListenerMap(panelMap, frame, buttonOst, buttonWest, buttonNord, buttonSued, zoomIn, zoomOut, PrintToPDF, comboBoxCRS,BereichAuswaehlen, bBoxX1Feld, bBoxY1Feld, bBoxX2Feld, bBoxY2Feld, infoBBOXaenderung,AuswahlFormat,MassstabOK,Massstab,txtErkl�rung);
     
     //Registrierung aller Objekte, die "abgeh�rt" werden sollen:
    
     loadMap.addActionListener(mapActionListener);
     buttonOst.addActionListener(mapActionListener);
     buttonWest.addActionListener(mapActionListener);
     buttonNord.addActionListener(mapActionListener);
     buttonSued.addActionListener(mapActionListener);
     zoomIn.addActionListener(mapActionListener);
     zoomOut.addActionListener(mapActionListener); 
     PrintToPDF.addActionListener(mapActionListener);
     BereichAuswaehlen.addActionListener(mapActionListener);
     comboBoxCRS.addActionListener(mapActionListener);
     bBoxX1Feld.addActionListener(mapActionListener);
     bBoxY1Feld.addActionListener(mapActionListener);
     bBoxX2Feld.addActionListener(mapActionListener);
     bBoxY2Feld.addActionListener(mapActionListener);
     Massstab.addActionListener(mapActionListener);
     AuswahlFormat.addActionListener(mapActionListener);
     MassstabOK.addActionListener(mapActionListener);
     
//-------------------------------------------------------------------------------------------------------------------  
	}
	}
