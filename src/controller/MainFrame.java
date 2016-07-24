package controller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.Model;

public class MainFrame {

	public static void main(String[] args) {
		JTabbedPane jtp = new JTabbedPane();
		jtp.add("play", new PlayPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("algorithm", new AlgorithmPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("solution", new SolutionPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("intelligent", new JPanel());
		jtp.add("learning", new JPanel());
		jtp.setSelectedIndex(0);

		JFrame frame = new JFrame("River and Boat");
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(jtp);
		frame.pack();
		frame.setVisible(true);
	}

}
