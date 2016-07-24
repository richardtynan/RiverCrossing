package controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;
import view.RiverPanel;

public class SolutionPanel extends JPanel implements ActionListener {

	private RiverPanel river;

	public SolutionPanel(Model model) {

		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JTextArea inst = new JTextArea("Algorithm:\n" + "1. Move chicken right.\n" + "2. Move boat left.\n"
				+ "3. Move fox right.\n" + "4. Move chicken left.\n" + "5. Move grain right.\n" + "6. Move boat left.\n"
				+ "7. Move chicken right.");
		inst.setOpaque(false);
		inst.setEditable(false);
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		this.add(inst, g);

		JButton go = new JButton("Go");
		go.addActionListener(this);
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		this.add(go, g);

		river = new RiverPanel(model);
		river.setOpaque(false);
		g.gridx = 0;
		g.gridy = 2;
		g.weightx = 1;
		g.weighty = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(river, g);

	}

	public void actionPerformed(ActionEvent e) {
		river.moveChickenRight();
		river.moveEmptyLeft();
		river.moveFoxRight();
		river.moveChickenLeft();
		river.moveGrainRight();
		river.moveEmptyLeft();
		river.moveChickenRight();
	}

}
