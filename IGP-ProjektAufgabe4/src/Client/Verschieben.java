package Client;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class Verschieben implements MouseListener,MouseMotionListener {

	

  static   ArrayList<Component>Kartenelemente = new ArrayList<>();
	
    private int X,Y;
    
	public Verschieben(Component... pns) {
		for (Component panel : pns) {
			panel.addMouseListener(this);
			panel.addMouseMotionListener(this);
			Kartenelemente.add(panel);
			
			Client.Druckuebersicht.Kartenblatt.repaint();
			
		}
	}
	
	
	@Override
	public String toString() {
		return "Verschieben [X=" + X + ", Y=" + Y + "]";
	}


	public ArrayList<Component> getKartenelemente() {
		return Kartenelemente;
	}

	public void setKartenelemente(ArrayList<Component> kartenelemente) {
		Kartenelemente = kartenelemente;
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		X= e.getX();
		Y=e.getY();
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {

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
		e.getComponent().setLocation((e.getX()+e.getComponent().getX())-X,(e.getY()+e.getComponent().getY())-Y );
		
	Client.Druckuebersicht.Kartenblatt.repaint();
}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
