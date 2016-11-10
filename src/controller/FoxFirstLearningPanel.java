package controller;

import model.Model;
import model.State;

public class FoxFirstLearningPanel extends LearningPanel {

	public FoxFirstLearningPanel(Model model) {
		super(model);
	}

	protected void filter(State state) {
		state.filterFoxFirst();
	}

}
