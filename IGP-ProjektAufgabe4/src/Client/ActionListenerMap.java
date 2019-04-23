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
	
	ArrayList<String> exportListe = new ArrayList<String>();		// Anlegen einer Exportliste (wird sp�ter "gef�ttert" und ausgegeben)
	static String exportFormat;										// wird im ActionListener initialisiert und sp�ter ben�tigt f�r das schreiben der Ausgabedatei
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
		   
//------------------------------------------------------ Drop-Down-Men�s: --------------------------------------------------------------------------------------
		  // CRS w�hlen und passende Bounding Box abgreifen:
		  if(actionCommand.equals("Drop-Down CRS")) {
			  crs = (String) ((JComboBox<?>) actionEvent.getSource()).getSelectedItem();          
			  System.out.println("ausgew�hltes Koordinatensystem: "+crs);
                     
			  // Koordinaten der BBox zum gew�hlten CRS:
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
		  	  // Wenn Karte Laden-Button gedr�ckt wurde:
			  if(actionCommand.equals("lade Karte")) {
				  
				  // L�schen der aktuellen Karte:
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
			  if(actionCommand.equals("BBox Xmin ge�ndert")) {
				  minx = Integer.parseInt(bBoxX1Feld.getText());
			  }
			  if(actionCommand.equals("BBox Ymin ge�ndert")) {
				  miny = Integer.parseInt(bBoxY1Feld.getText());
			  }
			  if(actionCommand.equals("BBox Xmax ge�ndert")) {
				  maxx = Integer.parseInt(bBoxX2Feld.getText());
			  }
			  if(actionCommand.equals("BBox Ymax ge�ndert")) {
				  maxy = Integer.parseInt(bBoxY2Feld.getText());
			  }
//--------------------------------------------------------- Navigation: ----------------------------------------------------------------------------------------		 
			  // Wenn Ostpfeil-Button gedr�ckt wurde:	   
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
				  // L�schen der aktuellen Karte:
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

			  // Wenn Westpfeil gedr�ckt wurde:	 
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
				  // L�schen der aktuellen Karte:
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

			  // Wenn Nordpfeil gedr�ckt wurde:	 		   
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
			   	  // L�schen der aktuellen Karte:
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

			  // Wenn S�dpfeil gedr�ckt wurde:	 
			   if(actionCommand.equals("Nach S�den")) {
				   System.out.println("Gehe nach S�den");				   
				   
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
				   // L�schen der aktuellen Karte:
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
			   
			   // Wenn Zoom-In-Button (+) gedr�ckt wurde:	 		   
			   if(actionCommand.equals("zoom+")) {
				   System.out.println("Zoom in das Bild");
				   
				   double a = maxx - minx;	   
				   minx = minx + (a/5*verhaeltnis);
				   miny = miny + a/5;
				   maxx = maxx - (a/5*verhaeltnis);
				   maxy = maxy - a/5;			 
				   // L�schen der aktuellen Karte:
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
		 
			   // Wenn Zoom-Out (-) gedr�ckt wurde:		 
			   if(actionCommand.equals("zoom-")) {
				   System.out.println("Zoom aus dem Bild");
				   
				   
				   
				   double a = maxx - minx;
				   minx = minx - (a/3*verhaeltnis);
				   miny = miny - a/3;
				   maxx = maxx + (a/3*verhaeltnis);
				   maxy = maxy + a/3;	
				   
				   
				   
				   
				   
				   // L�schen der aktuellen Karte:
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

			   // Wenn DRUCKEN gedr�ckt wurde:
			   if(actionCommand.equals("druck")) {
				   System.out.println("Drucke PDF"); 
			  Druckuebersicht druckuebersicht = new Druckuebersicht();
			
				try {
					druckuebersicht.Oeffne�bersicht(crs,minx, miny, maxx, maxy,verhaeltnis);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			   }
			   
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------		 			   
	   } // Ende der Action-Command
}