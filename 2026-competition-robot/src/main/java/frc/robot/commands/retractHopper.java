package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.HopperSubsystem;

public class retractHopper extends SequentialCommandGroup {
    public retractHopper(HopperSubsystem hopperSubsystem) {
        addCommands(
                hopperSubsystem.retract(),
                Commands.waitSeconds(2.0),
                hopperSubsystem.Stop());
    }
}