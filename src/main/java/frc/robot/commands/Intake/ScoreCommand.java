package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;


public class ScoreCommand extends Command {
    IntakeSubsystem intake;
    public ScoreCommand(IntakeSubsystem intake) {
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // move the arm of the pivot bar out of the way, and score the coral/ball.
    }

    @Override
    public void end(boolean interrupted) {
        // when command is done, ensure pivot bar returns to original position
    }

    @Override
    public boolean isFinished() {
        // this command should be finite, but will confirm later.
        return true;
    }
}
