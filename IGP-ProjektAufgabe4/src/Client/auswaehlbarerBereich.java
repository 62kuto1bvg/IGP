package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
		g2.drawLine(100, 100, 500, 500);
	
		/////////Rechnung für Quadratischen Auschnitt////////
		
	
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

	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	
		
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
