package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.BallHandlerConstants;
import frc.robot.subsystems.BallHandler;

public class Shoot extends SequentialCommandGroup {
    public Shoot(BallHandler ballHandlerSubsystem) {
        addCommands(
                Commands.runOnce(() -> ballHandlerSubsystem.launch(BallHandlerConstants.INTAKE_MOTOR_SPEEDGEAR_TWO)),
                Commands.waitSeconds(5),
                Commands.runOnce(() -> ballHandlerSubsystem.stopMotor())
        );

    }

}
