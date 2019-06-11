package Client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class RoterKasten extends JPanel {
int Breite;
int Hoehe;
	

public RoterKasten(int Breite, int Hoehe) {
	this.Breite=Breite;
	this.Hoehe=Hoehe;
	
	

}
public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	g2.setColor(Color.RED);
	g2.setStroke(new BasicStroke(2));
	g2.drawRect((int)(Breite/3), (int)(Hoehe/3), (int)(Breite/3), (int)(Hoehe/3));


}}
