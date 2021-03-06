package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class ActionListenerDruckuebersicht implements ActionListener {

	public void actionPerformed(ActionEvent actionEvent) {

		String actionCommand = actionEvent.getActionCommand();
		System.out.println("Geklickt: " + actionCommand);

//-------------------------------------------------------------------	   

		if (actionCommand.equals("Druckmenue")) {
			
				Druckausgabe druckausgabe =new Druckausgabe();
				
				try {
					druckausgabe.erstelleDruckausgabe(Druckuebersicht.crs, Druckuebersicht.minEast,Druckuebersicht.minNorth, Druckuebersicht.maxEast, Druckuebersicht.maxNorth, Druckuebersicht.verhaeltnis,Druckuebersicht.nordpfeil);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			PrinterJob printjob = PrinterJob.getPrinterJob();

			printjob.setJobName("Kartendruck");
			printjob.setPrintable(new PrintObject());

				if(printjob.printDialog())
				{
					try {
						printjob.print();
					} catch (PrinterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				
				
		}
		
		
			
			if (actionCommand.equals("Speicher")) {
			
			

					JFrame BitteWarten = new JFrame("Bitte warten");

					BitteWarten.setBounds(200, 200, 400,100);
					JTextField Warntext = new JTextField("Bitte warten");
					Warntext.setText("Karte wird Heruntergeladen");
					Warntext.setBounds(50, 250, 400, 400);
					BitteWarten.add(Warntext);
					Warntext.setVisible(true);
					BitteWarten.setVisible(true);
				
				Druckausgabe druckausgabe =new Druckausgabe();
				
				try {
					druckausgabe.erstelleDruckausgabe(Druckuebersicht.crs, Druckuebersicht.minEast,Druckuebersicht.minNorth, Druckuebersicht.maxEast, Druckuebersicht.maxNorth, Druckuebersicht.verhaeltnis,Druckuebersicht.nordpfeil);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BitteWarten.setVisible(false);
				
				JFileChooser chooser2 = new JFileChooser();
				try {chooser2.setCurrentDirectory(new File(".").getCanonicalFile());

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				int returnVal2 = chooser2.showSaveDialog(null);

				if (returnVal2 == JFileChooser.APPROVE_OPTION) {

					File Ausgabedatei = chooser2.getSelectedFile().getAbsoluteFile();

					String fileName = Ausgabedatei.getPath();

					if (fileName.substring(fileName.length() - 4, fileName.length()).contains(".")) {
					}

				
					else {
						fileName = fileName + ".png";
					}
					System.out.println(fileName);
				
				
			        // Erstelle ein BufferedImage 
			        int w =Druckausgabe.KartenblattDruck.getWidth(); 
			        int h = Druckausgabe.KartenblattDruck.getHeight(); 
			       // float quality = 1f; 
			        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB); 
			        Graphics2D g2d = bi.createGraphics(); 

			        // Male das JPanel in das BufferedImage 
			     
			        Druckausgabe.KartenblattDruck.paint(g2d); 

			        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("png"); 
			        ImageWriter writer = (ImageWriter) iter.next(); 
			        ImageWriteParam iwp = writer.getDefaultWriteParam(); 
//			        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); 
//			        iwp.setCompressionQuality(quality); 

			        try { 
			            FileImageOutputStream fos = new FileImageOutputStream(new File(fileName)); 
			            writer.setOutput(fos); 
			            IIOImage img = new IIOImage((RenderedImage) bi, null, null); 
			            writer.write(null, img, iwp); 

			        } catch (Exception ex) { 
			            ex.printStackTrace(); 
			        } 
					}

				}

			

	if(actionCommand.equals("auswählen Farbe Nordstern"))

	{
		{
			JColorChooser Farben = new JColorChooser();
			Client.Druckuebersicht.ausgewaehlteFarbeNordstern = Farben.showDialog(null, "Waehlen Sie die Stiftfarbe",
					Color.BLACK);
			Client.Druckuebersicht.Kartenblatt.repaint();
		}
	}

	if(actionCommand.equals("auswählen Farbe Massstab"))
	{
		{
			JColorChooser Farben = new JColorChooser();
			Client.Druckuebersicht.ausgewaehlteFarbeMassstabsleiste = Farben.showDialog(null,
					"Waehlen Sie die Stiftfarbe", Color.BLACK);
			Client.Druckuebersicht.Kartenblatt.repaint();
		}
	}

	if(actionCommand.equals("auswählen Farbe Gitter"))
	{
		{
			JColorChooser Farben = new JColorChooser();
			Client.Druckuebersicht.ausgewaehlteFarbeKoordinatengitter = Farben.showDialog(null,
					"Waehlen Sie die Stiftfarbe", Color.BLACK);
			Client.Druckuebersicht.Kartenblatt.repaint();
		}
	}
	
	
	
	if(actionCommand.equals("LegendeSkalieren"))
	{
		{
			if(Druckuebersicht.LegendeSkalierungsmodus.contains("Aus")) {
				Druckuebersicht.LegendeSkalierungsmodus="An";
				Druckuebersicht.ausgewählteSkalierung=Color.DARK_GRAY;
				
				Client.Druckuebersicht.Kartenblatt.repaint();
				Client.Druckuebersicht.FensterDruckuebersicht.repaint();
			
				System.out.println(Druckuebersicht.LegendeSkalierungsmodus);
			}
			

			else if(Druckuebersicht.LegendeSkalierungsmodus.contains("An")) {
				Druckuebersicht.LegendeSkalierungsmodus="Aus";
				Druckuebersicht.ausgewählteSkalierung=Color.LIGHT_GRAY;
				
				Client.Druckuebersicht.Kartenblatt.repaint();
				Client.Druckuebersicht.FensterDruckuebersicht.repaint();
				System.out.println(Druckuebersicht.LegendeSkalierungsmodus);
			}
			
			
		}
	}
	
	
	
	
	
	
	
}}
