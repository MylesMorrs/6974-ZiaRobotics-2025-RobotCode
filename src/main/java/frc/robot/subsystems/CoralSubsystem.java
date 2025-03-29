package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;  // Correct import for CANSparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType;  // Import MotorType for setting brushless type

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase
import com.revrobotics.RelativeEncoder;

public class CoralSubsystem extends SubsystemBase {
    private static SparkMax CoralElevatorMotor;
    private static SparkMax CoralIntakeArticulatorMotor;
    private static SparkMax CoralIntakeMotor;
    private static SparkMax CoralIntakeMotor2;
    private static RelativeEncoder m_elevatorEncoder;
    private static RelativeEncoder m_intakeArticulatorEncoder;

    private static DigitalInput CoralIntakeSensor = new DigitalInput(0);
    private static DigitalInput CoralArticulatorLimitSwitch = new DigitalInput(1);
    
    private static double inchesPerRotation = 12; // 12 inches per motor rotation

    

    public CoralSubsystem(int CoralElevatorCanId, int CoralIntakeCanId, int CoralIntakeArticulatorCanId, int CoralIntake2CanId){
        CoralElevatorMotor = new SparkMax(CoralElevatorCanId, MotorType.kBrushed);
        CoralIntakeArticulatorMotor = new SparkMax(CoralIntakeArticulatorCanId, MotorType.kBrushless);
        CoralIntakeMotor = new SparkMax(CoralIntakeCanId, MotorType.kBrushed);
        CoralIntakeMotor2 = new SparkMax(CoralIntake2CanId, MotorType.kBrushed);

        m_elevatorEncoder = CoralElevatorMotor.getEncoder();
        m_intakeArticulatorEncoder = CoralIntakeArticulatorMotor.getEncoder();

    
        
    }
    public void ResetElevator(){

    }
    public static void CoralArticulatorZero(){
        while(!CoralArticulatorLimitSwitch.get()){
            CoralIntakeArticulatorMotor.set(-0.1);
        }
        m_intakeArticulatorEncoder.setPosition(15);
    }
    public static void CoralIntakeSensing(){
        while(CoralIntakeSensor.get()){
            CoralIntakeMotor.set(-1);
            CoralIntakeMotor2.set(1);
        } 
        CoralIntakeMotor.set(0);
        CoralIntakeMotor2.set(0);
    }
    public static void CoralAnglePreset(double targetAngle){
        double currentPosition2 = -m_intakeArticulatorEncoder.getPosition()/60;
        double currentAngle = currentPosition2*360;
        if (targetAngle < currentAngle -5){
            CoralIntakeArticulatorMotor.set(0.1);
        } else if (targetAngle > currentAngle +5){
            CoralIntakeArticulatorMotor.set(-0.1);
        }
        else{
            CoralIntakeArticulatorMotor.set(0);
        } 
        
    }

    public static void ElevatorPreset(double inchesToMove){
        // Calculate the number of encoder ticks required to move the desired distance
        double rotationsRequired = inchesToMove / inchesPerRotation;
        double currentPosition = m_elevatorEncoder.getPosition();
        // Reset the encoder so we start from the current position
        if (currentPosition < -rotationsRequired - 0.1) {
            CoralElevatorMotor.set(0.3); // Set motor speed to 50% (adjust as needed)
        } else if(currentPosition > -rotationsRequired + 0.1) {
            CoralElevatorMotor.set(-0.3);
        }
        else {
            CoralElevatorMotor.set(-0.1);// Stop the motor when the target is reached
        }
    }


    //Sets Speed of Coral Elevator
    public void setCoralElevatorMotorSpeed(double speed) {
        speed = Math.max(-0.3, Math.min(0.3, -speed));
        CoralElevatorMotor.set(-speed);
    }

    public static Command coralElevatorHeightL2Command() {
  return Commands.runOnce(
      () -> {
        // Calculate the number of encoder ticks required to move the desired distance
        double rotationsRequired = 14 / inchesPerRotation;
        while(true){
        double currentPosition = m_elevatorEncoder.getPosition();
        // Reset the encoder so we start from the current position
        if (currentPosition < -rotationsRequired - 0.1) {
            CoralElevatorMotor.set(0.3); // Set motor speed to 50% (adjust as needed)
        } else if(currentPosition > -rotationsRequired + 0.1) {
            CoralElevatorMotor.set(-0.3);
        }
        else {
            CoralElevatorMotor.set(-0.1);
            break; // Stop the motor when the target is reached
        } } } );}

    public static Command coralElevatorHeightCommand() {
  return Commands.runOnce(
      () -> {
        // Calculate the number of encoder ticks required to move the desired distance
        double rotationsRequired = 55 / inchesPerRotation;
        while(true){
        double currentPosition = m_elevatorEncoder.getPosition();
        // Reset the encoder so we start from the current position
        if (currentPosition < -rotationsRequired - 0.1) {
            CoralElevatorMotor.set(0.3); // Set motor speed to 50% (adjust as needed)
        } else if(currentPosition > -rotationsRequired + 0.1) {
            CoralElevatorMotor.set(-0.3);
        }
        else {
            CoralElevatorMotor.set(-0.1);
            break; // Stop the motor when the target is reached
        } }
      });
}

    public void setCoralIntakeArticulatorMotorSpeed(double speed) {
        speed = Math.max(-0.2, Math.min(0.2, -speed));
        CoralIntakeArticulatorMotor.set(speed);
    }

    public static Command coralIntakeArticulatorAngleCommand() {
        return Commands.runOnce(
        () -> {
            while(true){
        double currentPosition2 = -m_intakeArticulatorEncoder.getPosition()/60;
        double currentAngle = currentPosition2*360;
        if (90 < currentAngle -5){
            CoralIntakeArticulatorMotor.set(0.1);
        } else if (90 > currentAngle +5){
            CoralIntakeArticulatorMotor.set(-0.1);
        }
        else{
            CoralIntakeArticulatorMotor.set(0);
            break;
        } }
        });
    }
    // Method to set the speed of the intake wheel
    public void setCoralIntakeMotorSpeed(double speed) {
        CoralIntakeMotor.set(speed);
        CoralIntakeMotor2.set(speed);
    }
    public static Command coralIntakeSensingCommand(){
        return Commands.runOnce(
        () -> {
            for(int i = 0; i < 8000; i++){
            CoralIntakeMotor.set(1);
            CoralIntakeMotor2.set(1);
            }
            CoralIntakeMotor.set(0);
            CoralIntakeMotor2.set(0);
        });
    }


    // Example of stopping both motors
    public void stopMotors() {
        //CoralElevatorMotor.stopMotor();
        CoralIntakeArticulatorMotor.stopMotor();
    }
}
