package com.valued.elevatorsystem.elevators;

import com.valued.elevatorsystem.elevators.ElevatorConstants.ElevatorState;

public interface IElevatorManager {
	public ElevatorState getGoalDirection(InputParams inParams);
}
