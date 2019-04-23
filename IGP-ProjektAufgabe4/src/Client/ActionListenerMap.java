package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.text.BadLocationException;

public class ActionListenerMap extends CreateWindow implements ActionListener {   
	
	static String crs;
	static double minx;
	static double miny;
	static double maxx;
	static double maxy;
	double verhaeltnis;
	
	JPanel panelMap;
	JFrame frame;
	BasicArrowButton buttonOst, buttonWest, buttonNord, buttonSued;
	JButton zoomIn, zoomOut, printToPDF;
	JComboBox<?> comboBoxCRS;
	JLabel infoBBOXaenderung;
	JTextField bBoxX1Feld, bBoxY1Feld, bBoxX2Feld, bBoxY2Feld;
	JScrollPane display;
	
	ArrayList<String> exportListe = new ArrayList<String>();		// Anlegen einer Exportliste (wird später "gefüttert" und ausgegeben)
	static String exportFormat;										// wird im ActionListener initialisiert und später benötigt für das schreiben der Ausgabedatei
//--------------------------------------------- Konstruktor: -----------------------------------------------------------------------------------------------------	
	   public ActionListenerMap(JPanel panelMap, JFrame frame,
			BasicArrowButton buttonOst, BasicArrowButton buttonWest, BasicArrowButton buttonNord, BasicArrowButton buttonSued,
			JButton zoomIn, JButton zoomOut, JButton printToPDF,
			JComboBox<?> comboBoxCRS,
			JTextField bBoxX1Feld, JTextField bBoxY1Feld, JTextField bBoxX2Feld, JTextField bBoxY2Feld, JLabel infoBBOXaenderung) 
	   {
		super();
		this.panelMap = panelMap;
		this.frame = frame;
		this.buttonOst = buttonOst;
		this.buttonWest = buttonWest;
		this.buttonNord = buttonNord;
		this.buttonSued = buttonSued;
		this.zoomIn = zoomIn;
		this.zoomOut = zoomOut;
		this.printToPDF = printToPDF;
		this.comboBoxCRS = comboBoxCRS;
		this.bBoxX1Feld = bBoxX1Feld;
		this.bBoxY1Feld = bBoxY1Feld;
		this.bBoxX2Feld = bBoxX2Feld;
		this.bBoxY2Feld = bBoxY2Feld;
		this.infoBBOXaenderung = infoBBOXaenderung;		
	   }
//--------------------------------------- Action-Command:
	   @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent actionEvent) {				// Ein ActionListenerMap muss IMMER "actionPerformed" implementieren !
		   
		   String actionCommand = actionEvent.getActionCommand();
		   System.out.println("Geklickt: " + actionCommand);			// Konsolenausgabe, welche Aktion geklickt wurde
		   
//------------------------------------------------------ Drop-Down-Menüs: --------------------------------------------------------------------------------------
		  // CRS wählen und passende Bounding Box abgreifen:
		  if(actionCommand.equals("Drop-Down CRS")) {
			  crs = (String) ((JComboBox<?>) actionEvent.getSource()).getSelectedItem();          
			  System.out.println("ausgewähltes Koordinatensystem: "+crs);
                     
			  // Koordinaten der BBox zum gewählten CRS:
			  GetBBox newBBoxList = new  GetBBox(url);          
			  BBoxList koordList = new BBoxList();
			  koordList = newBBoxList.getBBoxfromXML();
           
			  for(int k=0; k<koordList.amountBBoxes(); k++)
				  if (koordList.giveback(k).crs.equals(crs) ) {
					  minx = koordList.giveback(k).minx;
					  miny = koordList.giveback(k).miny;
					  maxx = koordList.giveback(k).maxx;
					  maxy = koordList.giveback(k).maxy;
					  
					  
        		   
					  System.out.println("BBox: "+minx+"/"+miny+"  "+maxx+"/"+maxy);
					  if(minx <0 && miny <0) {
						  verhaeltnis = (maxx+(0-minx))/(maxy+(0-miny));
					  }
					  if(minx >0 && miny <0) {
						  verhaeltnis = (maxx-minx)/(maxy+(0-miny));
					  }
					  if(minx <0 && miny >0) {
						  verhaeltnis = (maxx+(0-minx))/(maxy-miny);
					  }
					  else verhaeltnis = (maxx-minx)/(maxy-miny);
					  
				  }    
		  }	  
//--------------------------------------------------------- Karte laden: --------------------------------------------------------------------------------------		  
		  	  // Wenn Karte Laden-Button gedrückt wurde:
			  if(actionCommand.equals("lade Karte")) {
				  
				  // Löschen der aktuellen Karte:
		 	 	 panelMap.removeAll();
		 	 	 
		 	 	 // Laden der neuen Karte:
		 	 	 try {
		 	 		 LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
		 	 		 JLabel actualMap = (JLabel) newMap.showMap();
		 	 		 panelMap.add(actualMap);			    
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
		 	 	 
		 	 	 // Karte auf Fenster setzen:
		 	 	 frame.add(panelMap); 
		 	 	 frame.add(panelNavigation);
		 	 	 frame.setVisible(true);
		 	 
		 	 	 // Navigations- und DRUCKEN-Buttons sichtbar setzen:
		 	 	 buttonOst.setVisible(true);
		 	 	 buttonWest.setVisible(true);
		 	 	 buttonNord.setVisible(true);
		 	 	 buttonSued.setVisible(true);
		 	 	 zoomIn.setVisible(true);
		 	 	 zoomOut.setVisible(true);
		 	 	 printToPDF.setVisible(true);
		 	 	 
		 	 	 // Bounding-Box-Felder sichtbar setzten und aktuelle BBox-Koordinaten ausgeben:
		 	 	 bBoxX1Feld.setVisible(true);
		 	 	 bBoxY1Feld.setVisible(true);
		 	 	 bBoxX2Feld.setVisible(true);
		 	 	 bBoxY2Feld.setVisible(true);
		 	 	 	bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
		 	 	 	bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
		 	 	 	bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
		 	 	 	bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000);		 	 	 	
		 	 	 infoBBOXaenderung.setVisible(true);
			  }		
			  
			  // Benutzerdefiniertes Anpassen der Bounding Box:
			  if(actionCommand.equals("BBox Xmin geändert")) {
				  minx = Integer.parseInt(bBoxX1Feld.getText());
			  }
			  if(actionCommand.equals("BBox Ymin geändert")) {
				  miny = Integer.parseInt(bBoxY1Feld.getText());
			  }
			  if(actionCommand.equals("BBox Xmax geändert")) {
				  maxx = Integer.parseInt(bBoxX2Feld.getText());
			  }
			  if(actionCommand.equals("BBox Ymax geändert")) {
				  maxy = Integer.parseInt(bBoxY2Feld.getText());
			  }
//--------------------------------------------------------- Navigation: ----------------------------------------------------------------------------------------		 
			  // Wenn Ostpfeil-Button gedrückt wurde:	   
			  if(actionCommand.equals("Nach Osten")) {
				  System.out.println("Gehe nach Osten");	// Befehl an Konsole ausgeben				  				  
					  
				  // Berechnen der neuen BBox-Koord.:
				  if (crs.equalsIgnoreCase("EPSG:4326")){
					  double a = maxy - miny;			
					  miny = miny + a/25;
					  maxy = maxy + a/25;			  
				  }
				  else { //bei CRS:84
					  double a = maxx - minx;
					  minx = minx + a/3;
					  maxx = maxx + a/3;
				  }
				  // Löschen der aktuellen Karte:
				  panelMap.removeAll();			 
				  // neue Karte (mit neuer BBox) laden:
				  try {
					  LoadMap newMap = new LoadMap(crs, minx, miny, maxx, maxy, verhaeltnis); 				
					  JLabel actualMap = (JLabel) newMap.showMap();
					  panelMap.add(actualMap);
			    
					  frame.repaint();
					  frame.setVisible(true);
				  		} catch (IOException e) {
				  			// TODO Auto-generated catch block
				  			e.printStackTrace();
				  			}
				  		// BBox-Koord.-Feld aktualisieren:
				  		bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
				  		bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
				  		bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
				  		bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000);
			  }

			  // Wenn Westpfeil gedrückt wurde:	 
			  if(actionCommand.equals("Nach Westen")) {
				  System.out.println("Gehe nach Westen");	
				  
				  // Berechnen der neuen BBox-Koord.:
				  if (crs.equalsIgnoreCase("EPSG:4326")){
					  double a = maxy - miny;
					   miny = miny - a/25;
					   maxy = maxy - a/25;				  
				  }
				  else { //bei CRS:84
					  double a = maxx - minx;
					  minx = minx - a/3;
					  maxx = maxx - a/3; 
				  }
				  // Löschen der aktuellen Karte:
				  panelMap.removeAll();
				  // neue Karte (mit neuer BBox) laden:
				  try {
					  LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
					  JLabel actualMap = (JLabel) newMap.showMap();
					  panelMap.add(actualMap);
			    
					  frame.repaint();
					  frame.setVisible(true);
				  		} catch (IOException e) {
				  			// TODO Auto-generated catch block
				  			e.printStackTrace();
				  			}
				  
				  		bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
				  		bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
				  		bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
				  		bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000); 		
			  }

			  // Wenn Nordpfeil gedrückt wurde:	 		   
			  if(actionCommand.equals("Nach Norden")) {
				  System.out.println("Gehe nach Norden");
				  
				  // Berechnen der neuen BBox-Koord.:
				  if (crs.equalsIgnoreCase("EPSG:4326")){
					  double a = maxx - minx;
					  minx = minx + a/20;
				   	  maxx = maxx + a/20;			  
				  }				  
				  else { //bei CRS:84
					  double a = maxy - miny;			
					  miny = miny + a/3;
					  maxy = maxy + a/3; 
				  }
			   	  // Löschen der aktuellen Karte:
			   	  panelMap.removeAll();
			   	  // neue Karte (mit neuer BBox) laden:
				  try {
					  LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
					  JLabel actualMap = (JLabel) newMap.showMap();
					  panelMap.add(actualMap);
				    
	 			  	  frame.repaint();
	 			  	  frame.setVisible(true);
	 				   	} catch (IOException e) {
	 						// TODO Auto-generated catch block
	 						e.printStackTrace();
	 						}
				  
				  		bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
				  		bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
				  		bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
				  		bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000);  		
	 			 }

			  // Wenn Südpfeil gedrückt wurde:	 
			   if(actionCommand.equals("Nach Süden")) {
				   System.out.println("Gehe nach Süden");				   
				   
				   // Berechnen der neuen BBox-Koord.:
				   if (crs.equalsIgnoreCase("EPSG:4326")){
					   double a = maxx - minx;
					   minx = minx - a/20;
					   maxx = maxx - a/20;			  
					  }
					  else { //bei CRS:84
						  double a = maxy - miny;
						  miny = miny - a/3;
						  maxy = maxy - a/3;
					  }	 			 
				   // Löschen der aktuellen Karte:
				   panelMap.removeAll();
				   // neue Karte (mit neuer BBox) laden:
				   try {
					   LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
					   JLabel actualMap = (JLabel) newMap.showMap();
					   panelMap.add(actualMap);
				    
					   frame.repaint();
					   frame.setVisible(true);
				   		} catch (IOException e) {
	 						// TODO Auto-generated catch block
	 						e.printStackTrace();
	 						}
				   
				   		bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
				   		bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
				   		bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
				   		bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000);   		
	 			 }
			   
			   // Wenn Zoom-In-Button (+) gedrückt wurde:	 		   
			   if(actionCommand.equals("zoom+")) {
				   System.out.println("Zoom in das Bild");
				   
				   double a = maxx - minx;	   
				   minx = minx + (a/5*verhaeltnis);
				   miny = miny + a/5;
				   maxx = maxx - (a/5*verhaeltnis);
				   maxy = maxy - a/5;			 
				   // Löschen der aktuellen Karte:
				   panelMap.removeAll();
				   // neue Karte (mit neuer BBox) laden:
				   try {
					   LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
					   JLabel actualMap = (JLabel) newMap.showMap();
					   panelMap.add(actualMap);
				    
					   frame.repaint();
					   frame.setVisible(true);
				   		} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
				   
				   		bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
				   		bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
				   		bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
				   		bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000);   		
				 }	
		 
			   // Wenn Zoom-Out (-) gedrückt wurde:		 
			   if(actionCommand.equals("zoom-")) {
				   System.out.println("Zoom aus dem Bild");
				   
				   
				   
				   double a = maxx - minx;
				   minx = minx - (a/3*verhaeltnis);
				   miny = miny - a/3;
				   maxx = maxx + (a/3*verhaeltnis);
				   maxy = maxy + a/3;	
				   
				   
				   
				   
				   
				   // Löschen der aktuellen Karte:
				   panelMap.removeAll();
				   // neue Karte (mit neuer BBox) laden:
				   try {
					   LoadMap newMap = new LoadMap(crs,minx, miny, maxx, maxy, verhaeltnis); 				
					   JLabel actualMap = (JLabel) newMap.showMap();
					   panelMap.add(actualMap);
				   
					   frame.repaint();
					   frame.setVisible(true);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
				   
				   		bBoxX1Feld.setText(""+Math.rint(minx*1000)/1000);
				   		bBoxY1Feld.setText(""+Math.rint(miny*1000)/1000);
				   		bBoxX2Feld.setText(""+Math.rint(maxx*1000)/1000);
				   		bBoxY2Feld.setText(""+Math.rint(maxy*1000)/1000);	   		
				  } 

			   // Wenn DRUCKEN gedrückt wurde:
			   if(actionCommand.equals("druck")) {
				   System.out.println("Drucke PDF"); 
			  Druckuebersicht druckuebersicht = new Druckuebersicht();
			
				try {
					druckuebersicht.OeffneÜbersicht(crs,minx, miny, maxx, maxy,verhaeltnis);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			   }
			   
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------		 			   
	   } // Ende der Action-Command
}