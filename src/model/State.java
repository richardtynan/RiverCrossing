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

	public State(State parent, String operator, String farmer, String fox, String chicken, String grain) {
		this.parent = parent;
		this.operator = operator;
		this.farmer = farmer;
		this.fox = fox;
		this.chicken = chicken;
		this.grain = grain;
	}
	
	public void filter() {
		Vector<State> children = new Vector<>();
		if (this.farmer.equals("L")) {
			if (this.fox.equals("L")) {
				children.addElement(new State(this, "foxRight", "R", "R", chicken, grain));
			}
			if (this.chicken.equals("L")) {
				children.addElement(new State(this, "chickenRight", "R", fox, "R", grain));
			}
			if (this.grain.equals("L")) {
				children.addElement(new State(this, "grainRight", "R", fox, chicken, "R"));
			}
			children.addElement(new State(this, "farmerRight", "R", fox, chicken, grain));
		} else if (this.farmer.equals("R")) {
			if (this.fox.equals("R")) {
				children.addElement(new State(this, "foxLeft", "L", "L", chicken, grain));
			}
			if (this.chicken.equals("R")) {
				children.addElement(new State(this, "chickenLeft", "L", fox, "L", grain));
			}
			if (this.grain.equals("R")) {
				children.addElement(new State(this, "grainLeft", "L", fox, chicken, "L"));
			}
			children.addElement(new State(this, "farmerLeft", "L", fox, chicken, grain));
		}
		filteredChildren = new Vector<>();
		for (int i = 0; i < children.size(); i++) {
			if (!children.elementAt(i).invalid()) {
				filteredChildren.addElement(children.elementAt(i));
			}
		}
	}

	public boolean equals(State state) {
		return state.getFarmer().equals(this.farmer) && state.getFox().equals(this.fox)
				&& state.getChicken().equals(this.chicken) && state.getGrain().equals(this.grain);
	}

	public boolean invalid() {
		Vector<State> invalid = new Vector<>();
		invalid.addElement(new State(null, null, "L", "L", "R", "R"));
		invalid.addElement(new State(null, null, "R", "R", "L", "L"));
		invalid.addElement(new State(null, null, "L", "R", "R", "L"));
		invalid.addElement(new State(null, null, "R", "L", "L", "R"));
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
