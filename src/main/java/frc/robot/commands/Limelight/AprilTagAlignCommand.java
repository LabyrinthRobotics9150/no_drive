package frc.robot.commands.Limelight;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.LimelightSubsystem;

public class AprilTagAlignCommand extends Command {
    //private final Drivetrain drivetrain;
    private final LimelightSubsystem limelight;
    
    private final PIDController rotationController;
    private final PIDController distanceController;

    // Constants
    private static final double TARGET_DISTANCE = 1.0; // meters
    private static final double MAX_SPEED = 3.0; // meters per second
    private static final double MAX_ANGULAR_SPEED = Math.PI; // radians per second
    private static final double LIMELIGHT_HEIGHT = 0.5; // meters
    private static final double APRILTAG_HEIGHT = 0.2; // meters
    private static final double LIMELIGHT_MOUNT_ANGLE = 30.0; // degrees

    public AprilTagAlignCommand(/* Drivetrain drivetrain, */ LimelightSubsystem limelight) {
        //this.drivetrain = drivetrain;
        this.limelight = limelight;

        rotationController = new PIDController(0.1, 0, 0.01); // Tune these values
        distanceController = new PIDController(0.5, 0, 0.1); // Tune these values

        rotationController.setTolerance(1.0); // degrees tolerance
        distanceController.setTolerance(0.1); // meters tolerance

        addRequirements(/*drivetrain */, limelight);
    }

    @Override
    public void initialize() {
        limelight.setLedMode(3); // Turn on LEDs
        limelight.setPipeline(0); // AprilTag pipeline
    }

    @Override
    public void execute() {
        if (!limelight.hasTarget()) return;

        double tx = limelight.getTargetX();
        double ty = limelight.getTargetY();
        double distance = calculateDistance(ty);

        double rotationSpeed = -rotationController.calculate(tx, 0);
        double forwardSpeed = -distanceController.calculate(distance, TARGET_DISTANCE);

        // Apply speed limits
        rotationSpeed = Math.max(-MAX_ANGULAR_SPEED, Math.min(rotationSpeed, MAX_ANGULAR_SPEED));
        forwardSpeed = Math.max(-MAX_SPEED, Math.min(forwardSpeed, MAX_SPEED));


        /*
        
        drivetrain.drive(
            new Translation2d(forwardSpeed, 0),
            rotationSpeed,
            true, // field-relative
            true // keep heading when not rotating
        );

         */

    }

    private double calculateDistance(double ty) {
        double angleToTarget = LIMELIGHT_MOUNT_ANGLE + ty;
        return (APRILTAG_HEIGHT - LIMELIGHT_HEIGHT) / 
               Math.tan(Math.toRadians(angleToTarget));
    }

    @Override
    public boolean isFinished() {
        return rotationController.atSetpoint() && 
               distanceController.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.drive(new Translation2d(0, 0), 0, true, true);
        limelight.setLedMode(1); // Turn off LEDs
    }
}