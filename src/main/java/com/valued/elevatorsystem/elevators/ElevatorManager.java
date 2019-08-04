package com.valued.elevatorsystem.elevators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.valued.elevatorsystem.elevators.ElevatorConstants.ElevatorState;

public class ElevatorManager implements IElevatorManager, Runnable {
	private boolean stopElevatorManager;

	// Up moving
	private static Map<Integer, Elevator> upMovingElevators = new HashMap<Integer, Elevator>();

	// DOWN moving
	private static Map<Integer, Elevator> downMovingElevators = new HashMap<Integer, Elevator>();
	// STATIONARY elevators

	private static List<Elevator> elevators = new ArrayList<Elevator>(ElevatorConstants.ELEVATORS);

	private static final ElevatorManager elevatorInstance = new ElevatorManager();

	public ElevatorManager() {
		if (elevatorInstance != null) {
			throw new IllegalStateException("Already instantiated");
		}
		setStopElevatorManager(false);
		initElevators(ElevatorConstants.ELEVATORS);
	}

	private void initElevators(int numElevators) {
		for (int i = 0; i < numElevators; i++) {
			Elevator elevator = new Elevator(i);
			Thread t = new Thread(elevator);
			t.start();
			elevators.add(elevator);
		}
	}

	/**
	 * Selects the elevator from the list of elevators using Custom Algorithm
	 * 
	 * @param requestFromUser
	 *           
	 * @return returns selected Elevator
	 */
	public synchronized Elevator selectElevator(InputParams inParams) {

		Elevator elevator = null;

		ElevatorConstants.ElevatorState elevatorState = getGoalDirection(inParams);
		int requestedFloor = inParams.getSourceFloor();
		int targetFloor = inParams.getDestFloor();
		
		elevator = CustomAlgorithm.pickElevator(elevatorState, requestedFloor, targetFloor, upMovingElevators,
				downMovingElevators);
		// once the elevator is picked notify other elevators
		notifyAll();
		return elevator;

	}


	public void run() {
		stopElevatorManager = false;
		while (true) {
			try {
				Thread.sleep(1000);
				if (stopElevatorManager) {
					break;
				}
			} catch (InterruptedException e) {
				System.out.println(e.getStackTrace());
			}
		}
	}


	public ElevatorState getGoalDirection(InputParams inParams) {
		ElevatorState elevatorState = null;
		int requestedFloor = inParams.getSourceFloor();
		int targetFloor = inParams.getDestFloor();

		if (targetFloor - requestedFloor > 0) {
			elevatorState = ElevatorState.UP;
		} else {
			elevatorState = ElevatorState.DOWN;
		}

		return elevatorState;
	}

	public static ElevatorManager getInstance() {
		return elevatorInstance;
	}

	/**
	 * updating the state of elevator in elevatorList
	 * 
	 * @param CurrentElevator
	 */
	public static synchronized void updateElevatorLists(Elevator elevator) {
		if (elevator.getElevatorCurrentState().equals(ElevatorState.UP)) {
			upMovingElevators.put(elevator.getElevatorID(), elevator);
			downMovingElevators.remove(elevator.getElevatorID());
		} else if (elevator.getElevatorCurrentState().equals(ElevatorState.DOWN)) {
			downMovingElevators.put(elevator.getElevatorID(), elevator);
			upMovingElevators.remove(elevator.getElevatorID());
		} else if (elevator.getElevatorCurrentState().equals(ElevatorState.STATIONARY)) {
			upMovingElevators.put(elevator.getElevatorID(), elevator);
			downMovingElevators.put(elevator.getElevatorID(), elevator);
		} 

	}

	public synchronized List<Elevator> getElevatorList() {
		return elevators;
	}

	public boolean isStopController() {
		return stopElevatorManager;
	}

	public void setStopElevatorManager(boolean stopElevatorManager) {
		this.stopElevatorManager = stopElevatorManager;
	}

}
