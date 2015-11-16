package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PicPanel extends JPanel{
	
	Image img;
	
	public PicPanel(String fileName){
		try {
			img= ImageIO.read(new File(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	@ Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, 100, 99, Color.WHITE, null);
	}

}
