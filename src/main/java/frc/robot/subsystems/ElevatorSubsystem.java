package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;

public class ElevatorSubsystem extends SubsystemBase {
    private final SparkFlex elevatorMotor = new SparkFlex(5, MotorType.kBrushless); 
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder(); //new Encoder(0, 1); 
    private final PIDController pidController = new PIDController(0.1, 0, 0); 
    private double holdPosition = 0.0; 

    public ElevatorSubsystem() {
    }

    public void setElevatorSpeed(double speed) {
        elevatorMotor.set(speed);
        holdPosition = getHeight();
    }

    public void stopElevator() {
        elevatorMotor.set(0);
        holdPosition = getHeight(); // Update hold position when stopped
    }

    public double getHeight() {
        return elevatorEncoder.getPosition();
    }

    public void setHeight(double targetHeight) {
        double output = pidController.calculate(getHeight(), targetHeight);
        elevatorMotor.set(output);
        holdPosition = targetHeight; // Set hold position to target
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
