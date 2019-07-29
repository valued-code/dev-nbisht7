package com.valued.elevatorsystem;

import java.util.List;

import com.valued.elevatorsystem.Elevator.ElevatorDIRECTION;

public interface IElevatorManager {
	public List<Elevator> getAllElevators();
	public ElevatorDIRECTION getGoalDirection(int destFloor, int srcFloor);
}
