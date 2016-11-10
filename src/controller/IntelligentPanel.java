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

public abstract class IntelligentPanel extends APanel implements ActionListener {

	protected JTextArea state;
	private JPanel alg;
	private JScrollPane algScroll;
	private JScrollPane scroll;
	private JButton go;
	private JButton think;

	public IntelligentPanel(Model model) {
		super(model);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(go)) {
			go();
		} else if (e.getSource().equals(think)) {
			resetSteps();
			think();
		}
	}

	private void go() {
		new Thread(new Runnable() {
			// badness
			public void run() {
				for (int i = 0; i < alg.getComponentCount(); i++) {
					if (alg.getComponent(i).getClass().equals(JButton.class)) {
						JButton cmd = (JButton) alg.getComponent(i);
						// System.out.println(cmd.getText());
						model.move(cmd.getText().substring(0, cmd.getText().length() - 18));
						while (river.getBusy()) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		}).start();
	}

	protected void think() {
		new Thread(new Runnable() {
			// badness
			public void run() {
				Vector<State> invalid = new Vector<>();
				invalid.addElement(new State(null, null, "L", "R", "R", "R", null));
				invalid.addElement(new State(null, null, "R", "L", "L", "L", null));
				invalid.addElement(new State(null, null, "L", "L", "R", "R", null));
				invalid.addElement(new State(null, null, "R", "R", "L", "L", null));
				invalid.addElement(new State(null, null, "L", "R", "R", "L", null));
				invalid.addElement(new State(null, null, "R", "L", "L", "R", null));
				State start = new State(null, null, "L", "L", "L", "L", invalid);
				State goal = new State(null, null, "R", "R", "R", "R", invalid);

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

	protected abstract void filter(State state);

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

	protected JPanel createPanel() {

		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JTextArea inst = new JTextArea("Start: [ L(farmer), L(fox), L(chicken, L(grain) ]\n\n"
				+ "Goal: [  R(farmer), R(fox), R(chicken, R(grain) ]\n\n" + "Operators:\n" + "1. Move fox left/right.\n"
				+ "2. Move chicken left/right.\n" + "3. Move grain left/right.\n" + "4. Move boat left/right.\n\n"
				+ "Invalid states: [L,L,R,R], [R,R,L,L], [L,R,R,L], [R,L,L,R]\n\n"
				+ "Constraints: The farmer can only move an item from the\nbank they are on.");
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
		g.gridheight = 1;
		g.fill = GridBagConstraints.BOTH;
		p.add(algScroll, g);

		go = new JButton("Go");
		go.addActionListener(this);
		g.gridx = 2;
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
	
	protected void resetSteps() {
		while (alg.getComponentCount() > 4) {
			alg.remove(2);
		}
	}
}
