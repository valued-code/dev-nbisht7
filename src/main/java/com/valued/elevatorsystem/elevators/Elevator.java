package com.valued.elevatorsystem.elevators;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableSet;

import com.valued.elevatorsystem.elevators.ElevatorConstants.ElevatorState;

public class Elevator implements Runnable{

	private boolean operatingStatus;
	private int elevatorID;
	private ElevatorState elevatorCurrentState;
	private int currentElevatorFloor;

	// adds the floors from src to destination to show the progress
	private NavigableSet<Integer> elevatorFloorStops;

	//this map is required for elevators which needs to go UP first in order to come down and their
	//current floor is less than source floor which is requested.

	public Map<ElevatorState, NavigableSet<Integer>> elevatorFloorStopsMapping;

	public Elevator(int elevatorID) {
		this.setElevatorID(elevatorID);
		setOperating(true);
	}

	private void setOperating(boolean elevatorState) {
		this.operatingStatus = elevatorState;

		setElevatorCurrentState(ElevatorState.STATIONARY);
		this.elevatorFloorStopsMapping = new LinkedHashMap<ElevatorState, NavigableSet<Integer>>();

		// updates the manager that its ready to serve more requests
		ElevatorManager.updateElevatorLists(this);
		setCurrentElevatorFloor(0);
	}

	/**
	 * to move elevators up or down
	 */
	public void move() {
		// Synchronized with Elevator Manager.
		synchronized (ElevatorManager.getInstance()) { 
			Iterator<ElevatorState> iter = elevatorFloorStopsMapping.keySet().iterator();

			while (iter.hasNext()) {
				elevatorCurrentState = iter.next();

				//get floors in same direction
				elevatorFloorStops = elevatorFloorStopsMapping.get(elevatorCurrentState);
				iter.remove();
				Integer currFlr = null;
				Integer nextFlr = null;

				// move the elevators
				while (!elevatorFloorStops.isEmpty()) {

					if (elevatorCurrentState.equals(ElevatorState.UP)) {
						currFlr = elevatorFloorStops.pollFirst();
						nextFlr = elevatorFloorStops.higher(currFlr);

					} else if (elevatorCurrentState.equals(ElevatorState.DOWN)) {
						currFlr = elevatorFloorStops.pollLast();
						nextFlr = elevatorFloorStops.lower(currFlr);
					} else {
						return;
					}

					setCurrentElevatorFloor(currFlr);

					if (nextFlr != null) {
						// getting intermediate floors in requests
						generateFloors(currFlr, nextFlr);
					} else {
						setElevatorCurrentState(ElevatorState.STATIONARY);
						ElevatorManager.updateElevatorLists(this);
					}

					System.out.println("Current floor - "+ getCurrentElevatorFloor() +
							           "| Elevator ID " + this.getElevatorID() + 
							           "| Moving towards - " + getElevatorCurrentState());

					try {
						Thread.sleep(1000); // elevator travels 1 floor per second
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				// handling the intermediate requests
				ElevatorManager.getInstance().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * generates floor between src and dest
	 * 
	 * @param src
	 * @param dest
	 */
	private void generateFloors(Integer currFlr, Integer nextFlr) {
		if (currFlr == nextFlr) {
			return;
		}

		if (Math.abs(currFlr - nextFlr) == 1) {
			return;
		}

		int n = 1;
		if (nextFlr - currFlr < 0) {
			// moving DOWN
			n = -1;
		}

		while (currFlr != nextFlr) {
			currFlr += n;
			if (!elevatorFloorStops.contains(currFlr)) {
				elevatorFloorStops.add(currFlr);
			}
		}

	}
	
	public void run() {
		while (true) {
			if (isOperating()) {
				move();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
	}

	private boolean isOperating() {
		return this.operatingStatus;
	}

	public ElevatorState getElevatorCurrentState() {
		return elevatorCurrentState;
	}

	public void setElevatorCurrentState(ElevatorState elevatorCurrentState) {
		this.elevatorCurrentState = elevatorCurrentState;
	}

	public int getCurrentElevatorFloor() {
		return currentElevatorFloor;
	}

	public void setCurrentElevatorFloor(int currentElevatorFloor) {
		this.currentElevatorFloor = currentElevatorFloor;
	}

	public int getElevatorID() {
		return elevatorID;
	}

	public void setElevatorID(int elevatorID) {
		this.elevatorID = elevatorID;
	}

}