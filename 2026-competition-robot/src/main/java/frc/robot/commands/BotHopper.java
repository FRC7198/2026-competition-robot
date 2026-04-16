package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.HopperSubsystem;
public class BotHopper extends SequentialCommandGroup{
public BotHopper(HopperSubsystem hopperSubsystem) {
addCommands(
               
               Commands.waitSeconds(1.0),
                 hopperSubsystem.retract(),
                Commands.waitSeconds(2.0),
                hopperSubsystem.extend(),
                Commands.waitSeconds(2.0),
                hopperSubsystem.retract(),
                Commands.waitSeconds(2.0),
                hopperSubsystem.extend(),
                Commands.waitSeconds(2.0),
                hopperSubsystem.Stop());

}


    
}
