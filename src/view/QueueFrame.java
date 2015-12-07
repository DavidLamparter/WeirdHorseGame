package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import model.Job;
import model.WorkQueue;

public class QueueFrame extends JFrame implements Observer {
	private SettlementGUI caller;
	private WorkQueue model;
	private DefaultListModel<String> queueListModel;
	private JList<String> displayList;
	private JLabel title;
	private JPanel panel;
	
	public QueueFrame(SettlementGUI caller) {
		this.caller = caller;
		this.setLocation(caller.getWidth()-100, 0);
		this.setSize(100, 200);
		this.setUndecorated(true);
		title = new JLabel();
		title.setText("<HTML><U><b>Worker Queue</b></U></HTML>");
//		title.setFont(font);
		title.setForeground(Color.LIGHT_GRAY);
		panel = new JPanel();
		panel.setBackground(new Color(127, 106, 69));
		panel.add(title);
//		this.setLayout(null);
		queueListModel = new DefaultListModel();
		displayList = new JList();
		displayList.setSize(this.getSize());
		displayList.setLocation(0, 0);
		displayList.setModel(queueListModel);
		//this.setOpacity(0);
		//  displayList.setOpaque(false);
		displayList.setBackground(new Color(127, 106, 69));
		displayList.setFont(displayList.getFont().deriveFont(Font.BOLD, 14f));
		displayList.setForeground(Color.LIGHT_GRAY);
		panel.add(displayList);
		this.add(panel);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	    queueListModel = new DefaultListModel<String>();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		model = (WorkQueue) arg0;
	    ArrayList<Job> temp = model.getQueue();
	    queueListModel.clear();
	    for(int i = 0; i < temp.size(); i++) {
	    	queueListModel.addElement(temp.get(i).getName());
	    }
	    displayList.setModel(queueListModel);
	}
}
