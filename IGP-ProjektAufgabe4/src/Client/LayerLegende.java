package Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LayerLegende extends JPanel {
	int Legendenhoehe=this.getHeight();
	int Legendenbreite=this.getHeight();
public LayerLegende() {
	this.setLayout(null);
	this.setBackground(Color.WHITE);
	this.setBorder(BorderFactory.createLineBorder(Color.black));
}


	public void paintComponent(Graphics g) {
	
	super.paintComponent(g);

	Graphics2D g2de = (Graphics2D) g;
	g2de.drawLine(0, 0, 100, 100);

	
}
}
