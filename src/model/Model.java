package model;

import java.util.Observable;

public class Model extends Observable {

	public static char LEFT = 'L';
	public static char RIGHT = 'R';

	private char farmer;
	private char fox;
	private char chicken;
	private char grain;

	public Model(char farmer, char fox, char chicken, char grain) {
		this.farmer = farmer;
		this.fox = fox;
		this.chicken = chicken;
		this.grain = grain;
	}

	public boolean move(String op) {
		this.setChanged();
		if (op.equals("foxRight"))
			return moveFoxRight();
		else if (op.equals("chickenRight"))
			return moveChickenRight();
		else if (op.equals("grainRight"))
			return moveGrainRight();
		else if (op.equals("foxLeft"))
			return moveFoxLeft();
		else if (op.equals("chickenLeft"))
			return moveChickenLeft();
		else if (op.equals("grainLeft"))
			return moveGrainLeft();
		else if (op.equals("farmer")) {
			if (isFarmerLeft())
				return moveFarmerRight();
			else
				return moveFarmerLeft();
		}
		return false;
	}

	private boolean moveFarmerLeft() {
		if (canMoveFarmerLeft()) {
			this.farmer = Model.LEFT;
			this.notifyObservers("farmerLeft");
			return true;
		}
		this.notifyObservers("farmerLeftFailed");
		return false;
	}

	private boolean moveFarmerRight() {
		if (canMoveFarmerRight()) {
			this.farmer = Model.RIGHT;
			this.notifyObservers("farmerRight");
			return true;
		}
		this.notifyObservers("farmerRightFailed");
		return false;
	}

	private boolean moveFoxLeft() {
		if (canMoveFoxLeft()) {
			this.farmer = Model.LEFT;
			this.fox = Model.LEFT;
			this.notifyObservers("foxLeft");
			return true;
		}
		this.notifyObservers("foxLeftFailed");
		return false;
	}

	private boolean moveFoxRight() {
		if (canMoveFoxRight()) {
			this.farmer = Model.RIGHT;
			this.fox = Model.RIGHT;
			this.notifyObservers("foxRight");
			return true;
		}
		this.notifyObservers("foxRightFailed");
		return false;
	}

	private boolean moveChickenLeft() {
		if (canMoveChickenLeft()) {
			this.farmer = Model.LEFT;
			this.chicken = Model.LEFT;
			this.notifyObservers("chickenLeft");
			return true;
		}
		this.notifyObservers("chickenLeftFailed");
		return false;
	}

	private boolean moveChickenRight() {
		if (canMoveChickenRight()) {
			this.farmer = Model.RIGHT;
			this.chicken = Model.RIGHT;
			this.notifyObservers("chickenRight");
			return true;
		}
		this.notifyObservers("chickenRightFailed");
		return false;
	}

	private boolean moveGrainLeft() {
		if (canMoveGrainLeft()) {
			this.farmer = Model.LEFT;
			this.grain = Model.LEFT;
			this.notifyObservers("grainLeft");
			return true;
		}
		this.notifyObservers("grainLeftFailed");
		return false;
	}

	private boolean moveGrainRight() {
		if (canMoveGrainRight()) {
			this.farmer = Model.RIGHT;
			this.grain = Model.RIGHT;
			this.notifyObservers("grainRight");
			return true;
		}
		this.notifyObservers("grainRightFailed");
		return false;
	}

	public String toString() {
		return "[ " + farmer + ", " + fox + ", " + chicken + ", " + grain + " ]";
	}

	public char getFarmer() {
		return farmer;
	}

	public char getFox() {
		return fox;
	}

	public char getChicken() {
		return chicken;
	}

	public char getGrain() {
		return grain;
	}

	public boolean isFarmerLeft() {
		return farmer == LEFT;
	}

	public boolean isFoxLeft() {
		return fox == LEFT;
	}

	public boolean isChickenLeft() {
		return chicken == LEFT;
	}

	public boolean isGrainLeft() {
		return grain == LEFT;
	}

	public boolean isFarmerRight() {
		return farmer == RIGHT;
	}

	public boolean isFoxRight() {
		return fox == RIGHT;
	}

	public boolean isChickenRight() {
		return chicken == RIGHT;
	}

	public boolean isGrainRight() {
		return grain == RIGHT;
	}

	public boolean canMoveFarmerLeft() {
		return this.isFarmerRight();
	}

	public boolean canMoveFarmerRight() {
		return this.isFarmerLeft();
	}

	public boolean canMoveFoxLeft() {
		return this.isFarmerRight() && isFoxRight();
	}

	public boolean canMoveFoxRight() {
		return this.isFarmerLeft() && isFoxLeft();
	}

	public boolean canMoveChickenLeft() {
		return this.isChickenRight() && isChickenRight();
	}

	public boolean canMoveChickenRight() {
		return this.isFarmerLeft() && isChickenLeft();
	}

	public boolean canMoveGrainLeft() {
		return this.isGrainRight() && isGrainRight();
	}

	public boolean canMoveGrainRight() {
		return this.isFarmerLeft() && isGrainLeft();
	}
	
	public boolean isGoal() {
		return farmer == 'R' && fox == 'R' && chicken == 'R' && grain == 'R';
	}
	
	public boolean leftInvalid() {
		if (fox == 'L' && chicken == 'L' && farmer == 'R') {
			return true;
		} else if (chicken == 'L' && grain == 'L' && farmer == 'R') {
			return true;
		}
		return false;
	}
		
	public boolean rightInvalid() {	
		if (fox == 'R' && chicken == 'R' && farmer == 'L') {
			return true;
		} else if (chicken == 'R' && grain == 'R' && farmer == 'L') {
			return true;
		}
		return false;
	}

	public void resetConfig() {
		this.farmer = Model.LEFT;
		this.fox = Model.LEFT;
		this.chicken = Model.LEFT;
		this.grain = Model.LEFT;
		this.setChanged();
		this.notifyObservers("resetConfig");
	}

	public void resetGame() {
		System.out.println("Reset Game");
		//resetConfig();
	}
}
