package Client;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;

public class CreateWindow {
	String url = "http://cidportal.jrc.ec.europa.eu/copernicus/services/ows/wms/public/core003?service=WMS&request=GetCapabilities";
	
	static JFrame frame = new JFrame();
	JPanel panelTextfields = new JPanel();
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

     panelMap.setBounds(350, 50, 650, 650);	//Position und Größe des Map-Panels
//--------------------------------------------------------------------------------------------------------     
     // Drop-Down-Menu Koord.System:
     GetBBox bBoxChoice = new GetBBox(url);												// zählen der verfügbaren CRS (hier 7)
     String [] auswahlCRS = new String [bBoxChoice.getBBoxfromXML().amountBBoxes()];	// Anlegen des Arrays
     for (int j=0; j<bBoxChoice.getBBoxfromXML().amountBBoxes(); j++) {					// Füttern des Arrays...
    	 auswahlCRS[j] = bBoxChoice.getBBoxfromXML().giveback(j).crs;
     }
     
     JComboBox<String> comboBoxCRS = new JComboBox<String>(auswahlCRS);   				// Anlegen des Drop-Down-Menüs...
     comboBoxCRS.setActionCommand("Drop-Down CRS");
     comboBoxCRS.setBounds(230, 60, 100, 20);
     frame.add(comboBoxCRS);
     	JLabel tfCRS = new JLabel("gewünschtes Koordinatensystem:");						// Textfeld vor dem Drop-Down-Menü...
     	tfCRS.setBounds(10, 60, 200, 20);
     	frame.add(tfCRS);
     
     // Anzeige für die Bounding Box Koordinaten:
  
    
     JTextField bBoxX1Feld = new JTextField();
     JTextField bBoxY1Feld = new JTextField();
     JTextField bBoxX2Feld = new JTextField();
     JTextField bBoxY2Feld = new JTextField();
     JTextField Massstab = new JTextField();
     Massstab.setBounds(1150, 300, 150, 40);
     bBoxX1Feld.setBounds(30, 90, 100, 20);
     bBoxY1Feld.setBounds(30, 110, 100, 20);
     bBoxX2Feld.setBounds(140, 90, 100, 20);
     bBoxY2Feld.setBounds(140, 110, 100, 20);
     Massstab.setActionCommand("Massstabszahl");
     bBoxX1Feld.setActionCommand("BBox Xmin geändert");
     bBoxY1Feld.setActionCommand("BBox Ymin geändert");
     bBoxX2Feld.setActionCommand("BBox Xmax geändert");
     bBoxY2Feld.setActionCommand("BBox Ymax geändert");
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
     	infoBBOXaenderung.setText("<html><body>Beachte: Alle Änderungen der BBox-Koordinaten müssen mit Enter bestätigt werden. Anschließend muss die Karte neu geladen werden</body></html>");
     	infoBBOXaenderung.setBounds(30, 130, 300, 60);
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
     frame.add(buttonOst);														// dem Panel hinzufügen    
     
     
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
     buttonSued.setActionCommand("Nach Süden");
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
     JButton BereichAuswaehlen = new JButton(">fenster wählen ");
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
          
//------------------------------------------------    
// Fenster öffnen:
   frame.setVisible(true); 
   
//---------------------------------------------------ActionListener------------------------------------------------------------------------------------------------------------------------------------------------------
   	 //Listener anlegen:        
     ActionListenerMap mapActionListener = new ActionListenerMap(panelMap, frame, buttonOst, buttonWest, buttonNord, buttonSued, zoomIn, zoomOut, PrintToPDF, comboBoxCRS,BereichAuswaehlen, bBoxX1Feld, bBoxY1Feld, bBoxX2Feld, bBoxY2Feld, infoBBOXaenderung,AuswahlFormat,MassstabOK,Massstab);
     
     //Registrierung aller Objekte, die "abgehört" werden sollen:
    
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