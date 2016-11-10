package controller;

import javax.swing.JPanel;

import model.Model;
import model.State;

public class GrainFirstIntelligentPanel extends IntelligentPanel {

	public GrainFirstIntelligentPanel(Model model) {
		super(model);
	}

	protected void filter(State state) {
		state.filterGrainFirst();
	}

}
