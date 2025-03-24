// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.ClimbingSubsystem;
import frc.robot.subsystems.CoralSubsystem;
import frc.robot.subsystems.DriveSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  
  private Joystick m_joystick;
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

  private RobotContainer m_robotContainer;
  private final AlgaeSubsystem algaeSubsystem = new AlgaeSubsystem(7, 8);
  private final CoralSubsystem coralSubsystem = new CoralSubsystem(3, 4, 5, 0 );
  private final ClimbingSubsystem climbingSubsystem = new ClimbingSubsystem(6);
  //private final DriveSubsystem m_robotDrive = new DriveSubsystem();

  boolean auto = false;
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    m_joystick = new Joystick(OIConstants.kManipulatorControllerPort);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  public AlgaeSubsystem getAlgaeSubsystem() {
    return algaeSubsystem;
}
  public CoralSubsystem getCoralSubsystem() {
    return coralSubsystem;
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    
     /*  String autoSelected = SmartDashboard.getString("Auto Selector",
      "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
      = new MyAutoCommand(); break; case "Default Auto": default:
      autonomousCommand = new ExampleCommand(); break; }*/
     
    
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  public void ExampleCommand(){

  }
  public void MyAutoCommand(){

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (m_joystick.getRawAxis(Axis.kLeftTrigger.value) > 0 ) {
      // left trigger is pressed outtake
        algaeSubsystem.setAlgaeManipulaterMotorSpeed(0.4);
      }
    else if (m_joystick.getRawButton(5)) {
        // Button 5 is pressed intake
          algaeSubsystem.setAlgaeManipulaterMotorSpeed(-0.4);
        } 
    else {
      algaeSubsystem.setAlgaeManipulaterMotorSpeed(0.05);
    }
    if (m_joystick.getRawAxis(Axis.kRightTrigger.value) > 0 ) {
      // left trigger is pressed outtake
        coralSubsystem.setCoralIntakeMotorSpeed(-1);
      }
    else if (m_joystick.getRawButton(6)) {
        // Button 5 is pressed intake
          coralSubsystem.setCoralIntakeMotorSpeed(1);
        } 
    else {
      coralSubsystem.setCoralIntakeMotorSpeed(0);
    }
    if (m_joystick.getRawButton(4)) {
      // left trigger is pressed outtake
        algaeSubsystem.setAlgaeIntakeRotationMotorSpeed(0.4);
      }
    else if (m_joystick.getRawButton(1)) {
        // Button 5 is pressed intake
          algaeSubsystem.setAlgaeIntakeRotationMotorSpeed(-0.4);
        } 
    else {
      algaeSubsystem.setAlgaeIntakeRotationMotorSpeed(0.1);
    }
    if (m_joystick.getRawButton(3)) {
      //Coral Station
      coralSubsystem.ElevatorPreset(4);
      }
    if (m_joystick.getPOV() == 0) {
      // First Layer on Reef (Not trough)

      }
    if (m_joystick.getPOV() == 90) {
      // Second Layer on Reef
  
      }
    if (m_joystick.getPOV() == 270) {
      // Third Layer on Reef

      }
    coralSubsystem.setCoralElevatorMotorSpeed(m_joystick.getRawAxis(Axis.kLeftY.value)-0.1);
    coralSubsystem.setCoralIntakeArticulatorMotorSpeed(m_joystick.getRawAxis(Axis.kRightY.value));

    if (m_driverController.getRawButton(5)){
      climbingSubsystem.setClimberMotorSpeed(0.3);
    }
    else if (m_driverController.getRawButton(6)){
      climbingSubsystem.setClimberMotorSpeed(-0.3);
    } else{
      climbingSubsystem.setClimberMotorSpeed(0);
    }
    }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
