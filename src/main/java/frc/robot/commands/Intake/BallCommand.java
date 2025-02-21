package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class BallCommand extends Command {
    private final IntakeSubsystem intake;

    public BallCommand(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setHeight(intake.BALL_POSITION); // Move to ball position
    }

    @Override
    public void execute() {
        // The intake subsystem handles the motion profile in its periodic method
    }

    @Override
    public void end(boolean interrupted) {
        intake.setHeight(intake.HOME_POSITION); // Return to home position
    }

    @Override
    public boolean isFinished() {
        // End the command when the pivot reaches the target position
        return Math.abs(intake.getHeight() - intake.BALL_POSITION) < 0.01; // Tolerance for position
    }
}