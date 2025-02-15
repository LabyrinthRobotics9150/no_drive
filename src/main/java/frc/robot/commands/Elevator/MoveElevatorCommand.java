package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevatorCommand extends Command {
    private final ElevatorSubsystem elevator;
    private final double targetHeight;
    private double startingHeight;
    private boolean atTarget = false;

    public MoveElevatorCommand(ElevatorSubsystem elevator, double height) {
        this.elevator = elevator;
        this.targetHeight = height;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        startingHeight = 0; // elevator.getHeight(); - if we want to use starting position from before
        atTarget = false; // Reset flag
        System.out.println("Moving elevator to height: " + targetHeight);
    }

    @Override
    public void execute() {
        if (!atTarget) {
            elevator.setHeight(targetHeight); // Move to preset
            if (Math.abs(elevator.getHeight() - targetHeight) < 0.5) {
                atTarget = true; // Mark as reached
            }
        } else {
            elevator.holdPosition(); // Hold at preset height
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Returning to starting position: " + startingHeight);
        elevator.setHeight(startingHeight);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}
