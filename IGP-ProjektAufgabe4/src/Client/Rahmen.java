package Client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class Rahmen extends JPanel {
int KarteBreite;
int KarteHoehe;
int Verhältnis;	
	public Rahmen(int KarteBreite, int KarteHoehe,int Verhältnis) {
		this.KarteBreite=KarteBreite;
		this.KarteHoehe=KarteHoehe;
	//	this.setBounds(0, 0, Width, Heigth);
		this.Verhältnis=Verhältnis;
		
	}

	public void paint(Graphics g) {
		
		
		Graphics2D g2 = (Graphics2D) g;
		//Rahmen um das Kartenbild
		g2.setStroke(new BasicStroke(Verhältnis*2));
		g2.drawRect(KarteBreite/40, KarteHoehe/40,( KarteBreite - (KarteBreite/20)), KarteHoehe - (KarteHoehe/20));
		
		//linie zwischenKartebild und legende
		g2.drawLine((KarteBreite / 10) * 8,KarteHoehe/40,(KarteBreite / 10) * 8,KarteHoehe - (KarteHoehe/40)-1);
		g2.setStroke(new BasicStroke(Verhältnis*3));
		g2.drawRect(KarteBreite/100, KarteHoehe/100,( KarteBreite - (KarteBreite/50)), KarteHoehe - (KarteHoehe/50));
		
		//Roter Kasten Minimap
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(Verhältnis*1));
	
	
	
		
		
	}
}
