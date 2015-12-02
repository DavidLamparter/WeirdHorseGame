package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import model.Job;
import model.WorkQueue;

public class QueueFrame extends JFrame implements Observer {
	private SettlementGUI caller;
	private WorkQueue model;
	private DefaultListModel<String> queueListModel;
	private JList<String> displayList;
	
	public QueueFrame(SettlementGUI caller) {
		this.caller = caller;
		this.setLocation(caller.getWidth()-200, 0);
		this.setSize(200, 500);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setBackground(null);
		queueListModel = new DefaultListModel();
		displayList = new JList();
		displayList.setSize(this.getSize());
		displayList.setLocation(0, 0);
		displayList.setModel(queueListModel);
		//  displayList.setOpaque(false);
		this.add(displayList);
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
