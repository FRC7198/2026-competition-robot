package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BallHandler;


public class Shoot extends SequentialCommandGroup{
 public Shoot(BallHandler ballHandlerSubsystem){
addCommands(
    ballHandlerSubsystem.launch(),
    Commands.waitSeconds(2),
    ballHandlerSubsystem.stopMotor()


);

 }
 
    
}
