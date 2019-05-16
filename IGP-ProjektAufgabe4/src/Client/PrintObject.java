package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrintObject implements Printable {

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		if(pageIndex > 0) {
			
			return NO_SUCH_PAGE;
			
		}
		
		Graphics2D g2d  = (Graphics2D)graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		Druckausgabe.KartenblattDruck.paint(g2d);
		//Druckuebersicht.Kartenbild.paint(g2d);
	
		return PAGE_EXISTS;
	 
		
	}

}
