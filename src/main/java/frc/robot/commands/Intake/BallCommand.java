package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class BallCommand extends Command {
    private final IntakeSubsystem intake;
    private final double targetHeight;
    private double startingHeight;
    private boolean atTarget = false;

    public BallCommand(IntakeSubsystem intake, double height) {
        this.intake = intake;
        this.targetHeight = height;
        addRequirements(intake);
    }

    /*

    @Override
    public void initialize() {
        startingHeight = intake.getHeight();
        atTarget = false; // Reset flag
        System.out.println("Moving elevator to height: " + targetHeight);
    }

    @Override
    public void execute() {
        if (!atTarget) {
            elevator.setHeight(targetHeight); // Move to preset
            if (Math.abs(elevator.getHeight() - targetHeight) < 0.5) {
                atTarget = true; // Mark as reached
            }
        } else {
            elevator.holdPosition(); // Hold at preset height
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Returning to starting position: " + startingHeight);
        elevator.setHeight(startingHeight);
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
    */
}
