package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BallHandlerConstants;
import edu.wpi.first.wpilibj.Timer;

public class BallHandler extends SubsystemBase implements AutoCloseable {

  SparkMax IntakeMotor;
  SparkMax FeedMotor;
  private boolean isLaunching = false;
  private Timer launchTimer = new Timer();
  private double launchSpeed = BallHandlerConstants.INTAKE_MOTOR_SPEEDGEAR_ONE;

  public BallHandler() {

    IntakeMotor = new SparkMax(BallHandlerConstants.INTAKEMOTOR_CONSTANT, MotorType.kBrushless);
    FeedMotor = new SparkMax(BallHandlerConstants.FEEDMOTOR_CONSTANT, MotorType.kBrushless);

  }

  public void spinUpFeedMotor() {
    FeedMotor.set(BallHandlerConstants.FEED_MOTOR_SPEED);
  }

  public void reverseFeedMotor() {
    FeedMotor.set(-BallHandlerConstants.FEED_MOTOR_SPEED);
  }


  public void stopMotor() {
          isLaunching = false;
          launchTimer.reset();
          IntakeMotor.stopMotor();
          FeedMotor.stopMotor();
  }

  public Command intake() {
    return runOnce(
        () -> {
          IntakeMotor.set(BallHandlerConstants.INTAKE_MOTOR_SPEEDGEAR_ONE);
          FeedMotor.set(BallHandlerConstants.FEED_MOTOR_SPEED);
        });
  }

  public void launch(double speed) {
    launchSpeed = speed;
    if (!isLaunching) {
      launchTimer.start();
      isLaunching = true;
    }
  }

  @Override
  public void close() throws Exception {
    IntakeMotor.close();
    FeedMotor.close();
  }

  @Override
  public void periodic() {
    if (isLaunching) {
      if (launchTimer.hasElapsed(1.5)) {
        FeedMotor.set(-BallHandlerConstants.FEED_MOTOR_SPEED);
      }
      IntakeMotor.set(launchSpeed);
    }
  }
}
