package com.valued.elevatorsystem.elevators;

/**
 * 
 *   This class takes Input Parameters from users and initializes the floors.
 */
public class InputParams {
	private int sourceFloor;
	private int destFloor;

	public InputParams(int sourceFloor, int destFloor) {
		this.setDestFloor(destFloor);
		this.setSourceFloor(sourceFloor);
	}

	public int getSourceFloor() {
		return sourceFloor;
	}

	public void setSourceFloor(int sourceFloor) {
		this.sourceFloor = sourceFloor;
	}

	public int getDestFloor() {
		return destFloor;
	}

	public void setDestFloor(int destFloor) {
		this.destFloor = destFloor;
	}
	
	/**
	 *Gets the requests from users and gets the instance 
	 *return - selected elevator
	 */
	public Elevator submitRequest() {
		    	 return ElevatorManager.getInstance().selectElevator(InputParams.this);
	}
}
