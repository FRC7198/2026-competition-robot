package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.BallHandler;

public class Intake extends SequentialCommandGroup{
 public Intake(BallHandler ballHandlerSubsystem){
 addCommands(
    ballHandlerSubsystem.intake(),
    Commands.waitSeconds(2),
    ballHandlerSubsystem.stopMotor()
 );
  
 }
 


}


    

