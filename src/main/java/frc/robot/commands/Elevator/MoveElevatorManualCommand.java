package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevatorManualCommand {
    public static Command moveElevator(ElevatorSubsystem elevator, double speed) {
        return new Command() {
            @Override
            public void initialize() {
                // Disable the motion profile when manually controlling the elevator
                elevator.stopElevator(); // Stop the motion profile and hold the current position
            }

            @Override
            public void execute() {
                // Check limit switches and set the speed
                if ((ElevatorSubsystem.forwardLimitSwitch.isPressed() && speed > 0) || 
                    (ElevatorSubsystem.reverseLimitSwitch.isPressed() && speed < 0)) {
                    elevator.stopElevator(); 
                } else {
                    elevator.setElevatorSpeed(speed); // Set the manual speed
                }
                System.out.println("height: " + elevator.getHeight());
            }

            @Override
            public void end(boolean interrupted) {
                elevator.stopElevator();
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }
}