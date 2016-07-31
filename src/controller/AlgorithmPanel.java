package controller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Model;

public class AlgorithmPanel extends APanel implements ActionListener {

	protected JPanel alg;
	protected JScrollPane algScroll;

	protected JButton farmerLeft;
	protected JButton farmerRight;
	protected JButton foxLeft;
	protected JButton foxRight;
	protected JButton chickenLeft;
	protected JButton chickenRight;
	protected JButton grainLeft;
	protected JButton grainRight;
	private JButton go;

	public AlgorithmPanel(Model model) {
		super(model);
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

	protected JPanel createPanel() {
		JPanel p = new JPanel(new GridLayout(1, 2));

		JPanel options = new JPanel(new GridLayout(5, 2));
		options.setOpaque(false);
		p.add(options);

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
		p.add(algScroll);

		return p;
	}

	protected void addControls() {
		river.addButtonListeners();
	}

}
