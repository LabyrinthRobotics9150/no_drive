package frc.robot.subsystems;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase  {
        public static final SparkFlex IntakePivotMotor = new SparkFlex(Constants.OperatorConstants.kIntakePivotCanId, MotorType.kBrushless); 
        public static final SparkFlex IntakeWheelsMotor = new SparkFlex(Constants.OperatorConstants.kIntakeWheelsCanId, MotorType.kBrushless);
        private final PIDController pidController = new PIDController(0.1, 0, 0);

        SparkAbsoluteEncoder intakePivotEncoder = IntakePivotMotor.getAbsoluteEncoder();
        private int MAX_POSITION;
        private int MIN_POSITION;
        private double holdPosition; 

        // pivot motor
        public void setPivotSpeed(double speed) {
            if (true /*  MAX_POSITION || MIN_POSITION */) {
                IntakePivotMotor.set(speed);
            } else {
                IntakePivotMotor.set(speed);
            }
        }
    
        public void stopPivot() {
            IntakePivotMotor.set(0);
            holdPosition = getHeight();
        }
    
        public double getHeight() {
            return intakePivotEncoder.getPosition();
        }
    
        public void setHeight(double targetHeight) {
            double output = pidController.calculate(getHeight(), targetHeight);
            IntakePivotMotor.set(output);
            holdPosition = targetHeight; 
        }
    
        public void holdPosition() {
            double output = pidController.calculate(getHeight(), holdPosition);
            IntakePivotMotor.set(output);
        }
    
        public void killPivot() {
            IntakePivotMotor.stopMotor();  
            holdPosition = getHeight();
        }

        // wheels motor
}
