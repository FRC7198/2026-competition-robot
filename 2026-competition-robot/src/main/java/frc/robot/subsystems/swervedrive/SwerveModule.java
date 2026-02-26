package frc.robot.subsystems.swervedrive;


import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.logging.Level;
import java.util.logging.Logger;


public class SwerveModule implements AutoCloseable {

    Logger logger = Logger.getLogger(SwerveModule.class.getName());
    private SparkMax angleMotor;
    private AbsoluteEncoder angleEncoder;
    private SparkMax driveMotor;
    private RelativeEncoder driveEncoder;

    private final DoublePublisher anglePublisher;
    private final DoublePublisher drivePublisher;
    private final DoublePublisher  driveVelocityPublisher;

    public static enum ModulePositionEnum { LEFT_FRONT, LEFT_BACK, RIGHT_FRONT, RIGHT_BACK, UNDEFINED }
    private ModulePositionEnum position = ModulePositionEnum.UNDEFINED;

    public ModulePositionEnum getPosition() { return position; }

    public SwerveModule(ModulePositionEnum position, int angleEncoderID, int driveEncoderID) {
        angleMotor = new SparkMax(angleEncoderID, MotorType.kBrushless);
        angleEncoder = angleMotor.getAbsoluteEncoder();
        driveMotor = new SparkMax(driveEncoderID, MotorType.kBrushless);
        driveEncoder = driveMotor.getEncoder();
        this.position = position;
        clearStickyFaults();


        anglePublisher = NetworkTableInstance.getDefault().getTable("SmartDashboard").getDoubleTopic("swerve/modules/" + position.toString() + "/angle").publish();
        drivePublisher = NetworkTableInstance.getDefault().getTable("SmartDashboard").getDoubleTopic("swerve/modules/" + position.toString() + "/drive").publish();
        driveVelocityPublisher = NetworkTableInstance.getDefault().getTable("SmartDashboard").getDoubleTopic("swerve/modules/" + position.toString() + "/driveVelocity").publish();}

    public void updateTelemetry() {
        anglePublisher.set(angleEncoder.getPosition());
        drivePublisher.set(driveEncoder.getPosition());
        driveVelocityPublisher.set(driveEncoder.getVelocity());
    }


    @Override
    public void close() throws Exception {
        angleMotor.close();
        driveMotor.close();
    }

    public void clearStickyFaults()
    {
        angleMotor.clearFaults();
        driveMotor.clearFaults();
    }

    public void setAngle(double angle) {
        angleMotor.set(angle);
    }

    public void setDrive(double drive) {
        driveMotor.set(drive);
    }

    public void stopDrive() {
        driveMotor.set(0);
    }

    public void stopAngle() {
        angleMotor.set(0);
    }

    public void stop() {
        stopDrive();
        stopAngle();
    }   
} 