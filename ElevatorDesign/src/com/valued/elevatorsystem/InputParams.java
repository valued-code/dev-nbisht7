package com.valued.elevatorsystem;

public class InputParams {
	private double timeInSeconds;
	private int sourceFloor;
	private int destFloor;

	public InputParams(double timeInSeconds, int sourceFloor, int destFloor) {
		this.setDestFloor(destFloor);
		this.setSourceFloor(sourceFloor);
		this.setTimeInSeconds(timeInSeconds);
	}

	public double getTimeInSeconds() {
		return timeInSeconds;
	}

	public void setTimeInSeconds(double timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
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

}
