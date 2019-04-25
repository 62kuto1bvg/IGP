package Client;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JColorChooser;

public class ActionListenerDruckuebersicht implements ActionListener {

	public void actionPerformed(ActionEvent actionEvent) {

		String actionCommand = actionEvent.getActionCommand();
		System.out.println("Geklickt: " + actionCommand);

//-------------------------------------------------------------------	   

		if (actionCommand.equals("Druckmenue")) {
			
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
		if (actionCommand.equals("ausw�hlen Farbe Nordstern")) {

			{
				JColorChooser Farben = new JColorChooser();

				//Client.Druckuebersicht.
				
				//
				//ausgewaehlteFarbe = Farben.showDialog(null, "Waehlen Sie die Stiftfarbe", Color.BLACK);

				
			}

		}
	}

}
