package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;  // Correct import for CANSparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType;  // Import MotorType for setting brushless type
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class CoralSubsystem extends SubsystemBase {
    private static SparkMax CoralElevatorMotor;
    private static SparkMax CoralIntakeArticulatorMotor;
    private static SparkMax CoralIntakeMotor;
    private static SparkMax CoralIntakeMotor2;
    private static RelativeEncoder m_elevatorEncoder;
    private static RelativeEncoder m_intakeArticulatorEncoder;
    
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
    public static void CoralAnglePreset(double targetAngle){
        while(true){
        double currentPosition2 = -m_intakeArticulatorEncoder.getPosition()/60;
        double currentAngle = currentPosition2*360;
        if (targetAngle < currentAngle -5){
            CoralIntakeArticulatorMotor.set(0.1);
        } else if (targetAngle > currentAngle +5){
            CoralIntakeArticulatorMotor.set(-0.1);
        }
        else{
            CoralIntakeArticulatorMotor.set(0);
            break;
        } }
        
    }

    public static void ElevatorPreset(double inchesToMove){
        // Calculate the number of encoder ticks required to move the desired distance
        double rotationsRequired = inchesToMove / inchesPerRotation;
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
    }


    //Sets Speed of Coral Elevator
    public void setCoralElevatorMotorSpeed(double speed) {
        speed = Math.max(-0.3, Math.min(0.3, -speed));
        CoralElevatorMotor.set(-speed);
    }

    public static Command coralElevatorHeightCommand() {
  return Commands.runOnce(
      () -> {
        ElevatorPreset(24);
      });
}

    public void setCoralIntakeArticulatorMotorSpeed(double speed) {
        speed = Math.max(-0.2, Math.min(0.2, -speed));
        CoralIntakeArticulatorMotor.set(speed);
    }

    public static Command coralIntakeArticulatorAngleCommand() {
        return Commands.runOnce(
        () -> {
            CoralAnglePreset(90);
        });
    }
    // Method to set the speed of the intake wheel
    public void setCoralIntakeMotorSpeed(double speed) {
        CoralIntakeMotor.set(speed);
        CoralIntakeMotor2.set(speed);
    }


    // Example of stopping both motors
    public void stopMotors() {
        //CoralElevatorMotor.stopMotor();
        CoralIntakeArticulatorMotor.stopMotor();
    }
}
