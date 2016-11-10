package controller;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.Model;

public class RiverCrossing {

	public static void main(String[] args) {
		JTabbedPane jtp = new JTabbedPane();
		jtp.add("play", new PlayPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("algorithm", new AlgorithmPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("solution", new SolutionPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("intelligent fox", new FoxFirstIntelligentPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("intelligent grain", new GrainFirstIntelligentPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("learning fox", new FoxFirstLearningPanel(new Model('L', 'L', 'L', 'L')));
		jtp.add("learning grain", new GrainFirstLearningPanel(new Model('L', 'L', 'L', 'L')));
		jtp.setSelectedIndex(0);

		JFrame frame = new JFrame("River Crossing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(jtp);
		frame.setSize(new Dimension(1024, 768));
		frame.setVisible(true);
	}

}
