package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;  // Correct import for CANSparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType;  // Import MotorType for setting brushless type
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeSubsystem extends SubsystemBase {
    // Motors declaration with the exact names you provided
    private final SparkMax AlgaeIntakeRotationMotor;  // Neo Redline motor
    private final SparkMax AlgaeManipulaterMotor;     // Neo Vortex motor

    // Constructor - initializing motors
    public AlgaeSubsystem(int AlgaeIntakeRotationCanId, int AlgaeManipulaterCanId){
        
        // Assign Neo Redline motor (Algae Intake Rotation) using SparkMax constructor
        AlgaeIntakeRotationMotor = new SparkMax(AlgaeIntakeRotationCanId, MotorType.kBrushed);
        
        // Assign Neo Vortex motor (Algae Manipulater) using SparkMax constructor
        AlgaeManipulaterMotor = new SparkMax(AlgaeManipulaterCanId, MotorType.kBrushless);

    }

    // Method to set the speed of the Neo Redline motor (Algae Intake Rotation)
    public void setAlgaeIntakeRotationMotorSpeed(double speed) {
        speed = Math.max(-0.6, Math.min(0.6, -speed));
        AlgaeIntakeRotationMotor.set(speed);
    }

    // Method to set the speed of the Neo Vortex motor (Algae Manipulater)
    public void setAlgaeManipulaterMotorSpeed(double speed) {
        speed = Math.max(-0.6, Math.min(0.6, -speed));
        AlgaeManipulaterMotor.set(speed);
    }

    // Example of stopping both motors
    public void manipulaterStopMotors() {
        AlgaeManipulaterMotor.stopMotor();
    }
    public void rotationStopMotors() {
        AlgaeIntakeRotationMotor.stopMotor();
    }
}
