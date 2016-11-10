package model;

import java.util.Vector;

public class State {

	private String farmer;
	private String fox;
	private String chicken;
	private String grain;
	private Vector<State> filteredChildren;
	private State parent;
	private String operator;
	private Vector<State> invalid;

	public State(State parent, String operator, String farmer, String fox, String chicken, String grain, Vector<State> invalid) {
		this.parent = parent;
		this.operator = operator;
		this.farmer = farmer;
		this.fox = fox;
		this.chicken = chicken;
		this.grain = grain;
		this.invalid = invalid;
	}
	
	public void addInvalid(State inv) {
		invalid.addElement(inv);
		for (int i = 0; i < filteredChildren.size(); i++) {
			filteredChildren.elementAt(i).addInvalid(inv);;
		}
	}
	
	private void filterLeftFox(Vector<State> children) {
		if (this.fox.equals("L")) {
			children.addElement(new State(this, "foxRight", "R", "R", chicken, grain, invalid));
		}
		if (this.chicken.equals("L")) {
			children.addElement(new State(this, "chickenRight", "R", fox, "R", grain, invalid));
		}
		if (this.grain.equals("L")) {
			children.addElement(new State(this, "grainRight", "R", fox, chicken, "R", invalid));
		}
		children.addElement(new State(this, "farmerRight", "R", fox, chicken, grain, invalid));
	}
	
	private void filterRightFox(Vector<State> children) {
		if (this.fox.equals("R")) {
			children.addElement(new State(this, "foxLeft", "L", "L", chicken, grain, invalid));
		}
		if (this.chicken.equals("R")) {
			children.addElement(new State(this, "chickenLeft", "L", fox, "L", grain, invalid));
		}
		if (this.grain.equals("R")) {
			children.addElement(new State(this, "grainLeft", "L", fox, chicken, "L", invalid));
		}
		children.addElement(new State(this, "farmerLeft", "L", fox, chicken, grain, invalid));
	}

	public void filterFoxFirst() {
		Vector<State> children = new Vector<>();
		if (this.farmer.equals("L")) {
			filterLeftFox(children);
		} else if (this.farmer.equals("R")) {
			filterRightFox(children);
		}
		filteredChildren = new Vector<>();
		for (int i = 0; i < children.size(); i++) {
			if (!children.elementAt(i).invalid()) {
				filteredChildren.addElement(children.elementAt(i));
			}
		}
	}

	public void filterGrainFirst() {
		Vector<State> children = new Vector<>();
		if (this.farmer.equals("L")) {
			filterLeftGrain(children);
		} else if (this.farmer.equals("R")) {
			filterRightGrain(children);
		}
		filteredChildren = new Vector<>();
		for (int i = 0; i < children.size(); i++) {
			if (!children.elementAt(i).invalid()) {
				filteredChildren.addElement(children.elementAt(i));
			}
		}
	}
	
	private void filterLeftGrain(Vector<State> children) {
		if (this.grain.equals("L")) {
			children.addElement(new State(this, "grainRight", "R", fox, chicken, "R", invalid));
		}
		if (this.chicken.equals("L")) {
			children.addElement(new State(this, "chickenRight", "R", fox, "R", grain, invalid));
		}
		if (this.fox.equals("L")) {
			children.addElement(new State(this, "foxRight", "R", "R", chicken, grain, invalid));
		}
		children.addElement(new State(this, "farmerRight", "R", fox, chicken, grain, invalid));
	}
	
	private void filterRightGrain(Vector<State> children) {
		if (this.grain.equals("R")) {
			children.addElement(new State(this, "grainLeft", "L", fox, chicken, "L", invalid));
		}
		if (this.chicken.equals("R")) {
			children.addElement(new State(this, "chickenLeft", "L", fox, "L", grain, invalid));
		}
		if (this.fox.equals("R")) {
			children.addElement(new State(this, "foxLeft", "L", "L", chicken, grain, invalid));
		}
		children.addElement(new State(this, "farmerLeft", "L", fox, chicken, grain, invalid));
	}
	
	public boolean equals(State state) {
		return state.getFarmer().equals(this.farmer) && state.getFox().equals(this.fox)
				&& state.getChicken().equals(this.chicken) && state.getGrain().equals(this.grain);
	}
	
	public boolean invalid() {
		for (int i = 0; i < invalid.size(); i++) {
			if (invalid.elementAt(i).equals(this)) {
				return true;
			}
		}
		return false;
	}

	public Vector<State> getChildren() {
		return filteredChildren;
	}

	public String toString() {
		return "[ " + farmer + ", " + fox + ", " + chicken + ", " + grain + " ]";
	}

	public String getTransitions() {
		if (parent == null) {
			return this.toString();
		} else {
			return this.parent.getTransitions() + " => " + this.operator;
		}
	}

	public State getParent() {
		return parent;
	}

	public String getOperator() {
		return operator;
	}

	public String getFarmer() {
		return farmer;
	}

	public String getFox() {
		return fox;
	}

	public String getChicken() {
		return chicken;
	}

	public String getGrain() {
		return grain;
	}

}
