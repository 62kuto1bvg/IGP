package Client;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LayerLegende extends JPanel {
int LinksObenX;
int LinksObenY;
int LinksUntenX;
int LinksUntenY;
int RechtsObenX;
int RechtsObenY;
int RechtsUntenX;
int RechtsUntenY;
public LayerLegende() {
	this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
	this.setBackground(Color.WHITE);
	
	
}
}
