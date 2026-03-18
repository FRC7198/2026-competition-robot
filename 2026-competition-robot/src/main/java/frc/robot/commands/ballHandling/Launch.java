package frc.robot.commands.ballHandling;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.BallHandlerConstants;
import frc.robot.subsystems.BallHandler;

public class Launch extends Command {

    BallHandler ballHandlerSubsystem;
    Timer spinUpTimer;

    public Launch(BallHandler ballHandlerSubsystem) {
        this.ballHandlerSubsystem = ballHandlerSubsystem;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        ballHandlerSubsystem.spinUpFeedMotor();
        spinUpTimer.start();

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        if (spinUpTimer.hasElapsed(.25)) {
            ballHandlerSubsystem.launch(BallHandlerConstants.INTAKE_MOTOR_SPEEDGEAR_ONE);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        ballHandlerSubsystem.stopMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (spinUpTimer.hasElapsed(1)) {
            return true;
        }
        return false;
    }
}
