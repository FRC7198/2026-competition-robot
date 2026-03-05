package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HOPPER_CONSTANTS;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel;

public class HopperSubsystem extends SubsystemBase implements AutoCloseable {
    SparkMax hopperExtender; 
    public HopperSubsystem(){
        hopperExtender = new SparkMax(HOPPER_CONSTANTS.HOPPER_MOTOR, MotorType.kBrushless);

    }
    public void extend(){
    hopperExtender.set(HOPPER_CONSTANTS.HOPPER_MOTOR_SPEED);
    }
    public void retract(){
    hopperExtender.set(-HOPPER_CONSTANTS.HOPPER_MOTOR_SPEED);
    }
    public void Stop(){
    hopperExtender.stopMotor();
    }
    @Override
    public void close() throws Exception {
    hopperExtender.close();
    }
}
