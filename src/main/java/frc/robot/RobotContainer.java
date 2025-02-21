// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Elevator.*;
import frc.robot.commands.Intake.BallCommand;
import frc.robot.commands.Intake.IntakeOuttakeCommand;
import frc.robot.commands.Intake.MovePivotManualCommand;
import frc.robot.commands.Intake.WheelMoveCommand;
import frc.robot.commands.Limelight.AprilTagAlignCommand;
import frc.robot.commands.Limelight.FollowClosestAprilTagCommand;
import frc.robot.commands.Swerve.ResetGyroCommand;
import frc.robot.commands.Swerve.SlowCommand;
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
    /* INITIALIZING ALL OBJECTS FOR COMMANDS */

    // primary
    SlowCommand slowCommand = new SlowCommand();
    IntakeOuttakeCommand intakeOuttakeCommand = new IntakeOuttakeCommand(m_intake);
    AprilTagAlignCommand alignRightCommand = new AprilTagAlignCommand(/*drivetrain,*/ limelight, true);
    AprilTagAlignCommand alignLeftCommand = new AprilTagAlignCommand(/*drivetrain,*/ limelight, false);
    ResetGyroCommand resetGyroCommand = new ResetGyroCommand(/* drivetrain? maybe? */);

    // temporary
    FollowClosestAprilTagCommand closestAprilTagCommand = new FollowClosestAprilTagCommand(limelight);

    // secondary
    MoveElevatorCommand level4Command = new MoveElevatorCommand(m_elevator, 5);
    MoveElevatorCommand level3Command = new MoveElevatorCommand(m_elevator, 10);
    MoveElevatorCommand level2Command = new MoveElevatorCommand(m_elevator, 25);
    MoveElevatorCommand level1Command = new MoveElevatorCommand(m_elevator, 50);
    WheelMoveCommand wheelMoveCommand = new WheelMoveCommand(m_intake, .1);
    BallCommand ballCommand = new BallCommand(m_intake);



  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_primaryController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
    
  private final CommandXboxController m_secondaryController = 
    new CommandXboxController(OperatorConstants.kSecondaryControllerPort);
  
  
    // determines which commands are enabled;
    boolean TESTING_MODE = false;

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
    if(TESTING_MODE) {
    // Y Button - manual raise
    m_primaryController.y()
    .whileTrue(MoveElevatorManualCommand.moveElevator(m_elevator, 0.1));

    // X Button - down button
    m_primaryController.x()
    .whileTrue(MoveElevatorManualCommand.moveElevator(m_elevator, -0.1));

    /* PIVOT CONTROLS */

    // Left Trigger - manual pivot raise
    m_primaryController.leftBumper()
    .whileTrue(MovePivotManualCommand.movePivot(m_intake, .05));

    // Left Trigger - manual pivot raise
    m_primaryController.rightBumper()
    .whileTrue(MovePivotManualCommand.movePivot(m_intake, -.05));

    // Right Trigger - manual pivot lower
    m_primaryController.rightTrigger()
    .whileTrue(MoveElevatorManualCommand.moveElevator(m_elevator, .1));

    // left Trigger - manual pivot lower
    m_primaryController.leftTrigger()
    .whileTrue(MoveElevatorManualCommand.moveElevator(m_elevator, -.1));

    } else {
    /* PRIMARY */

    // Left Trigger - slow mode
    m_primaryController.leftTrigger()
    .whileTrue(slowCommand);

    // Right Trigger - intake / outtake dependant on where the pivot arm is
    m_primaryController.rightTrigger()
    .onTrue(intakeOuttakeCommand);

    // Rb - Auto-align to right coral spoke
    m_primaryController.rightBumper()
    .onTrue(alignRightCommand);

    // Lb - Auto-align to left coral spoke
    m_primaryController.leftBumper()
    .onTrue(alignLeftCommand);

    /*
      new Trigger(limelight::hasTarget)
          .and(m_primaryController.rightBumper())
          .whileTrue(rb_Command);
  }  
     */

    // x - resets gyro
    m_primaryController.x()
    .onTrue(resetGyroCommand);


    /* SECONDARY */

    // elevator heights

    // A - Level 4
    m_secondaryController.a()
    .whileTrue(level4Command);

    // B - Level 3
    m_secondaryController.b()
    .whileTrue(level3Command);

    // X - Level 2
    m_secondaryController.x()
    .whileTrue(level2Command);

    // Y - Level 1
    m_secondaryController.y()
    .whileTrue(level1Command);

  
    // RT - intake in
    m_secondaryController.rightTrigger()
    .whileTrue(wheelMoveCommand);

    // B - Ball command
    m_secondaryController.b()
    .whileTrue(ballCommand);
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

}