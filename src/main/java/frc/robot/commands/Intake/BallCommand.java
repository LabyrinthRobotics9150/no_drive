package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class BallCommand extends Command {
    private final IntakeSubsystem intake;

    // not currently functioning. Hardware problem?
    private final double homePosition = 0.0;
    private final double ballPosition = 0.571; 
    private double targetPosition;

    public BallCommand(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        targetPosition = ballPosition; 
    }

    @Override
    public void execute() {
        intake.setHeight(targetPosition); 
    }

    @Override
    public void end(boolean interrupted) {
        targetPosition = homePosition; 
        intake.setHeight(targetPosition);
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}
