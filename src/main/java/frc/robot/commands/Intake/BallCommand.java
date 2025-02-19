package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class BallCommand extends Command {
    private final IntakeSubsystem intake;
    private final double homePosition = 0.0; // Adjust if needed
    private final double ballPosition = 0.571; // Adjust if needed
    private double targetPosition;

    public BallCommand(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        targetPosition = ballPosition; // Move to BALL_POSITION when button is pressed
        System.out.println("Moving arm to BALL position: " + targetPosition);
    }

    @Override
    public void execute() {
        intake.setHeight(targetPosition); // Actively hold position using PID
    }

    @Override
    public void end(boolean interrupted) {
        targetPosition = homePosition; // When button is released, move back to HOME
        System.out.println("Returning to HOME position: " + targetPosition);
        intake.setHeight(targetPosition);
    }

    @Override
    public boolean isFinished() {
        return false; // Runs continuously while the button is held
    }
}
