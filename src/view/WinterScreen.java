package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

public class WinterScreen extends JFrame {
	boolean isWinter = false;
	public WinterScreen(boolean isWinter) {
		this.isWinter = isWinter;
		goingUp = isWinter;
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setOpacity(0);
		JTextField winterText = new JTextField("Winter is upon us");
		winterText.setFont(new Font("Old English Text MT",Font.CENTER_BASELINE,72));
		winterText.setOpaque(true);
		winterText.setEditable(false);
		this.add(winterText);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		swing.start();
	
	}
	Timer swing = new Timer(50, new swagListener());
	private class closeIt implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}
	private boolean goingUp;
	private float opacity = 0;
	private class swagListener implements ActionListener {
		private boolean first = true;
		//  counter will give time to load
		int counter = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(opacity>1)
				opacity = 1;
			if(opacity<0)
				opacity = 0;
			setOpacity(opacity);
			if(goingUp)
				opacity+=.05;
			else
				opacity-=.05;
			if(counter == 0) {
				if((opacity>1)||(opacity<0)) {
					if(!first) {
						swing.stop();
						dispose();
					}
					counter ++;
				}
			}
			else {
				counter++;
				if(counter>=11) {
					goingUp = !goingUp;
					first = false;
				if(opacity>1)
					opacity = 1;
				if(opacity<0)
					opacity = 0;
				counter = 0;
				}		
			}
		}
	}
	/*  public static void main(String[] args) {
		WinterScreen screen = new WinterScreen(true);
	} */
}