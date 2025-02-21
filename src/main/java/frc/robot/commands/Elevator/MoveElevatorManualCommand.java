package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class MoveElevatorManualCommand {
    public static Command moveElevator(ElevatorSubsystem elevator, double speed) {
        return new Command() {
            @Override
            public void execute() {
                if ( (ElevatorSubsystem.forwardLimitSwitch.isPressed() && speed > 0) || 
                ElevatorSubsystem.reverseLimitSwitch.isPressed() && speed < 0) {
                } else {
                    elevator.setElevatorSpeed(speed);
                    System.out.println("height: " + elevator.elevatorMotor.getEncoder().getPosition());
                } 
            }

            @Override
            public void end(boolean interrupted) {
                elevator.stopElevator();
            }
        };
    }
}
