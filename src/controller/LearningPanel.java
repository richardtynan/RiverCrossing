package controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Model;

public class LearningPanel extends APanel implements ActionListener {
	
	protected JTextArea state;
	protected JTextArea invalid;
	private JPanel alg;
	private JScrollPane algScroll;
	private JScrollPane invalidScroll;
	private JScrollPane scroll;
	private JButton go;
	private JButton think;

	public LearningPanel(Model model) {
		super(model);
	}

	protected JPanel createPanel() {

		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JTextArea inst = new JTextArea("Start: [ L(farmer), L(fox), L(chicken, L(grain) ]\n"
				+ "Goal: [  R(farmer), R(fox), R(chicken, R(grain) ]\n\n" + "Operators:\n" + "1. Move fox left/right.\n"
				+ "2. Move chicken left/right.\n" + "3. Move grain left/right.\n" + "4. Move boat left/right.\n\n"
				+ "Constraints: The farmer can only move an item from the bank they are on.");
		inst.setEditable(false);
		inst.setOpaque(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		p.add(inst, g);

		think = new JButton("Think");
		think.addActionListener(this);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		p.add(think, g);

		invalid = new JTextArea("");
		invalid.setEditable(false);
		invalid.setOpaque(false);
		g.gridx = 1;
		g.gridy = 0;
		g.gridwidth = 1;
		g.gridheight = 2;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;

		invalidScroll = new JScrollPane(invalid);
		p.add(invalidScroll, g);
		
		state = new JTextArea("");
		state.setEditable(false);
		state.setOpaque(false);
		g.gridx = 2;
		g.gridy = 0;
		g.gridwidth = 1;
		g.gridheight = 2;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;

		scroll = new JScrollPane(state);
		p.add(scroll, g);

		alg = new JPanel();
		alg.setLayout(new GridLayout(0, 1));
		algScroll = new JScrollPane(alg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		alg.add(new JLabel("*******"));
		alg.add(new JLabel("****START****"));
		alg.add(new JLabel("****END****"));
		alg.add(new JLabel("*******"));
		alg.setOpaque(false);
		g.gridx = 3;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.gridheight = 1;
		g.fill = GridBagConstraints.BOTH;
		p.add(algScroll, g);

		go = new JButton("Go");
		go.addActionListener(this);
		g.gridx = 3;
		g.gridy = 1;
		g.gridwidth = 1;
		g.gridheight = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		p.add(go, g);
		return p;
	}

	protected void addControls() {
		river.addButtonListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(go)) {
			go();
		} else if (e.getSource().equals(think)) {
			think();
		}
	}

	private void think() {
	}

	private void go() {
	}
}
