package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevatorCommand {
    public static Command moveToHeight(ElevatorSubsystem elevator, double height) {
        return new Command() {
            @Override
            public void initialize() {
                System.out.println("Moving elevator to height: " + height);
            }

            @Override
            public void execute() {
                elevator.setHeight(height);
            }

            @Override
            public void end(boolean interrupted) {
                elevator.stopElevator();
            }

            @Override
            public boolean isFinished() {
                return Math.abs(elevator.getHeight() - height) < 0.5; // Stop when close
            }
        };
    }
}
