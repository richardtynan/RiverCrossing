package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Model;

public class RiverPanel extends JPanel implements Observer, MouseListener, ActionListener {

	private ImageIcon blank = new ImageIcon(getClass().getResource("/blank.png"));
	private ImageIcon win = new ImageIcon(getClass().getResource("/win.png"));
	private ImageIcon deadIcon = new ImageIcon(getClass().getResource("/dead.png"));
	private ImageIcon boatIcon = new ImageIcon(getClass().getResource("/boat.png"));
	
	private ImageIcon foxIcon = new ImageIcon(getClass().getResource("/fox.png"));
	private ImageIcon chickenIcon = new ImageIcon(getClass().getResource("/chicken.png"));
	private ImageIcon grainIcon = new ImageIcon(getClass().getResource("/grain.png"));
	
	private ImageIcon foxBoatIcon = new ImageIcon(getClass().getResource("/foxboat.png"));
	private ImageIcon chickenBoatIcon = new ImageIcon(getClass().getResource("/chickenboat.png"));
	private ImageIcon grainBoatIcon = new ImageIcon(getClass().getResource("/grainboat.png"));

	private JLabel leftFox;
	private JLabel leftChicken;
	private JLabel leftGrain;
	private JLabel rightFox;
	private JLabel rightChicken;
	private JLabel rightGrain;

	private JLabel farmer;

	private Model model;

	private JLabel chickenLabel;
	private int chickenCount;
	private JLabel grainLabel;
	private int grainCount;
	private JButton resetConfig;
	private JButton resetGame;
	
	private boolean busy;

	public RiverPanel(Model model) {
		this.model = model;
		model.addObserver(this);

		this.busy = false;
		this.setOpaque(true);
		this.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();

		JPanel scoreBoard = new JPanel();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 0;
		g.weighty = 0;
		g.gridwidth = 3;
		g.fill = GridBagConstraints.BOTH;
		this.add(scoreBoard, g);

		scoreBoard.add(new JLabel("Fox count: 1  "));
		chickenCount = 1;
		chickenLabel = new JLabel("Chicken count: " + chickenCount + "  ");
		scoreBoard.add(chickenLabel);
		grainCount = 1;
		grainLabel = new JLabel("Grain count: " + grainCount + "  ");
		scoreBoard.add(grainLabel);

		resetConfig = new JButton("Reset Pieces");
		scoreBoard.add(resetConfig);
		resetGame = new JButton("Reset Game");
		scoreBoard.add(resetGame);

		leftFox = new JLabel(foxIcon);
		leftFox.setBackground(Color.GREEN);
		leftFox.setOpaque(true);
		g.gridx = 0;
		g.gridy = 1;
		g.weightx = 0;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(leftFox, g);

		leftChicken = new JLabel(chickenIcon);
		leftChicken.setBackground(Color.GREEN);
		leftChicken.setOpaque(true);
		g.gridx = 0;
		g.gridy = 2;
		g.weightx = 0;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(leftChicken, g);

		leftGrain = new JLabel(grainIcon);
		leftGrain.setBackground(Color.GREEN);
		leftGrain.setOpaque(true);
		g.gridx = 0;
		g.gridy = 3;
		g.weightx = 0;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(leftGrain, g);

		JLabel riverUpper = new JLabel();
		riverUpper.setBackground(Color.BLUE);
		riverUpper.setOpaque(true);
		g.gridx = 1;
		g.gridy = 1;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(riverUpper, g);

		JPanel boatPanel = new JPanel();
		boatPanel.setBackground(Color.BLUE);
		boatPanel.setLayout(null);
		g.gridx = 1;
		g.gridy = 2;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(boatPanel, g);

		farmer = new JLabel(boatIcon);
		farmer.setBounds(0, 0, 128, 128);
		boatPanel.add(farmer);

		JLabel riverLower = new JLabel();
		riverLower.setBackground(Color.BLUE);
		riverLower.setOpaque(true);
		g.gridx = 1;
		g.gridy = 3;
		g.weightx = 1;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(riverLower, g);

		rightFox = new JLabel(blank);
		rightFox.setBackground(Color.GREEN);
		rightFox.setOpaque(true);
		g.gridx = 2;
		g.gridy = 1;
		g.weightx = 0;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(rightFox, g);

		rightChicken = new JLabel(blank);
		rightChicken.setBackground(Color.GREEN);
		rightChicken.setOpaque(true);
		g.gridx = 2;
		g.gridy = 2;
		g.weightx = 0;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(rightChicken, g);

		rightGrain = new JLabel(blank);
		rightGrain.setBackground(Color.GREEN);
		rightGrain.setOpaque(true);
		g.gridx = 2;
		g.gridy = 3;
		g.weightx = 0;
		g.weighty = 1;
		g.gridwidth = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(rightGrain, g);
	}

	public void move(String op) {
		if (op.equals("foxRight"))
			moveFoxRight();
		else if (op.equals("chickenRight"))
			moveChickenRight();
		else if (op.equals("grainRight"))
			moveGrainRight();
		else if (op.equals("farmerRight"))
			moveEmptyRight();
		else if (op.equals("foxLeft"))
			moveFoxLeft();
		else if (op.equals("chickenLeft"))
			moveChickenLeft();
		else if (op.equals("grainLeft"))
			moveGrainLeft();
		else if (op.equals("farmerLeft"))
			moveEmptyLeft();
		else if (op.equals("resetConfig")) {
			farmer.setLocation(0, 0);
			farmer.setIcon(boatIcon);
			drawBanks();
		} else if (op.equals("resetGame")) {
			farmer.setLocation(0, 0);
			farmer.setIcon(boatIcon);
			chickenCount = 1;
			this.chickenLabel.setText("Chicken count: " + chickenCount + "  ");
			grainCount = 1;
			this.grainLabel.setText("Grain count: " + grainCount + "  ");
			drawBanks();
		}
	}

