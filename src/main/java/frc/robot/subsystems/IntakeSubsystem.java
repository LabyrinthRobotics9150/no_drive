package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase  {
    public static final SparkFlex IntakePivotMotor = new SparkFlex(Constants.OperatorConstants.kIntakePivotCanId, MotorType.kBrushless); 
    public static final SparkFlex IntakeWheelsMotor = new SparkFlex(Constants.OperatorConstants.kIntakeWheelsCanId, MotorType.kBrushless);
    private final PIDController pidController = new PIDController(0.1, 0, 0);

    AbsoluteEncoder intakePivotEncoder = IntakePivotMotor.getAbsoluteEncoder();
    // home - 0.9278091788291931
    // ball - 0.5219756960868835
    public double HOME_POSITION = 0.84;
    public double BALL_POSITION = 0.3;
    private double targetPosition = HOME_POSITION; 

    public IntakeSubsystem() {
        setHeight(HOME_POSITION);
    }

    @Override
    public void periodic() {
        // Continuously adjust the arm position to the target position
        double output = pidController.calculate(getHeight(), targetPosition);
        IntakePivotMotor.set(output);
    }

    // pivot motor
    public void setPivotSpeed(double speed) {
        if (true /*  MAX_POSITION || MIN_POSITION */) {
            IntakePivotMotor.set(speed);
        } else {
            killPivot();
        }
    }

    public void stopPivot() {
        IntakePivotMotor.stopMotor();
    }

    public double getHeight() {
        return intakePivotEncoder.getPosition();
    }

    public void setHeight(double targetHeight) {
        targetPosition = targetHeight;
    }

    public void killPivot() {
        IntakePivotMotor.stopMotor();  
    }

    // wheels motor
    public void moveWheel(double speed) {
        IntakeWheelsMotor.set(speed);
    }

    public void stopWheel() {
        IntakeWheelsMotor.stopMotor();
    }
}