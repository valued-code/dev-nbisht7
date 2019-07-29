package com.valued.elevatorsystem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.valued.elevatorsystem.Elevator.ElevatorDIRECTION;

public class ElevatorDriver {
	double timeInSeconds;
	int sourceFloor;
	int destFloor;

	public static void main(String[] args) {
		System.out.println("Welcome to Elevator Program!!!");
		ElevatorDriver ed = new ElevatorDriver();
		InputParams inParams = ed.getInputParams();
		ElevatorManager ec = new ElevatorManager(ElevatorConstants.ELEVATORS, ElevatorConstants.FLOORS);

		Elevator e = new Elevator(ElevatorConstants.FLOORS);
		e.setGoalFloor(inParams.getDestFloor());

		// getting goal direction of the user i.e either UP,DOWN,NONE.
		ElevatorDIRECTION d = ec.getGoalDirection(inParams.getDestFloor(), inParams.getSourceFloor());

		// getting current Positions of all the elevators.
		List<Elevator> currPositions = ec.getAllElevators();

		// custom comparator for elevators
		Comparator<Elevator> compareByFloor = new Comparator<Elevator>() {
			@Override
			public int compare(Elevator o1, Elevator o2) {
				return o1.getCurrentFloor().compareTo(o2.getCurrentFloor());
			}
		};

		// sorting the elevators by positions.
		Collections.sort(currPositions, compareByFloor);
		Elevator closest = currPositions.get(0);
		// if two elevators at same positions go for tieBreaker
		if (currPositions.get(0).getCurrentFloor() == currPositions.get(1).getCurrentFloor())
			closest = sameDistTieBreaker(currPositions, d);


	}

	private static Elevator sameDistTieBreaker(List<Elevator> currPositions, ElevatorDIRECTION d) {
		Elevator e1 = currPositions.get(0);
		Elevator e2 = currPositions.get(1);
		// if two elevators which are at same distance from source moving in same
		// direction
		if (e1.getDirection() == e2.getDirection()) {
			return directionTieBreaker(e1, e2);
		} else if (e1.getDirection() == d)
			// return if elevator 1 is is moving in same direction
			return e1;
		else
			return e2;

	}

	private static Elevator directionTieBreaker(Elevator e1, Elevator e2) {
		// return e1 if it is moving
		if (e1.getMove())
			return e1;
		// return e2 if e1 is idle
		return e2;
	}

	private InputParams getInputParams() {
		Scanner sc = new Scanner(System.in);
		timeInSeconds = sc.nextDouble();
		sourceFloor = sc.nextInt();
		destFloor = sc.nextInt();
		sc.close();
		return new InputParams(timeInSeconds, sourceFloor, destFloor);
	}

}
