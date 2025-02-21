package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;

public class ElevatorSubsystem extends SubsystemBase {
    public static final SparkFlex elevatorMotor = new SparkFlex(Constants.OperatorConstants.kElevatorLeaderCanId, MotorType.kBrushless); 
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
    private final PIDController pidController = new PIDController(0.1, 0, 0);
    public static final SparkLimitSwitch forwardLimitSwitch = elevatorMotor.getForwardLimitSwitch();
    public static final SparkLimitSwitch reverseLimitSwitch = elevatorMotor.getReverseLimitSwitch();
    private double targetPosition = 0.0; 

    public ElevatorSubsystem() {
        // Initialize the encoder position at 0
        elevatorEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        // Continuously adjust the elevator position to the target position
        double output = pidController.calculate(getHeight(), targetPosition);
        elevatorMotor.set(output);
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
        targetPosition = getHeight(); // Hold the current position
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
            targetPosition = targetHeight;
        }
    }

    public void killElevator() {
        elevatorMotor.stopMotor(); 
        pidController.reset(); 
        targetPosition = getHeight(); // Hold the current position
    }
}