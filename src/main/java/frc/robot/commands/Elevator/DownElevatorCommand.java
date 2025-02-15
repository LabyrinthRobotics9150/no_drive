package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class DownElevatorCommand {
    public static Command downElevator(ElevatorSubsystem elevator, double speed) {
        return new Command() {
            @Override
            public void execute() {
                if (ElevatorSubsystem.forwardlimitswitch.isPressed() || 
                ElevatorSubsystem.reverselimitswitch.isPressed() ) {
                    end(true);
                } else {
                    elevator.setElevatorSpeed(-speed); // Move at the given speed
                }            
            }

            @Override
            public void end(boolean interrupted) {
                elevator.stopElevator(); // Stop when button is released
            }
        };
    }
}
