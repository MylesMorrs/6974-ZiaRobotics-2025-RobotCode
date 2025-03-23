package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;  // Correct import for CANSparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType;  // Import MotorType for setting brushless type
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.RelativeEncoder;

public class CoralSubsystem extends SubsystemBase {
    private final SparkMax CoralElevatorMotor;
    private final SparkMax CoralIntakeArticulatorMotor;
    private final SparkMax CoralIntakeMotor;
    private final SparkMax CoralIntakeMotor2;
    //private RelativeEncoder encoder;
    
    // Conversion factor (inches per rotation) - Tunable
    //private double inchesPerRotation = 6.0;  // Default 6 inches per rotation
     // Conversion factor (encoder counts per degree) - Tunable
     private double countsPerDegree = 10.0;  // Example: how many encoder counts per degree, tune this value

    public CoralSubsystem(int CoralElevatorCanId, int CoralIntakeCanId, int CoralIntakeArticulatorCanId, int CoralIntake2CanId){
        CoralElevatorMotor = new SparkMax(CoralElevatorCanId, MotorType.kBrushed);
        CoralIntakeArticulatorMotor = new SparkMax(CoralIntakeArticulatorCanId, MotorType.kBrushless);
        CoralIntakeMotor = new SparkMax(CoralIntakeCanId, MotorType.kBrushed);
        CoralIntakeMotor2 = new SparkMax(CoralIntake2CanId, MotorType.kBrushed);

       // RelativeEncoder encoder = CoralElevatorMotor.getEncoder();
       

        //encoder.setPosition(0);
    }

     // Method to move the motor to a specific angle
     public void moveToAngle(double angle) {
        RelativeEncoder intakeArticulatorEncoder = CoralIntakeArticulatorMotor.getEncoder();
        // Calculate the target encoder position (in counts)
        double targetEncoderPosition = angle * countsPerDegree;

        // Get the current encoder position (in counts)
        double currentEncoderPosition = intakeArticulatorEncoder.getPosition();

        // Move the motor to the target position
        moveMotorToPosition(targetEncoderPosition, currentEncoderPosition);
    }

    // Method to move the motor to a target encoder position
    private void moveMotorToPosition(double targetEncoderPosition, double currentEncoderPosition) {
        // Calculate the difference between the target and current positions
        double positionDifference = targetEncoderPosition - currentEncoderPosition;

        // Define a speed (this could be adjusted for smoother control)
        double speed = 0.2;  // You can adjust the speed to be more aggressive or more gentle

        // If the target position is higher than the current, move the motor up
        if (positionDifference > 0) {
            CoralIntakeArticulatorMotor.set(speed);
        }
        // If the target position is lower than the current, move the motor down
        else if (positionDifference < 0) {
            CoralIntakeArticulatorMotor.set(-speed);
        }
        // If the motor is already at the target position, stop the motor
        else {
            CoralIntakeArticulatorMotor.set(0);
        }
    }

    /*public void moveElevatorInches(double inches) {
        // Convert inches to rotations
        double targetRotations = inches / inchesPerRotation;

        // Get the current encoder position (in rotations)
        double currentPositionRotations = encoder.getPosition();

        // Calculate the target position in rotations
        double targetPositionRotations = currentPositionRotations + targetRotations;

        // Logic to move the arm based on the target position
        moveArmToPosition(targetPositionRotations);
    }*/

    // Method to move the arm to the target position (in rotations)
    /*private void moveArmToPosition(double targetRotations) {
        // Get the current encoder position (in rotations)
        double currentPositionRotations = encoder.getPosition();
        double deadzone = 0.5;
        

        // If the target position is higher than the current, move the motor up
        if (targetRotations > deadzone + currentPositionRotations) {
            CoralElevatorMotor.set(-0.3);  // Move the elevator up at half speed (adjust the speed as necessary)
        }
        // If the target position is lower than the current, move the motor down
        else if (targetRotations < currentPositionRotations - deadzone) {
            CoralElevatorMotor.set(0.3);  // Move the elevator down at half speed (adjust the speed as necessary)
        }
        // If the arm is already at the target position, stop the motor
        else {
            CoralElevatorMotor.set(0);  // Stop the motor
        }
    }*/

    //Sets Speed of Coral Elevator
    public void setCoralElevatorMotorSpeed(double speed) {
        speed = Math.max(-0.3, Math.min(0.3, -speed));
        CoralElevatorMotor.set(-speed);
    }

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
