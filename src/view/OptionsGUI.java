/*================================================================================================
|   Assignment:  FINAL PROJECT - Settlement Management
|      Authors:  David Lamparter (Lamparter@email.arizona.edu)
|                Kyle Grady (kgrady1@email.arizona.edu)
|    			 Kyle DeTar (kdeTar@email.arizona.edu)
|	  			 Brett Cohen (brett7@email.arizona.edu)
|                       
|       Course:  335
|   Instructor:  R. Mercer
|           PM:  Sean Stephens
|     Due Date:  12/9/15
|
|  Description:  This program . . .
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/


package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Game;

public class OptionsGUI extends JFrame {
	JButton close = new JButton("Exit the Game");
	JButton closeThis = new JButton("Close this window");
	JButton saver = new JButton("Save?");
	JButton oneX = new JButton("1X");
	JButton twoX = new JButton("2X");
	JButton fiveX = new JButton("5X");
	boolean oneON = true;
	boolean twoON = false;
	boolean fiveON = false;
	JPanel menuPanel = new JPanel();
	//  JButton loader = new JButton("Load?");
	private boolean wasSaved = false;
	SettlementGUI caller;
	private JButton options = new JButton("<HTML><b>Options</b></HTML>");
	MaxSize maxWindow = new MaxSize();
	
	
	public OptionsGUI(SettlementGUI caller) {
		this.caller = caller;
		this.setSize(caller.getWidth()/3, (int)(caller.getHeight()/1.5));
		this.setLocation(caller.getWidth()/3, caller.getHeight()/6);
		this.setLayout(null);
		this.setUndecorated(true);
		
		saver.addActionListener(new Saver());
		saver.setSize(this.getWidth()-20, caller.getHeight()/12);
		saver.setLocation(10, 20);
		saver.setBackground(new Color(210,180,140));
		saver.setVisible(false);
		
		//  SAVAGRY WILL BE ADDED
		/* loader.addActionListener(new LoaderListener());
		loader.setSize(this.getWidth()-20, caller.getHeight()/12);
		loader.setLocation(10, 40 + saver.getHeight());
		loader.setVisible(false); */
		
		closeThis.addActionListener(maxWindow);
		closeThis.setSize(this.getWidth()-20, caller.getHeight()/12);
		closeThis.setLocation(10, 40 + saver.getHeight()); //+ loader.getHeight());
		closeThis.setBackground(new Color(210,180,140));
		closeThis.setVisible(false);
		
		close.addActionListener(new CloseListener());
		close.setSize(this.getWidth()-20, caller.getHeight()/12);
		close.setLocation(10, saver.getHeight() + 60 + closeThis.getHeight()); // + loader.getHeight();
		close.setBackground(new Color(210,180,140));
		close.setVisible(false);
		
		//  SPEED BUTTONS
		JPanel speedHolder = new JPanel();
		speedHolder.setLayout(new FlowLayout());
		oneX.addActionListener(new SupaFastListener());
		twoX.addActionListener(new SupaFastListener());
		fiveX.addActionListener(new SupaFastListener());
		speedHolder.setSize(this.getWidth()-20, caller.getHeight()/12);
		speedHolder.setLocation(10, saver.getHeight() + 80 + closeThis.getHeight() + close.getHeight());
		
		speedHolder.add(oneX);
		oneX.setPreferredSize(new Dimension(speedHolder.getWidth()/3, speedHolder.getHeight()));
		oneX.setBackground(new Color(210,180,140));
		speedHolder.add(twoX);
		twoX.setBackground(new Color(210,180,140));
		twoX.setPreferredSize(new Dimension(speedHolder.getWidth()/3, speedHolder.getHeight()));
//		speedHolder.add(fiveX);
		fiveX.setPreferredSize(new Dimension(speedHolder.getWidth()/3, speedHolder.getHeight()));
		fiveX.setBackground(new Color(210,180,140));
		
		this.setSize(150, 50);
		//setLocation(caller.getWidth()-getWidth(), 0);
		this.setLocation(0, caller.getHeight()-getHeight());
		
		if(oneON) {
			oneX.setEnabled(false);
		}
		else if(twoON) {
			twoX.setEnabled(false);
		}
		else if(fiveON) {
			fiveX.setEnabled(false);
		}
		
		options.setLocation(0, 0);
		options.setSize(this.getSize());
		options.addActionListener(maxWindow);
		options.setBackground(new Color(127, 106, 69));
		options.setForeground(Color.LIGHT_GRAY);
		menuPanel.setBackground(new Color(127, 106, 69));
		speedHolder.setBackground(new Color(127, 106, 69));
		menuPanel.setSize(this.getSize());
		menuPanel.setLayout(null);
		menuPanel.add(saver);
		menuPanel.add(closeThis);
		this.add(options);
		menuPanel.add(close);
		menuPanel.add(speedHolder);
		this.add(menuPanel);
		//  this.add(loader);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	private class SupaFastListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			oneX.setEnabled(true);
			twoX.setEnabled(true);
			fiveX.setEnabled(true);
			
			if(arg0.getSource().equals(oneX)) {
				caller.getGame().changeTimers(Game.NORMAL_SPEED);
				oneX.setEnabled(false);
			}
			if(arg0.getSource().equals(twoX)) {
				caller.getGame().changeTimers(Game.NORMAL_SPEED*2);
				twoX.setEnabled(false);
			}
			if(arg0.getSource().equals(fiveX)) {
				caller.getGame().changeTimers(Game.NORMAL_SPEED*5);
				fiveX.setEnabled(false);
			}
		}
	}
	private class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(wasSaved) {
				caller.CloseEverything();
			}
			else {
				setAlwaysOnTop(false);
				int reply = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to exit without saving?", "Save Game?", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.NO_OPTION)
					setAlwaysOnTop(true);
				else if(reply == JOptionPane.YES_OPTION) {
					caller.CloseEverything();
				}
				else
					setAlwaysOnTop(true);
			}
		}
	}
	private class MaxSize implements ActionListener {
		boolean max = false;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			max=!max;
			if(max) {
				setSize(caller.getWidth()/3, (int)(caller.getHeight()/2));
				setLocation(caller.getWidth()/3, caller.getHeight()/6);
				menuPanel.setSize(caller.getWidth()/3, (int)(caller.getHeight()/2));
			}
			else {
				setSize(150, 50);
				setLocation(0, caller.getHeight()-getHeight());
				//  setLocation(caller.getWidth()-getWidth(), 0);
			}
			menuPanel.setVisible(max);
			options.setVisible(!max);
			close.setVisible(max);
			closeThis.setVisible(max);
			saver.setVisible(max);
			//  loader.setVisible(max);
			saver.setText("Save?");
			//  loader.setText("Load?");
			wasSaved = false;
		}
	}
	private class Saver implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//  Do saving stuff!
			try {
			FileOutputStream fos = new FileOutputStream("GameData");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(caller.getGame());
			fos.close();
			oos.close();
			}
			catch(Exception saveProbs) {
				saveProbs.printStackTrace();
			}
			
			saver.setText("Saved!");
			wasSaved = true;
		}
	}
	private class LoaderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			FileInputStream fos = new FileInputStream("GameData");
			ObjectInputStream oos = new ObjectInputStream(fos);
			//caller.loadTheSave((Game)oos.readObject());
			caller.revalidate();
			fos.close();
			oos.close();
			}
			catch(Exception saveProbs) {
				saveProbs.printStackTrace();
			}
			//  loader.setText("Your save has been loaded");
		}
	}
}
