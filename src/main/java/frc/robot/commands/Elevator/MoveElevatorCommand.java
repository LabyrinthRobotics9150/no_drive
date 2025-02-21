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
        // handled in periodic
    }

    @Override
    public void end(boolean interrupted) {
        elevator.setHeight(startingHeight); // Return to the starting position
    }

    @Override
    public boolean isFinished() {
        return false; // Run until interrupted
    }
}