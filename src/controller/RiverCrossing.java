package controller;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Model;

public class RiverCrossing {

	public static void main(String[] args) {
		JTabbedPane jtp = new JTabbedPane();
		jtp.add("play", new PlayPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("algorithm", new AlgorithmPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("solution", new SolutionPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("intelligent fox", new FoxFirstPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("intelligent grain", new GrainFirstPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("learning", new LearningPanel(new Model('L', 'L', 'L', 'L')));
		jtp.setSelectedIndex(5);

		JFrame frame = new JFrame("River Crossing");
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(jtp);
		frame.pack();
		frame.setVisible(true);
	}

}
