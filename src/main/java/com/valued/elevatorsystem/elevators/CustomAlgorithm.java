package com.valued.elevatorsystem.elevators;

import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.valued.elevatorsystem.elevators.ElevatorConstants.ElevatorState;

public class CustomAlgorithm {

	public static Elevator pickElevator(ElevatorState elevatorState, int srcFloor, int destFloor,
		Map<Integer, Elevator> upMovingElevators, Map<Integer, Elevator> downMovingElevators) {
		Elevator elevator = null;

		//TreeMap distance of elevator from Source floor to current floor and the elevator id.
		TreeMap<Integer, Integer> srcDestDistMap = new TreeMap<Integer, Integer>();

		if (elevatorState.equals(ElevatorState.UP)) {

			// Checking elevators going UP
			for (Map.Entry<Integer, Elevator> elevatorMap : upMovingElevators.entrySet()) {
				Elevator availableElevators = elevatorMap.getValue();
				Integer distance = srcFloor - availableElevators.getCurrentElevatorFloor();
				if (distance < 0 && availableElevators.getElevatorCurrentState().equals(ElevatorState.UP)) {
			//skip the elevators which are already passed source floor
					continue;
				} else {
					srcDestDistMap.put(Math.abs(distance), availableElevators.getElevatorID());
				}
			}

			Integer selectedElevatorId = srcDestDistMap.firstEntry().getValue();
			elevator = upMovingElevators.get(selectedElevatorId);

		} else if (elevatorState.equals(ElevatorState.DOWN)) {
			// Checking elevators going DOWN
			for (Map.Entry<Integer, Elevator> elvMap : downMovingElevators.entrySet()) {
				Elevator elv = elvMap.getValue();
				Integer distance = elv.getCurrentElevatorFloor() - srcFloor;
				if (distance < 0 && elv.getElevatorCurrentState().equals(ElevatorState.DOWN)) {
					//skip the elevators which are already passed source floor
					continue;
				} else {
					srcDestDistMap.put(Math.abs(distance), elv.getElevatorID());
				}
			}
			
			Integer selectedElevatorId = srcDestDistMap.firstEntry().getValue();
			elevator = downMovingElevators.get(selectedElevatorId);

		}

		 
		InputParams incomingRequest = new InputParams(elevator.getCurrentElevatorFloor(), srcFloor);
		ElevatorManager em = ElevatorManager.getInstance();
		ElevatorState elevatorDirection = em.getGoalDirection(incomingRequest);

		
		InputParams newRequest = new InputParams(srcFloor, destFloor);
		ElevatorState elevatorDirection2 = em.getGoalDirection(newRequest);

		NavigableSet<Integer> floors = elevator.elevatorFloorStopsMapping.get(elevatorDirection);
		if (floors == null) {
			floors = new ConcurrentSkipListSet<Integer>();
		}

		floors.add(elevator.getCurrentElevatorFloor());
		floors.add(srcFloor);
		elevator.elevatorFloorStopsMapping.put(elevatorDirection, floors);

		NavigableSet<Integer> floorsSet = elevator.elevatorFloorStopsMapping.get(elevatorDirection2);
		if (floorsSet == null) {
			floorsSet = new ConcurrentSkipListSet<Integer>();
		}

		floorsSet.add(srcFloor);
		floorsSet.add(destFloor);
		elevator.elevatorFloorStopsMapping.put(elevatorDirection2, floorsSet);

		return elevator;
	}

}
