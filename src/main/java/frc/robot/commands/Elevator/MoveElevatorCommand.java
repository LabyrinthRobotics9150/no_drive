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
        System.out.println("Moving elevator to height: " + targetHeight);
    }

    @Override
    public void execute() {
        elevator.setHeight(targetHeight); 
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Returning to starting position: " + startingHeight);
        elevator.setHeight(startingHeight); 
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
