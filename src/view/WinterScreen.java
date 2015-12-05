package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class WinterScreen extends JFrame {
	boolean isWinter = false;
	public WinterScreen(boolean isWinter) {
		this.isWinter = !isWinter;
		goingUp = true;
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setOpacity(0);
		JTextArea winterText = null;
		if(this.isWinter)
			winterText = new JTextArea("Winter is upon us");
		else
			winterText = new JTextArea("\n\n\nThe ice starts to thaw...\nThe snow begins to melt...\nYou've survived the Winter");
		
		winterText.setFont(new Font("Old English Text MT",Font.CENTER_BASELINE,72));
		winterText.setOpaque(true);
		winterText.setEditable(false);
		this.add(winterText);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		swing.start();
		this.addWindowListener(new FrameListener());
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
				if(counter>=35) {
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
	private class FrameListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			requestFocus();
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			requestFocus();
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			requestFocus();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			requestFocus();
		}
		
	}
	  public static void main(String[] args) {
		WinterScreen screen = new WinterScreen(false);
	} 
}