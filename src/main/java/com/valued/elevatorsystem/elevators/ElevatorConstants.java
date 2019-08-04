package com.valued.elevatorsystem.elevators;

public class ElevatorConstants {
	public static final int FLOORS = 16;
	public static final int ELEVATORS = 3;
	public static final int MIN_FLOOR = FLOORS + 1;
	public static final int MAX_FLOOR = -1;
	public enum ElevatorState {
	    UP,
	    DOWN,
	    STATIONARY
	}
}