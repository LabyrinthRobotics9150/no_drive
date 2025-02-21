package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevatorCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final double targetHeight;
    private double startingHeight;

    public MoveElevatorCommand(ElevatorSubsystem elevator, double height) {
        this.elevator = elevator;
        this.targetHeight = height;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        startingHeight = elevator.getHeight(); 
        elevator.setHeight(targetHeight); // Set the target position
    }

    @Override
    public void execute() {
        // Motion profile is handled in the subsystem's periodic method
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            elevator.setHeight(startingHeight); // Return to the starting position if interrupted
        }
    }

    @Override
    public boolean isFinished() {
        // Check if the elevator has reached the target position
        return Math.abs(elevator.getHeight() - targetHeight) < 0.01; // Tolerance for position
    }
}