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
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder(); //new Encoder(0, 1); 
    private final PIDController pidController = new PIDController(0.1, 0, 0);
    public static final SparkLimitSwitch forwardlimitswitch = elevatorMotor.getForwardLimitSwitch();
    public static final SparkLimitSwitch reverselimitswitch = elevatorMotor.getReverseLimitSwitch();
    private double holdPosition = 0.0; 

    public ElevatorSubsystem() {
    }

    public void setElevatorSpeed(double speed) {
        if (ElevatorSubsystem.forwardlimitswitch.isPressed() || 
        ElevatorSubsystem.reverselimitswitch.isPressed() ) {
            elevatorEncoder.setPosition(0);
            stopElevator();
        } else {
            elevatorMotor.set(speed);
            holdPosition = getHeight();
        }
    }

    public void stopElevator() {
        elevatorMotor.set(0);
        holdPosition = getHeight();
    }

    public double getHeight() {
        return elevatorEncoder.getPosition();
    }

    public void setHeight(double targetHeight) {
        double output = pidController.calculate(getHeight(), targetHeight);
        elevatorMotor.set(output);
        holdPosition = targetHeight; 
    }

    public void holdPosition() {
        double output = pidController.calculate(getHeight(), holdPosition);
        elevatorMotor.set(output);
    }

    public void killElevator() {
        elevatorMotor.stopMotor(); 
        pidController.reset(); 
        holdPosition = getHeight();
    }
    
}
