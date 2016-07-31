package controller;

import javax.swing.JPanel;

import model.Model;
import model.State;

public class GrainFirstPanel extends IntelligentPanel {

	public GrainFirstPanel(Model model) {
		super(model);
	}

	protected void filter(State state) {
		state.filterGrainFirst();
	}

}
