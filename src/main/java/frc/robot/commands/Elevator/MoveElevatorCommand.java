package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevatorCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final double targetHeight;

    public MoveElevatorCommand(ElevatorSubsystem elevator, double height) {
        this.elevator = elevator;
        this.targetHeight = height;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setHeight(targetHeight); // Set the target position
    }

    @Override
    public void execute() {
        // The elevator subsystem handles the motion profile in its periodic method
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stopElevator(); // Stop the elevator when the command ends
    }

    @Override
    public boolean isFinished() {
        // End the command when the elevator reaches the target position
        return Math.abs(elevator.getHeight() - targetHeight) < 0.01; // Tolerance for position
    }
}