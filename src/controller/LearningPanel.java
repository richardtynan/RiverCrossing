package controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Model;
import model.State;

public abstract class LearningPanel extends APanel implements ActionListener {

	protected JTextArea state;
	private JPanel invalid;
	private Vector<State> invStates;
	private JPanel alg;
	private JScrollPane algScroll;
	private JScrollPane invalidScroll;
	private JScrollPane scroll;
	private JButton go;

	public LearningPanel(Model model) {
		super(model);
		invStates = new Vector<State>();
		for (int i = 0; i < 10; i++) {
			invalid.add(new JLabel(), 0);
		}
	}

	protected JPanel createPanel() {

		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JTextArea inst = new JTextArea("Start: [ L(farmer), L(fox), L(chicken, L(grain) ]\n"
				+ "Goal: [  R(farmer), R(fox), R(chicken, R(grain) ]\n\n" + "Operators:\n" + "1. Move fox left/right.\n"
				+ "2. Move chicken left/right.\n" + "3. Move grain left/right.\n" + "4. Move boat left/right.\n\n"
				+ "Constraints: The farmer can only move an item\nfrom the bank they are on.");
		inst.setEditable(false);
		inst.setOpaque(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		p.add(inst, g);

		go = new JButton("Go");
		go.addActionListener(this);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		p.add(go, g);

		state = new JTextArea("");
		state.setEditable(false);
		state.setOpaque(false);
		g.gridx = 1;
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
		g.gridx = 2;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.gridheight = 2;
		g.fill = GridBagConstraints.BOTH;
		p.add(algScroll, g);

		invalid = new JPanel();
		invalid.setLayout(new GridLayout(0, 1));
		invalid.setOpaque(false);
		g.gridx = 3;
		g.gridy = 0;
		g.gridwidth = 1;
		g.gridheight = 2;
		g.weightx = 1;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;

		invalidScroll = new JScrollPane(invalid);
		p.add(invalidScroll, g);
		return p;
	}

	protected void addControls() {
		river.addButtonListeners();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(go)) {
			resetSteps();
			think();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			go();
		}
	}

	private void think() {
		new Thread(new Runnable() {
			// badness
			public void run() {
				State start = new State(null, null, "L", "L", "L", "L", invStates);
				State goal = new State(null, null, "R", "R", "R", "R", invStates);

				Vector<State> children = new Vector<>();
				children.addElement(start);

				int num = 0;
				State soln = null;
				while (!finished(children, goal)) {
					Vector<State> nextChildren = new Vector<>();
					for (int i = 0; i < children.size(); i++) {
						State next = children.elementAt(i);
						filter(next);
						nextChildren.addAll(next.getChildren());
					}
					children = nextChildren;
					for (int i = 0; i < children.size(); i++) {
						if (children.elementAt(i).equals(goal)) {
							state.append("XXX: " + children.elementAt(i).getTransitions() + " => "
									+ children.elementAt(i) + "\n");
							soln = children.elementAt(i);
							i = children.size();
						} else {
							state.append(num + ": " + children.elementAt(i).getTransitions() + " => "
									+ children.elementAt(i) + "\n");
						}
						state.setCaretPosition(state.getText().length());
						num++;
					}
				}
				Vector<String> operators = new Vector<>();
				State inter = soln;
				while (inter.getParent() != null) {
					addStep(inter.getOperator() + " (click to remove)");
					operators.addElement(inter.getOperator());
					inter = inter.getParent();
				}
			}
		}).start();
	}

	private void go() {
		new Thread(new Runnable() {
			// badness
			public void run() {
				for (int i = 0; i < alg.getComponentCount(); i++) {
					if (alg.getComponent(i).getClass().equals(JButton.class)) {
						JButton cmd = (JButton) alg.getComponent(i);
						model.move(cmd.getText().substring(0, cmd.getText().length() - 18));
						while (river.getBusy()) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
						if (learn()) {
							i = 0;
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}).start();
	}

	private boolean learn() {
		if (model.leftInvalid() || model.rightInvalid()) {
			State text = new State(null, null, "" + model.getFarmer(), "" + model.getFox(),
					"" + model.getChicken(), "" + model.getGrain(), null);
			invStates.addElement(text);
			JLabel button = new JLabel(text.toString());
			invalid.add(button, 0);
			model.resetConfig();
			resetSteps();
			think();
			return true;
		}
		return false;
	}

	protected void filter(State state) {
		state.filterGrainFirst();
	}

	protected boolean finished(Vector<State> states, State goal) {
		for (int i = 0; i < states.size(); i++) {
			if (states.elementAt(i).equals(goal))
				return true;
		}
		return false;
	}

	protected void addStep(String step) {
		JButton blah = new JButton(step);
		blah.addActionListener(this);
		alg.add(blah, 2);
		algScroll.getVerticalScrollBar().setValue(alg.getHeight() + 100);
		alg.revalidate();
	}

	protected void resetSteps() {
		while (alg.getComponentCount() > 4) {
			alg.remove(2);
		}
	}
}
