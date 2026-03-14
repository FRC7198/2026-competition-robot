package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HOPPER_CONSTANTS;

import com.revrobotics.spark.SparkMax;

public class HopperSubsystem extends SubsystemBase implements AutoCloseable {
    SparkMax hopperExtender;

    public HopperSubsystem() {
        hopperExtender = new SparkMax(HOPPER_CONSTANTS.HOPPER_MOTOR, MotorType.kBrushless);

    }

    public Command extend() {
        return runOnce(
                () -> {
                    hopperExtender.set(HOPPER_CONSTANTS.HOPPER_MOTOR_SPEED);
                });
    }

    public Command retract() {
        return runOnce(
                () -> {
                    hopperExtender.set(-HOPPER_CONSTANTS.HOPPER_MOTOR_SPEED);
                });
    }

    public Command Stop() {
        return runOnce(
                () -> {
                });

    }

    @Override
    public void close() throws Exception {
        hopperExtender.close();
    }
}
