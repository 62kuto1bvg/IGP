package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

public class PrintObject implements Printable {

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// TODO Auto-generated method stub
		if (pageIndex > 0) {

			return NO_SUCH_PAGE;

		}

		Druckausgabe druckausgabe = new Druckausgabe();

		try {
			druckausgabe.erstelleDruckausgabe(Druckuebersicht.crs, Druckuebersicht.minEast, Druckuebersicht.minNorth,
					Druckuebersicht.maxEast, Druckuebersicht.maxNorth, Druckuebersicht.verhaeltnis,
					Druckuebersicht.nordpfeil);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int w = Druckausgabe.KartenblattDruck.getWidth();
		int h = Druckausgabe.KartenblattDruck.getHeight();
		// float quality = 1f;
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();

		// Male das JPanel in das BufferedImage


	        
	        
	        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	        System.out.println("ende");
	        
		return PAGE_EXISTS;

	}

}
