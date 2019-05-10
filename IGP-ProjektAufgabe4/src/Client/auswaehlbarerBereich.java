package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class auswaehlbarerBereich extends JPanel implements MouseListener, MouseMotionListener {

	int startX=0;
	int startY=0;
	int endeX=0;
	int endeY=0;

	public auswaehlbarerBereich(Rectangle border) {
		
		this.setOpaque(false);
		
		this.setBounds(border);
		this.repaint();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
	}
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
	
		int deltax = startX-endeX;
		endeY = startY+deltax;
		
		g2.drawLine(startX, startY, endeX, startY);
		g2.drawLine(startX, startY, startX, endeY);
		g2.drawLine(startX, endeY, endeX, endeY);
		g2.drawLine(endeX, endeY, endeX, startY);
		this.repaint();

	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		startX= e.getX();
		startY=e.getY();
	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		

		Rectangle a=getBounds();
		int Hoehe =a.height;
		int breite=a.width;
		double MaxNorth= ActionListenerMap.getMaxNorth();
		double MaxEast= ActionListenerMap.getMaxEast();
		double MinNorth= ActionListenerMap.getMinNorth();
		double MinEast= ActionListenerMap.getMinEast();
		
		
		double deltaY=((MaxNorth-MinNorth)*endeY)/Hoehe;
		ActionListenerMap.setMaxNorth((ActionListenerMap.maxNorth-deltaY));

		double deltaY2=((MaxNorth-MinNorth)*(Hoehe-startY))/Hoehe;
		ActionListenerMap.setMinNorth((ActionListenerMap.minNorth+deltaY2));
		
		double deltaX=(startX*(MaxEast-MinEast)/breite);
		ActionListenerMap.setMinEast((ActionListenerMap.minEast+deltaX));
		
		double deltaX2=((breite-endeX)*(MaxEast-MinEast)/breite);
		ActionListenerMap.setMaxEast((ActionListenerMap.maxEast-deltaX2));
		
		CreateWindow.loadMap.doClick();		
		
		 startX=0;
		 startY=0;
		 endeX=0;
		 endeY=0;
		
		
		

	}	
		
	

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	endeX=e.getX();
	endeY=e.getY();
	
	//umrechnen des Kleinen fensters in neue BBboxkordinaten
	
	
	
}	
	
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	
//	int eX=e.getX();
//		int eY=e.getY();
//		System.out.println(eX);
//		System.out.println(eY);
	}
}
