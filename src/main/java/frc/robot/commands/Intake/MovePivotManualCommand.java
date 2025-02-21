package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class MovePivotManualCommand {
    public static Command movePivot(IntakeSubsystem intake, double speed) {
        return new Command() {
            @Override
            public void execute() {
                if (false /* MAX_POS || MIN_POS */) {
                    end(true);
                } else {
                    intake.setHeight(intake.getHeight() + .1); 
                }
            }

            @Override
            public void end(boolean interrupted) {
                intake.stopPivot(); 
            }
        };
    }
}
