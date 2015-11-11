package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class OptionsGUI extends JFrame {
	JButton close = new JButton("Close the window");
	SettlementGUI caller;
	public OptionsGUI(SettlementGUI caller) {
		this.caller = caller;
		this.setSize(caller.getWidth()/3, (int)(caller.getHeight()/1.5));
		this.setLocation(caller.getWidth()/3, caller.getHeight()/6);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		close.addActionListener(new CloseListener());
		this.add(close, BorderLayout.CENTER);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	private class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			caller.CloseEverything();
		}
	}
}
