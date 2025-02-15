package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax; // Or use SparkMax if using CAN
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.math.controller.PIDController;

public class ElevatorSubsystem extends SubsystemBase {
    private final PWMSparkMax elevatorMotor = new PWMSparkMax(5); 
    private final Encoder elevatorEncoder = new Encoder(0, 1); 
    private final PIDController pidController = new PIDController(0.1, 0, 0); 
    private double holdPosition = 0.0; 

    public ElevatorSubsystem() {
        elevatorEncoder.setDistancePerPulse(0.01); 
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
        return elevatorEncoder.getDistance();
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
