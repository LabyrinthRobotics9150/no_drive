package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class WheelMoveCommand {
    public static Command moveElevator(IntakeSubsystem intake, double speed) {
        return new Command() {
            @Override
            public void execute() {
                intake.moveWheel(speed);
            }

            @Override
            public void end(boolean interrupted) {
                intake.stopWheel(); 
            }
        };
    }
}
