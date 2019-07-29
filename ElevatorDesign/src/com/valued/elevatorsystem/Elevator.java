package com.valued.elevatorsystem;

public class Elevator {
	public enum ElevatorDIRECTION {
		NONE, UP, DOWN
	}

	private ElevatorDIRECTION elevatorDIRECTION = ElevatorDIRECTION.NONE;
	private Boolean move = false;
	private int ElevatorID;
	private boolean[] floors;
	private int cf = 0;
	private int min = ElevatorConstants.MIN_FLOOR;
	private int max = ElevatorConstants.MAX_FLOOR;
	private int numFloors;

	public Elevator(int numFloors) {
		if (numFloors < 0)
			throw new IllegalArgumentException();
		this.numFloors = numFloors;
		floors = new boolean[numFloors];
	}

	public Integer getCurrentFloor() {
		return cf;
	}

	public int getGoalFloor() {
		if (elevatorDIRECTION == ElevatorDIRECTION.UP)
			return max;
		if (elevatorDIRECTION == ElevatorDIRECTION.DOWN)
			return min;
		return -1;
	}

	public void setGoalFloor(int gf) {
		if ((gf < 0) || (gf >= numFloors))
			throw new IllegalArgumentException();
		if (cf == gf)
			return;
		if (floors[gf])
			return;
		floors[gf] = true;
		if (gf > cf) {
			max = (gf > max) ? (gf) : (max);
		}
		if (gf < cf) {
			min = (gf < min) ? (gf) : (min);
		}
		if (elevatorDIRECTION == ElevatorDIRECTION.NONE)
			elevatorDIRECTION = (gf > cf) ? (ElevatorDIRECTION.UP) : (ElevatorDIRECTION.DOWN);
	}

	public boolean getMove() {
		return move;
	}

	public ElevatorDIRECTION getDirection() {
		return elevatorDIRECTION;
	}

	public int getElevatorID() {
		return ElevatorID;
	}

	public void setElevatorID(int elevatorID) {
		ElevatorID = elevatorID;
	}

}