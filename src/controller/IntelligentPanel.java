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
import view.RiverPanel;

public class IntelligentPanel extends JPanel implements ActionListener {

	private Model model;
	private RiverPanel river;
	private JTextArea state;
	private JPanel alg;
	private JScrollPane algScroll;
	private JScrollPane scroll;
	private JButton go;
	private JButton think;

	public IntelligentPanel(Model model) {
		this.model = model;

		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JTextArea inst = new JTextArea("Start: [ L(farmer), L(fox), L(chicken, L(grain) ]\n\n"
				+ "Goal: [  R(farmer), R(fox), R(chicken, R(grain) ]\n\n" + "Operators:\n" + "1. Move fox left/right.\n"
				+ "2. Move chicken left/right.\n" + "3. Move grain left/right.\n" + "4. Move boat left/right.\n\n"
				+ "Invalid states: [L,L,R,R], [R,R,L,L], [L,R,R,L], [R,L,L,R]\n\n"
				+ "Constraints: The farmer can only move an item from the bank they are on.");
		inst.setEditable(false);
		inst.setOpaque(false);
		g.gridx = 0;
		g.gridy = 0;
		g.gridwidth = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		this.add(inst, g);

		think = new JButton("Think");
		think.addActionListener(this);
		g.gridx = 0;
		g.gridy = 1;
		g.gridwidth = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		this.add(think, g);

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
		this.add(scroll, g);

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
		this.add(algScroll, g);

		go = new JButton("Go");
		go.addActionListener(this);
		g.gridx = 2;
		g.gridy = 1;
		g.gridwidth = 1;
		g.gridheight = 1;
		g.weightx = 0;
		g.weighty = 0;
		g.fill = GridBagConstraints.BOTH;
		this.add(go, g);

		river = new RiverPanel(model);
		river.setOpaque(false);
		river.addButtonListeners();
		g.gridx = 0;
		g.gridy = 2;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 2;
		g.gridwidth = 3;
		g.fill = GridBagConstraints.BOTH;
		this.add(river, g);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(go)) {

			new Thread(new Runnable() {
				// badness
				public void run() {
					for (int i = 0; i < alg.getComponentCount(); i++) {
						if (alg.getComponent(i).getClass().equals(JButton.class)) {
							JButton cmd = (JButton) alg.getComponent(i);
							System.out.println(cmd.getText());
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

		} else if (e.getSource().equals(think)) {

			new Thread(new Runnable() {
				// badness
				public void run() {
					State start = new State(null, null, "L", "L", "L", "L");
					State goal = new State(null, null, "R", "R", "R", "R");

					Vector<State> children = new Vector<>();
					children.addElement(start);

					int num = 0;
					State soln = null;
					while (!finished(children, goal)) {
						Vector<State> nextChildren = new Vector<>();
						for (int i = 0; i < children.size(); i++) {
							State next = children.elementAt(i);
							next.filter();
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
	}

	public boolean finished(Vector<State> states, State goal) {
		for (int i = 0; i < states.size(); i++) {
			if (states.elementAt(i).equals(goal))
				return true;
		}
		return false;
	}

	private void addStep(String step) {
		JButton blah = new JButton(step);
		blah.addActionListener(this);
		alg.add(blah, 2);
		algScroll.getVerticalScrollBar().setValue(alg.getHeight() + 100);
		alg.revalidate();
	}
}
