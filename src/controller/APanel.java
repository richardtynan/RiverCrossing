package controller;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.Model;
import view.RiverPanel;

public abstract class APanel extends JSplitPane {

	protected Model model;
	protected JPanel panel;
	protected RiverPanel river;

	public APanel(Model model) {
		super(JSplitPane.VERTICAL_SPLIT);
		this.setContinuousLayout(true);
		this.model = model;
		panel = createPanel();
		river = new RiverPanel(model);
		addControls();
		this.setLeftComponent(panel);
		this.setRightComponent(river);
	}

	protected abstract JPanel createPanel();

	protected abstract void addControls();

}