	public boolean getBusy() {
		return busy;
	}
	
	public void moveEmptyRight() {
		moveRight(boatIcon, null);
	}

	public void moveEmptyLeft() {
		moveLeft(boatIcon, null);
	}

	public void moveFoxRight() {
		moveRight(foxBoatIcon, leftFox);
	}

	public void moveFoxLeft() {
		moveLeft(foxBoatIcon, rightFox);
	}

	public void moveChickenRight() {
		moveRight(chickenBoatIcon, leftChicken);
	}

	public void moveChickenLeft() {
		moveLeft(chickenBoatIcon, rightChicken);
	}

	public void moveGrainRight() {
		moveRight(grainBoatIcon, leftGrain);
	}

	public void moveGrainLeft() {
		moveLeft(grainBoatIcon, rightGrain);
	}

	private void moveRight(ImageIcon boat, JLabel from) {
		move(boat, from, this.getWidth() - this.farmer.getWidth() - 60, 10);
	}

	private void moveLeft(ImageIcon boat, JLabel from) {
		move(boat, from, farmer.getX(), -10);
	}

	private void move(ImageIcon boat, JLabel from, int dist, int step) {
		busy = true;
		new Thread(new Runnable() {
			// badness
			public void run() {
				if (from != null)
					from.setIcon(blank);
				farmer.setIcon(boat);
				int steps = Math.abs(dist) / 10;
				for (int i = 0; i < steps; i++) {
					farmer.setLocation(farmer.getX() + step, farmer.getY());
					try {
						Thread.sleep(25);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				farmer.setIcon(boatIcon);
				drawBanks();
				busy = false;
			}
		}).start();
	}

	private void drawBanks() {
		if (model.isFoxLeft()) {
			leftFox.setIcon(foxIcon);
			rightFox.setIcon(blank);
		} else {
			rightFox.setIcon(foxIcon);
			leftFox.setIcon(blank);
		}
		if (model.isChickenLeft()) {
			leftChicken.setIcon(chickenIcon);
			rightChicken.setIcon(blank);
		} else {
			rightChicken.setIcon(chickenIcon);
			leftChicken.setIcon(blank);
		}
		if (model.isGrainLeft()) {
			leftGrain.setIcon(grainIcon);
			rightGrain.setIcon(blank);
		} else {
			rightGrain.setIcon(grainIcon);
			leftGrain.setIcon(blank);
		}
		if (model.leftInvalid()) {
			setLeft(Color.RED);
			if (model.isFoxLeft() && model.isChickenLeft()) {
				leftChicken.setIcon(deadIcon);
				deadChicken();
			}
			if (model.isChickenLeft() && model.isGrainLeft()) {
				leftGrain.setIcon(deadIcon);
				deadGrain();
			}
		} else {
			setLeft(Color.GREEN);
		}
		if (model.rightInvalid()) {
			setRight(Color.RED);
			if (model.isFoxRight() && model.isChickenRight()) {
				rightChicken.setIcon(deadIcon);
				deadChicken();
			}
			if (model.isChickenRight() && model.isGrainRight()) {
				rightGrain.setIcon(deadIcon);
				deadGrain();
			}
		} else {
			setRight(Color.GREEN);
		}
		if (model.isGoal()) {
			JOptionPane.showMessageDialog(this, "", "WIN", JOptionPane.PLAIN_MESSAGE, win);
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(farmer)) {
			if (model.isFarmerLeft()) {
				model.move("farmerRight");
			} else {
				model.move("farmerLeft");
			}
		} else if (e.getSource().equals(leftFox)) {
			model.move("foxRight");
		} else if (e.getSource().equals(leftChicken)) {
			model.move("chickenRight");
		} else if (e.getSource().equals(leftGrain)) {
			model.move("grainRight");
		} else if (e.getSource().equals(rightFox)) {
			model.move("foxLeft");
		} else if (e.getSource().equals(rightChicken)) {
			model.move("chickenLeft");
		} else if (e.getSource().equals(rightGrain)) {
			model.move("grainLeft");
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(resetConfig)) {
			model.resetConfig();
		} else if (e.getSource().equals(resetGame)) {
			model.resetGame();
		}
	}

	public void addListeners() {
		leftFox.addMouseListener(this);
		leftChicken.addMouseListener(this);
		leftGrain.addMouseListener(this);
		farmer.addMouseListener(this);
		rightFox.addMouseListener(this);
		rightChicken.addMouseListener(this);
		rightGrain.addMouseListener(this);
	}
	
	public void addButtonListeners() {
		resetConfig.addActionListener(this);
		resetGame.addActionListener(this);
	}

	public void update(Observable o, Object arg) {
		move((String) arg);
	}

	private void setLeft(Color colour) {
		this.leftFox.setBackground(colour);
		this.leftChicken.setBackground(colour);
		this.leftGrain.setBackground(colour);
	}

	private void setRight(Color colour) {
		this.rightFox.setBackground(colour);
		this.rightChicken.setBackground(colour);
		this.rightGrain.setBackground(colour);
	}

	public void deadChicken() {
		chickenCount++;
		this.chickenLabel.setText("Chicken count: " + chickenCount + "  ");
	}

	public void deadGrain() {
		grainCount++;
		this.grainLabel.setText("Grain count: " + grainCount + "  ");
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
