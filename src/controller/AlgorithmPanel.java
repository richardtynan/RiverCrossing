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

import model.Model;
import view.RiverPanel;

public class AlgorithmPanel extends JPanel implements ActionListener {

	private Model model;
	private JPanel alg;
	private JButton farmerLeft;
	private JButton farmerRight;
	private JButton foxLeft;
	private JButton foxRight;
	private JButton chickenLeft;
	private JButton chickenRight;
	private JButton grainLeft;
	private JButton grainRight;
	private JScrollPane algScroll;
	private JButton go;
	private RiverPanel river;
	
	public AlgorithmPanel(Model model) {
		this.model = model;

		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JPanel options = new JPanel(new GridLayout(5, 2));
		options.setOpaque(false);
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(options, g);

		foxLeft = new JButton("foxLeft");
		options.add(foxLeft);
		foxLeft.addActionListener(this);
		foxRight = new JButton("foxRight");
		options.add(foxRight);
		foxRight.addActionListener(this);

		chickenLeft = new JButton("chickenLeft");
		options.add(chickenLeft);
		chickenLeft.addActionListener(this);
		chickenRight = new JButton("chickenRight");
		options.add(chickenRight);
		chickenRight.addActionListener(this);

		grainLeft = new JButton("grainLeft");
		options.add(grainLeft);
		grainLeft.addActionListener(this);
		grainRight = new JButton("grainRight");
		options.add(grainRight);
		grainRight.addActionListener(this);

		farmerLeft = new JButton("farmerLeft");
		options.add(farmerLeft);
		farmerLeft.addActionListener(this);
		farmerRight = new JButton("farmerRight");
		options.add(farmerRight);
		farmerRight.addActionListener(this);
		
		go = new JButton("Go");
		options.add(go);
		go.addActionListener(this);

		alg = new JPanel();
		alg.setLayout(new GridLayout(0, 1));
		algScroll = new JScrollPane(alg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		alg.add(new JLabel("*******"));
		alg.add(new JLabel("****START****"));
		alg.add(new JLabel("****END****"));
		alg.add(new JLabel("*******"));
		alg.setOpaque(false);
		g.gridx = 1;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 0;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(algScroll, g);

		river = new RiverPanel(model);
		river.setOpaque(false);
		river.addButtonListeners();
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 2;
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

		} else if (e.getActionCommand().endsWith("(click to remove)")) {
			alg.remove((JButton) e.getSource());
		} else if (e.getSource().equals(foxLeft)) {
			addStep("foxLeft (click to remove)");
		} else if (e.getSource().equals(foxRight)) {
			addStep("foxRight (click to remove)");
		} else if (e.getSource().equals(chickenLeft)) {
			addStep("chickenLeft (click to remove)");
		} else if (e.getSource().equals(chickenRight)) {
			addStep("chickenRight (click to remove)");
		} else if (e.getSource().equals(grainLeft)) {
			addStep("grainLeft (click to remove)");
		} else if (e.getSource().equals(grainRight)) {
			addStep("grainRight (click to remove)");
		} else if (e.getSource().equals(farmerLeft)) {
			addStep("farmerLeft (click to remove)");
		} else if (e.getSource().equals(farmerRight)) {
			addStep("farmerRight (click to remove)");
		}
	}

	private void addStep(String step) {
		JButton blah = new JButton(step);
		blah.addActionListener(this);
		alg.add(blah, alg.getComponentCount() - 2);
		algScroll.getVerticalScrollBar().setValue(alg.getHeight() + 100);
		alg.revalidate();
	}

}
