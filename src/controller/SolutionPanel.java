package controller;

import javax.swing.JButton;

import model.Model;

public class SolutionPanel extends AlgorithmPanel {

	public SolutionPanel(Model model) {
		super(model);

		farmerLeft.setEnabled(false);
		farmerRight.setEnabled(false);
		foxLeft.setEnabled(false);
		foxRight.setEnabled(false);
		chickenLeft.setEnabled(false);
		chickenRight.setEnabled(false);
		grainLeft.setEnabled(false);
		grainRight.setEnabled(false);
		
		JButton button = new JButton("chickenRight (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);
		button = new JButton("farmerLeft (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);
		button = new JButton("foxRight (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);
		button = new JButton("chickenLeft (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);
		button = new JButton("grainRight (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);
		button = new JButton("farmerLeft (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);
		button = new JButton("chickenRight (click to remove)");
		//button.setEnabled(false);
		alg.add(button, alg.getComponentCount() - 2);

		algScroll.getVerticalScrollBar().setValue(alg.getHeight() + 100);
		alg.revalidate();
	}

}
