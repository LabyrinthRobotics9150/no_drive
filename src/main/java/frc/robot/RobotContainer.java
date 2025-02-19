// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Elevator.*;
import frc.robot.commands.Intake.BallCommand;
import frc.robot.commands.Intake.MovePivotManualCommand;
import frc.robot.commands.Limelight.AprilTagAlignCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
    private final ElevatorSubsystem m_elevator = new ElevatorSubsystem();
    private final IntakeSubsystem m_intake = new IntakeSubsystem();
    private final LimelightSubsystem limelight = new LimelightSubsystem();



  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_primaryController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
    
  private final CommandXboxController m_secondaryController = 
    new CommandXboxController(OperatorConstants.kSecondaryControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {


    /* ELEVATOR CONTROLS */

    /* PRIMARY */

    // Left Trigger - slow mode

    // Right Trigger - Score coral

    // Rb - Auto-align to right coral spoke

    // Lb - Auto-align to left coral spoke

    // 3 lines - resets gyro


    /* SECONDARY */

    // elevator heights

    // A - Level 4

    // B - Level 3

    // C - Level 2

    // D - Level 1

    
    // RT - intake in

    // B - Ball command


    // initialize objects beforehand
    MoveElevatorCommand b_Command = new MoveElevatorCommand(m_elevator, 50);
    KillElevatorCommand a_Command = new KillElevatorCommand(m_elevator);
    BallCommand lb_Command = new BallCommand(m_intake);
    AprilTagAlignCommand rb_Command = new AprilTagAlignCommand(/*drivetrain,*/ limelight);

    // B Button - move to preset and hold, return to origin when released
    m_primaryController.b()
    .whileTrue(b_Command);

    // Y Button - manual raise
    m_primaryController.y()
        .whileTrue(MoveElevatorManualCommand.moveElevator(m_elevator, 0.01));

    // A Button - ohcrap button
    m_primaryController.a()
        .onTrue(a_Command); 

    // X Button - down button
    m_primaryController.x()
    .whileTrue(MoveElevatorManualCommand.moveElevator(m_elevator, -0.01));

    /* PIVOT CONTROLS */
    
    // Left Trigger - manual pivot raise
    m_primaryController.leftTrigger()
    .whileTrue(MovePivotManualCommand.movePivot(m_intake, .05));

    // Right Trigger - manual pivot lower
    m_primaryController.rightTrigger()
    .whileTrue(MovePivotManualCommand.movePivot(m_intake, -.05));

    // Left Bumper - Moves pivot arm to BALL position while held, returns to HOME when released
    m_primaryController.leftBumper()
    .whileTrue(lb_Command);


    /* Autoalign?!?!? */
      new Trigger(limelight::hasTarget)
          .and(m_primaryController.rightBumper())
          .whileTrue(rb_Command);

  }  
  
  
}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
    */

