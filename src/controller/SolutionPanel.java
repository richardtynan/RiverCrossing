package controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import model.Model;
import view.RiverPanel;

public class SolutionPanel extends JPanel implements ActionListener {

	private Model model;
	private JPanel alg;
	private JButton farmer;
	private JButton foxLeft;
	private JButton foxRight;
	private JButton chickenLeft;
	private JButton chickenRight;
	private JButton grainLeft;
	private JButton grainRight;
	private JScrollPane algScroll;
	private JButton go;
	private RiverPanel river;

	public SolutionPanel(Model model) {
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
		foxLeft.setEnabled(false);
		options.add(foxLeft);
		foxLeft.addActionListener(this);
		foxRight = new JButton("foxRight");
		foxRight.setEnabled(false);
		options.add(foxRight);
		foxRight.addActionListener(this);

		chickenLeft = new JButton("chickenLeft");
		chickenLeft.setEnabled(false);
		options.add(chickenLeft);
		chickenLeft.addActionListener(this);
		chickenRight = new JButton("chickenRight");
		chickenRight.setEnabled(false);
		options.add(chickenRight);
		chickenRight.addActionListener(this);

		grainLeft = new JButton("grainLeft");
		grainLeft.setEnabled(false);
		options.add(grainLeft);
		grainLeft.addActionListener(this);
		grainRight = new JButton("grainRight");
		grainRight.setEnabled(false);
		options.add(grainRight);
		grainRight.addActionListener(this);

		farmer = new JButton("farmer");
		farmer.setEnabled(false);
		farmer.addActionListener(this);
		options.add(farmer);

		go = new JButton("Go");
		options.add(go);
		go.addActionListener(this);

		alg = new JPanel(new GridLayout(0, 1));
		algScroll = new JScrollPane(alg);
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

		alg.add(new JButton("chickenRight (click to remove)"));
		alg.add(new JButton("farmer (click to remove)"));
		alg.add(new JButton("foxRight (click to remove)"));
		alg.add(new JButton("chickenLeft (click to remove)"));
		alg.add(new JButton("grainRight (click to remove)"));
		alg.add(new JButton("farmer (click to remove)"));
		alg.add(new JButton("chickenRight (click to remove)"));
		
		JScrollBar vertical = algScroll.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(go)) {

			new Thread(new Runnable() {
				// badness
				public void run() {
					for (int i = 0; i < alg.getComponentCount(); i++) {
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
			}).start();

		}
	}

}
