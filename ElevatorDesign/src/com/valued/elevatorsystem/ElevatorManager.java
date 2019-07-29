package com.valued.elevatorsystem;

import java.util.Arrays;
import java.util.List;

import com.valued.elevatorsystem.Elevator.ElevatorDIRECTION;

public class ElevatorManager implements IElevatorManager {
	private int numOfElevators;
	private int numOfFloors;
	private Elevator[] elevators = null;

	private void initElevators(int numElevators, int numFloors) {
		if (numElevators < 0)
			throw new IllegalArgumentException();
		elevators = new Elevator[numElevators];
		for (int i = 0; i < numElevators; i++) {
			Elevator el = new Elevator(numFloors);
			el.setElevatorID(i);
			elevators[i] = el;
		}
	}

	private void initFloors(int numFloors) {
		if (numFloors < 0)
			throw new IllegalArgumentException();
		}

	public ElevatorManager(int numElevators, int numFloors) {
		initFloors(numFloors);
		initElevators(numElevators, numFloors);
		this.numOfElevators = numElevators;
		this.numOfFloors = numFloors;
	}

	@Override
	public List<Elevator> getAllElevators() {
		return Arrays.asList(elevators);
	}

	@Override
	public ElevatorDIRECTION getGoalDirection(int destFloor, int srcFloor) {
		if ((destFloor - srcFloor) > 0)
			return ElevatorDIRECTION.UP;
		else if ((destFloor - srcFloor) < 0)
			return ElevatorDIRECTION.DOWN;
		return ElevatorDIRECTION.NONE;
	}

}
