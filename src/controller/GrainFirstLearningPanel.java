package controller;

import javax.swing.JPanel;

import model.Model;
import model.State;

public class GrainFirstLearningPanel extends LearningPanel {

	public GrainFirstLearningPanel(Model model) {
		super(model);
	}

	protected void filter(State state) {
		state.filterGrainFirst();
	}

}
