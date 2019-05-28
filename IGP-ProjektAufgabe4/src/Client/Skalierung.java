package Client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Skalierung  extends JPanel implements MouseListener, MouseMotionListener {
	int Breite;
	int Hoehe;
	int LinksObenX=0;
	int LinksObenY=0;
	int LinksUntenX=0;
	int LinksUntenY=0;
	int RechtsObenX=0;
	int RechtsObenY=0;
	int RechtsUntenX=0;
	int RechtsUntenY=0;
	
	int xMaus=0;
	int yMaus=0;
	public void erzeugeSkalierung(Rectangle Bounds){

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.Breite=Bounds.width;
		this.Hoehe=Bounds.height;
		this.setBounds(Bounds);
		
	
		int RpHoehe=Bounds.height;
		int RpBreite=Bounds.width;
		
		this.LinksObenX=Bounds.x;
		this.LinksObenY=Bounds.y;
		
		this.LinksUntenX=Bounds.x;
		this.LinksUntenY=Bounds.y+RpHoehe;
		
		this.RechtsObenX=Bounds.x+RpBreite;
		this.RechtsObenY=Bounds.y;
		
		this.RechtsUntenX=Bounds.x+RpBreite;
		this.RechtsUntenY=Bounds.y+RpHoehe;
		

	}
	
	public void paint(Graphics g) {
	
		System.out.println("LinksObenX"+LinksObenX);
		System.out.println("LinksObenY"+LinksObenY);
	if(Druckuebersicht.LegendeSkalierungsmodus.contains("An")) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawLine(100, 100, 500, 500);		
	
		if(((LinksObenX-xMaus)<15)&&((xMaus-LinksObenX)>(-15))&&(LinksObenY-yMaus)<15&&((yMaus-LinksObenY)>(-15))) {
			
			
			g2.drawOval(LinksObenX, LinksObenY, 15, 15);
			System.out.println("BINGOOOO");
			
		}else {}
	
	
	
	
	}
	else {};
	
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		xMaus=e.getX();
		yMaus=e.getY();
		
		System.out.println(xMaus);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
