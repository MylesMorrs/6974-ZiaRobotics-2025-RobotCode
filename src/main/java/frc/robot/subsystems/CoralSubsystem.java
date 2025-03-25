package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;  // Correct import for CANSparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType;  // Import MotorType for setting brushless type

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

public class CoralSubsystem extends SubsystemBase {
    private final SparkMax CoralElevatorMotor;
    private final SparkMax CoralIntakeArticulatorMotor;
    private final SparkMax CoralIntakeMotor;
    private final SparkMax CoralIntakeMotor2;
    private final RelativeEncoder m_elevatorEncoder;
    
    private final double inchesPerRotation = 12; // 12 inches per motor rotation
    private final int encoderTicksPerRotation = 360; // Adjust based on your encoder resolution
    private double targetPositionTicks = 0; // Target position in encoder ticks
    private boolean moving = false; // To track if we are still moving the elevator

    

    public CoralSubsystem(int CoralElevatorCanId, int CoralIntakeCanId, int CoralIntakeArticulatorCanId, int CoralIntake2CanId){
        CoralElevatorMotor = new SparkMax(CoralElevatorCanId, MotorType.kBrushed);
        CoralIntakeArticulatorMotor = new SparkMax(CoralIntakeArticulatorCanId, MotorType.kBrushless);
        CoralIntakeMotor = new SparkMax(CoralIntakeCanId, MotorType.kBrushed);
        CoralIntakeMotor2 = new SparkMax(CoralIntake2CanId, MotorType.kBrushed);

        m_elevatorEncoder = CoralElevatorMotor.getEncoder();
        
    }
    public void ResetElevator(){

    }

    public void ElevatorPreset(double inchesToMove){
        // Calculate the number of encoder ticks required to move the desired distance
        double rotationsRequired = inchesToMove / inchesPerRotation;
        targetPositionTicks = rotationsRequired * encoderTicksPerRotation;
        double currentPosition = m_elevatorEncoder.getPosition();
        String message = "Current Position is" + currentPosition;
         DriverStation.reportWarning(message, false);
        
        // Reset the encoder so we start from the current position
        if (currentPosition < -rotationsRequired - 0.1) {
            CoralElevatorMotor.set(0.3); // Set motor speed to 50% (adjust as needed)
        } else if(currentPosition > -rotationsRequired + 0.1) {
            CoralElevatorMotor.set(-0.3);
        }
        else {
            CoralElevatorMotor.set(-0.1); // Stop the motor when the target is reached
        } 
    }


    //Sets Speed of Coral Elevator
    public void setCoralElevatorMotorSpeed(double speed) {
        speed = Math.max(-0.3, Math.min(0.3, -speed));
        CoralElevatorMotor.set(-speed);
    }
    public static Command coralIntakeSpeedCommand(speed){ 
        CoralIntakeMotor.set(speed); CoralIntakeMotor2.set(speed); }

    public void setCoralIntakeArticulatorMotorSpeed(double speed) {
        speed = Math.max(-0.2, Math.min(0.2, -speed));
        CoralIntakeArticulatorMotor.set(speed);
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
