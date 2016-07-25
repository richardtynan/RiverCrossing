package controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;
import view.RiverPanel;

public class PlayPanel extends JPanel {

	public PlayPanel(Model model) {

		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JTextArea inst = new JTextArea("Instructions:\n" + "1. Click the item to transfer it to a different bank.\n"
				+ "2. The fox and the chicken cannot be left alone on the bank.\n"
				+ "3. The chicken and the grain cannot be left alone on the bank.\n"
				+ "4. The boat can only take zero or one item at a time.\n"
				+ "5. Goal: Transfer all 3 items safely from the left bank to the right bank.");
		inst.setOpaque(false);
		inst.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(inst, g);

		RiverPanel river = new RiverPanel(model);
		river.setOpaque(false);
		river.addListeners();
		river.addButtonListeners();
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 2;
		g.fill = GridBagConstraints.BOTH;
		this.add(river, g);

	}

}
