package com.valued.elevatorsystem.elevators;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InputParamsTest {
	private ElevatorManager elevatorManager;
	private Thread elevatorManagerThread;

	/**
	 * Starts the elevator threads
	 */
	@Before
	public void setUp() throws Exception {
		elevatorManager = ElevatorManager.getInstance();
		elevatorManagerThread = new Thread(elevatorManager);
		elevatorManagerThread.start();

	}
	/**
	 * Stops the elevator threads
	 */
	@After
	public void tearDown() throws Exception {
		if (!elevatorManager.isStopController()) {
			elevatorManager.setStopElevatorManager(true);
		}

	}

	@Test
	public void testSubmitRequest1() throws Exception {
		System.out.println("Test1");
		InputParams elevatorRequest = new InputParams(0, 3);
		Elevator elevator = elevatorRequest.submitRequest();
		Thread.sleep(6000);
		assertEquals(3, elevator.getCurrentElevatorFloor());
	}

	@Test
	public void testSubmitRequest2() throws Exception {
		System.out.println("Test2");
		InputParams elevatorRequest = new InputParams(0, 2);
		Elevator elevator = elevatorRequest.submitRequest();
		Thread.sleep(4000);
		InputParams elevatorRequest1 = new InputParams(4, 5);
		elevator = elevatorRequest1.submitRequest();
		Thread.sleep(12000);
		assertEquals(5, elevator.getCurrentElevatorFloor());
	}

	@Test
	public void testSubmitRequest3() throws Exception {
		System.out.println("Test3");
		InputParams elevatorRequest = new InputParams(1, 8);
		Elevator elevator = elevatorRequest.submitRequest();
		Thread.sleep(2000);
		InputParams elevatorRequest1 = new InputParams(4, 6);
		elevator = elevatorRequest1.submitRequest();
		Thread.sleep(10000);
		assertEquals(6, elevator.getCurrentElevatorFloor());
	}

	@Test
	public void testSubmitRequest4() throws Exception {
		System.out.println("Test4");
		InputParams elevatorRequest = new InputParams(0, 3);
		Elevator elevator = elevatorRequest.submitRequest();
		Thread.sleep(7000);
		InputParams elevatorRequest1 = new InputParams(3, 0);
		elevator = elevatorRequest1.submitRequest();
		Thread.sleep(6000);
		assertEquals(0, elevator.getCurrentElevatorFloor());
	}

	@Test
	public void testSubmitRequest5() throws Exception {
		System.out.println("Test5");
		InputParams elevatorRequest = new InputParams(4, 1);
		Elevator elevator = elevatorRequest.submitRequest();
		Thread.sleep(10000);
		assertEquals(1, elevator.getCurrentElevatorFloor());
	}
}
