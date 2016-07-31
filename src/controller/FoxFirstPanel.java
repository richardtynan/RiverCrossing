package controller;

import model.Model;
import model.State;

public class FoxFirstPanel extends IntelligentPanel {

	public FoxFirstPanel(Model model) {
		super(model);
	}

	protected void filter(State state) {
		state.filterFoxFirst();
	}

}
