package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax; // Or use SparkMax if using CAN
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.math.controller.PIDController;

public class ElevatorSubsystem extends SubsystemBase {
    private final PWMSparkMax elevatorMotor = new PWMSparkMax(5); // Change to correct port
    private final Encoder elevatorEncoder = new Encoder(0, 1); // Update ports
    private final PIDController pidController = new PIDController(0.1, 0, 0); // Tune PID values

    public ElevatorSubsystem() {
        elevatorEncoder.setDistancePerPulse(0.01); // Set conversion factor
    }

    public void setElevatorSpeed(double speed) {
        elevatorMotor.set(speed);
    }

    public void stopElevator() {
        elevatorMotor.set(0);
    }

    public double getHeight() {
        return elevatorEncoder.getDistance();
    }

    public void setHeight(double targetHeight) {
        double output = pidController.calculate(getHeight(), targetHeight);
        elevatorMotor.set(output);
    }
}
