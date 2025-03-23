package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;  // Correct import for CANSparkMax
import com.revrobotics.spark.SparkLowLevel.MotorType;  // Import MotorType for setting brushless type

public class ClimbingSubsystem {

    private SparkMax ClimberMotor;

    public ClimbingSubsystem(int ClimberCanId){

        ClimberMotor = new SparkMax(ClimberCanId, MotorType.kBrushed);
    }

    public void setClimberMotorSpeed(double speed) {
        speed = Math.max(-0.3, Math.min(0.3, -speed));
        ClimberMotor.set(speed);
    }

    // Example of stopping both motors
    public void climberStopMotors() {
        ClimberMotor.stopMotor();
    }

}
