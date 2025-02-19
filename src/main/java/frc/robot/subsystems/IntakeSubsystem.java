package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
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

        AbsoluteEncoder intakePivotEncoder = IntakePivotMotor.getAbsoluteEncoder();
        // home - 0.9278091788291931
        // ball - 0.5219756960868835
        private double HOME_POSITION = 0.0;
        private double BALL_POSITION = 0.571;
        private double holdPosition; 

        public IntakeSubsystem() {
            setHeight(HOME_POSITION);
        }

        // pivot motor
        public void setPivotSpeed(double speed) {
            if (true /*  MAX_POSITION || MIN_POSITION */) {
                IntakePivotMotor.set(speed);
            } else {
                killPivot();;
            }
        }
    
        public void stopPivot() {
            IntakePivotMotor.set(0);
            holdPosition = getHeight();
            holdPosition();
        }
    
        public double getHeight() {
            return intakePivotEncoder.getPosition();
        }
    
        public void setHeight(double targetHeight) {
            double currentPosition = getHeight();
            double error = calculateShortestError(currentPosition, targetHeight);
            double output = pidController.calculate(0, error);
            IntakePivotMotor.set(output);
            holdPosition = targetHeight;
            holdPosition();
        }
    
        public void holdPosition() {
            double currentPosition = getHeight();
            double error = calculateShortestError(currentPosition, holdPosition);
            double output = pidController.calculate(0, error);
            IntakePivotMotor.set(output);
        }
    
        public void killPivot() {
            IntakePivotMotor.stopMotor();  
            holdPosition = getHeight();
        }

        private double calculateShortestError(double currentPosition, double targetPosition) {
            double error = targetPosition - currentPosition;
            if (error > 0.5) {
                error -= 1.0; 
            } else if (error < -0.5) {
                error += 1.0; 
            }
            return error;
        }

        // wheels motor
}
