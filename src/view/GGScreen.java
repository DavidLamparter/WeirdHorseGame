package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GGScreen extends JFrame {
	public GGScreen(int time, int wintersSurvived) {
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setOpacity(0);
		JButton text = new JButton("                            You've made a valliant effort but in the end it didn't even matter\n"
				+ "\n                            You've Survived " + wintersSurvived + " winters and lasted " + time/60 + " minutes");
		text.setOpaque(true);
		this.add(text);
		text.addActionListener(new closeIt());
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
	private class swagListener implements ActionListener {
		float opacity = 0;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setOpacity(opacity);
			opacity+=.05;
			if(opacity>1)
				swing.stop();
		}
	}
}
