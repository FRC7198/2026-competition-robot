package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.HopperSubsystem;

public class extendHopper extends SequentialCommandGroup {
    public extendHopper(HopperSubsystem hopperSubsystem) {
        addCommands(
                hopperSubsystem.extend(),
                Commands.waitSeconds(2.0),
                hopperSubsystem.Stop());
    }
}
