package controller;

import model.Model;
import model.State;

public class FoxFirstIntelligentPanel extends IntelligentPanel {

	public FoxFirstIntelligentPanel(Model model) {
		super(model);
	}

	protected void filter(State state) {
		state.filterFoxFirst();
	}

}
