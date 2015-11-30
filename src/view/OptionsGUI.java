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

import model.Game;

public class OptionsGUI extends JFrame {
	JButton close = new JButton("Exit the Game");
	JButton closeThis = new JButton("Close this window");
	JButton saver = new JButton("Save?");
	JButton loader = new JButton("Load?");
	private boolean wasSaved = false;
	SettlementGUI caller;
	private JButton options = new JButton("Options");
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
		saver.setVisible(false);
		
		loader.addActionListener(new LoaderListener());
		loader.setSize(this.getWidth()-20, caller.getHeight()/12);
		loader.setLocation(10, 40 + saver.getHeight());
		loader.setVisible(false);
		
		closeThis.addActionListener(maxWindow);
		closeThis.setSize(this.getWidth()-20, caller.getHeight()/12);
		closeThis.setLocation(10, 60 + saver.getHeight() + loader.getHeight());
		closeThis.setVisible(false);
		
		close.addActionListener(new CloseListener());
		close.setSize(this.getWidth()-20, caller.getHeight()/12);
		close.setLocation(10, saver.getHeight() + 80 + loader.getHeight() + closeThis.getHeight());
		close.setVisible(false);
		
		this.setSize(150, 50);
		//setLocation(caller.getWidth()-getWidth(), 0);
		this.setLocation(0, caller.getHeight()-getHeight());
		
		options.setLocation(0, 0);
		options.setSize(this.getSize());
		options.addActionListener(maxWindow);
		options.setBackground(new Color(25,255,140));
		//options.setForeground(new Color(25,255,140));
		
		this.add(saver);
		this.add(closeThis);
		this.add(options);
		this.add(close);
		this.add(loader);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
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
				setSize(caller.getWidth()/3, (int)(caller.getHeight()/1.5));
				setLocation(caller.getWidth()/3, caller.getHeight()/6);
			}
			else {
				setSize(150, 50);
				setLocation(0, caller.getHeight()-getHeight());
				//  setLocation(caller.getWidth()-getWidth(), 0);
			}
			options.setVisible(!max);
			close.setVisible(max);
			closeThis.setVisible(max);
			saver.setVisible(max);
			loader.setVisible(max);
			saver.setText("Save?");
			loader.setText("Load?");
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
			caller.loadTheSave((Game)oos.readObject());
			caller.revalidate();
			fos.close();
			oos.close();
			}
			catch(Exception saveProbs) {
				saveProbs.printStackTrace();
			}
			loader.setText("Your save has been loaded");
		}
	}
}
