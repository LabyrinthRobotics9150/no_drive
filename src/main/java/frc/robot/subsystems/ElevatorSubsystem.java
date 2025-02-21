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
    private final PIDController pidController = new PIDController(0.1, 0, 0);
    public static final SparkLimitSwitch forwardLimitSwitch = elevatorMotor.getForwardLimitSwitch();
    public static final SparkLimitSwitch reverseLimitSwitch = elevatorMotor.getReverseLimitSwitch();

    // Motion profile constraints (max velocity and acceleration)
    private final TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(1.0, 0.5); // Adjust values as needed

    // Motion profile state
    private TrapezoidProfile.State targetState = new TrapezoidProfile.State();
    private TrapezoidProfile.State currentState = new TrapezoidProfile.State();

    private final Timer timer = new Timer();

    public ElevatorSubsystem() {
        elevatorEncoder.setPosition(0);
        timer.start();
    }

    @Override
    public void periodic() {
        // Generate a new motion profile
        TrapezoidProfile profile = new TrapezoidProfile(constraints, targetState, currentState);

        // Update the current state based on the profile
        currentState = profile.calculate(timer.get());

        // Use the PID controller to follow the profile
        double output = pidController.calculate(getHeight(), currentState.position);
        elevatorMotor.set(output);
    }

    public void setElevatorSpeed(double speed) {
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
    }

    public double getHeight() {
        return elevatorEncoder.getPosition();
    }

    public void setHeight(double targetHeight) {
        if ((forwardLimitSwitch.isPressed() && targetHeight > getHeight()) || 
            (reverseLimitSwitch.isPressed() && targetHeight < getHeight())) {
            stopElevator();
        } else {
            // Update the target state for the motion profile
            targetState = new TrapezoidProfile.State(targetHeight, 0); // Target velocity is 0
            timer.reset(); // Reset the timer for the new profile
        }
    }

    public void killElevator() {
        elevatorMotor.stopMotor(); 
        pidController.reset(); 
        targetState = new TrapezoidProfile.State(getHeight(), 0); // Hold the current position
    }
}