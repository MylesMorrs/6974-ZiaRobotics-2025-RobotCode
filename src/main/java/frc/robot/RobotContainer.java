// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
/*import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;*/
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.PS4Controller.Button;
/*import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;*/
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.CoralSubsystem;
//import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
//import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import java.util.List;


/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    
    private final SendableChooser<Command> autoChooser;
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  //private final AlgaeSubsystem algaeSubsystem = new AlgaeSubsystem(9, 8);
  private boolean driveToggle = true;

  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  // Manipulator Controller
  //XboxController m_manipulatorController = new XboxController(OIConstants.kManipulatorControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    if(m_driverController.getRawButton(4)){
        driveToggle = !driveToggle;
    }
    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();
    //NamedCommands.registerCommand("Arm Up", CoralSubsystem.coralElevatorHeightCommand());
    NamedCommands.registerCommand("Coral Angle", CoralSubsystem.coralIntakeArticulatorAngleCommand());
    NamedCommands.registerCommand("Coral Intake", CoralSubsystem.coralIntakeSensingCommand());
    //NamedCommands.registerCommand("Arm Up L2", CoralSubsystem.coralElevatorHeightL2Command());
    
    // Another option that allows you to specify the default auto by its name
    // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

    SmartDashboard.putData("Auto Chooser", autoChooser);
    
    //algaeSubsystem.setAlgaeIntakeRotationMotorSpeed(m_manipulatorController.getRightY());
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        //When Y is pressed field centric toggles.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(-m_driverController.getLeftY()*(1-(m_driverController.getRightTriggerAxis()*0.6)), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(-m_driverController.getLeftX()*(1-(m_driverController.getRightTriggerAxis()*0.6)), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX()*(1-(m_driverController.getRightTriggerAxis()*0.6)), OIConstants.kDriveDeadband),
                driveToggle),
            m_robotDrive));
    
  }

  //public AlgaeSubsystem getAlgaeSubsystem() {
   // return algaeSubsystem;
//}

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    if (m_driverController.getPOV() == 270){
        m_robotDrive.zeroHeading();
    }
    if (m_driverController.getRightTriggerAxis() > 0) {
        m_robotDrive.setX();
    }
    
   /*  new JoystickButton(m_driverController, Button.kTriangle.value)
        .whileTrue(new RunCommand(
            () -> algaeSubsystem.setAlgaeManipulaterMotorSpeed(-0.2)
        ));
    new JoystickButton(m_driverController, Button.kTriangle.value)
        .whileFalse(new RunCommand(
            () -> algaeSubsystem.setAlgaeManipulaterMotorSpeed(0)
        ));  */
       
  }
    // A simple auto routine that drives forward a specified distance, and then stops.
  




  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    return new PathPlannerAuto("L3 CoralScoring");
    // Create config for trajectory
    /*TrajectoryConfig config = new TrajectoryConfig(
        AutoConstants.kMaxSpeedMetersPerSecond,
        AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        // Add kinematics to ensure max speed is actually obeyed
        .setKinematics(DriveConstants.kDriveKinematics);

    // An example trajectory to follow. All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(new Translation2d(1, 0)),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(2, 0, new Rotation2d(0)),
        config);

    var thetaController = new ProfiledPIDController(
        AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
        exampleTrajectory,
        m_robotDrive::getPose, // Functional interface to feed supplier
        DriveConstants.kDriveKinematics,

        // Position controllers
        new PIDController(AutoConstants.kPXController, 0, 0),
        new PIDController(AutoConstants.kPYController, 0, 0),
        thetaController,
        m_robotDrive::setModuleStates,
        m_robotDrive);

    // Reset odometry to the starting pose of the trajectory.
    m_robotDrive.resetOdometry(exampleTrajectory.getInitialPose());

    // Run path following command, then stop at the end.
    return Commands.waitSeconds(5)
    .andThen(swerveControllerCommand)
    .andThen(() -> m_robotDrive.drive(0, 0, 0, false));*/
  }
}
