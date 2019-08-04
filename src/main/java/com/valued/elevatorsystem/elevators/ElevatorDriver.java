package com.valued.elevatorsystem.elevators;

import java.util.Scanner;

/**
 * This program simulates the sync elevator working. 
 * 1. Elevators closest to floor are referred with highest priority.
 * 2. Elevators in motion have more priority over stationary elevators.
 */
public class ElevatorDriver {
	double timeInSeconds;
	int sourceFloor;
	int destFloor;
	private static ElevatorManager elevatorManager;
	private static Thread elevatorManagerThread;

	public static void main(String[] args) {
		System.out.println("Welcome to Elevator Program!!!");
		elevatorManager = ElevatorManager.getInstance();
		elevatorManagerThread = new Thread(elevatorManager);
		elevatorManagerThread.start();

		Scanner sc = new Scanner(System.in);
		while (true) {
			
			System.out.println("Enter the floor where elevator is requested from (1 to 16): ");
			int requestFloor = sc.nextInt();
			System.out.println("Source Floor is - " + requestFloor);
			
			System.out.println("Enter the destination floor(1 to 16): ");
			int targetFloor = sc.nextInt();
			System.out.println("Destination Floor is - " + targetFloor);
			
			InputParams inputParams = new InputParams(requestFloor, targetFloor);
			inputParams.submitRequest();
		}

	}
}
