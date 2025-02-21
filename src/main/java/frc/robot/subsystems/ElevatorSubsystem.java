package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;

public class ElevatorSubsystem extends SubsystemBase {
    public static final SparkFlex elevatorMotor = new SparkFlex(Constants.OperatorConstants.kElevatorLeaderCanId, MotorType.kBrushless); 
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
    private final PIDController pidController = new PIDController(0.01, 0, 0);
    public static final SparkLimitSwitch forwardLimitSwitch = elevatorMotor.getForwardLimitSwitch();
    public static final SparkLimitSwitch reverseLimitSwitch = elevatorMotor.getReverseLimitSwitch();

    private TrapezoidProfile motionProfile;
    private final Timer profileTimer = new Timer();
    private TrapezoidProfile.State targetState = new TrapezoidProfile.State();
    private TrapezoidProfile.State currentState = new TrapezoidProfile.State();

    // (max velocity and acceleration)
    private final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(
        .50, // (units per second)
        0.25  // (units per second squared)
    );

    public ElevatorSubsystem() {
        // Initialize the encoder position at 0
        elevatorEncoder.setPosition(0);
        profileTimer.start();

        // Initialize the motion profile with limits
        motionProfile = new TrapezoidProfile(constraints);
    }

    @Override
    public void periodic() {
        // Update the motion profile
        if (motionProfile != null) {
            double elapsedTime = profileTimer.get();
            currentState = motionProfile.calculate(elapsedTime, currentState, targetState);

            // Set the elevator motor output based on the motion profile
            double output = pidController.calculate(getHeight(), currentState.position);
            elevatorMotor.set(output);
        }
    }

    public void setElevatorSpeed(double speed) {
        // Safety checks for limit switches
        if ((forwardLimitSwitch.isPressed() && speed > 0) || 
            (reverseLimitSwitch.isPressed() && speed < 0)) {
            stopElevator();
        } else {
            elevatorMotor.set(speed);
        }
    }

    public void stopElevator() {
        elevatorMotor.set(0);
        targetState = new TrapezoidProfile.State(getHeight(), 0); // Hold the current position
        motionProfile = null; // Stop the motion profile
    }

    public double getHeight() {
        return elevatorEncoder.getPosition();
    }

    public void setHeight(double targetHeight) {
        // Safety checks for limit switches
        if ((forwardLimitSwitch.isPressed() && targetHeight > getHeight()) || 
            (reverseLimitSwitch.isPressed() && targetHeight < getHeight())) {
            stopElevator();
        } else {
            // Create a new motion profile
            targetState = new TrapezoidProfile.State(targetHeight, 0);
            currentState = new TrapezoidProfile.State(getHeight(), 0);
            motionProfile = new TrapezoidProfile(constraints);
            profileTimer.reset(); // Reset the timer for the new motion profile
        }
    }

    public void killElevator() {
        elevatorMotor.stopMotor(); 
        pidController.reset(); 
        targetState = new TrapezoidProfile.State(getHeight(), 0); // Hold the current position
        motionProfile = null; // Stop the motion profile
    }
}