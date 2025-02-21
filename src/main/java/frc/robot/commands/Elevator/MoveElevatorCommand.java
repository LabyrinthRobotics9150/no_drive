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
        elevator.setHeight(targetHeight);
        System.out.println("moved to level 1"); 
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            elevator.setHeight(startingHeight); 
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(elevator.getHeight() - targetHeight) < 0.01;
    }
}