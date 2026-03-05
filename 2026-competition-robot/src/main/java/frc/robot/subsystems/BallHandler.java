package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.BallHandlerConstants;

public class BallHandler extends SubsystemBase implements AutoCloseable{}
      SparkMax IntakeMotor;
    SparkMax FeedMotor;
    public BallHandler (){

        IntakeMotor = new SparkMax(BallHandlerConstants.INTAKEMOTOR_CONSTANT, MotorType.kBrushless);
        FeedMotor = new SparkMax(BallHandlerConstants.FEEDMOTOR_CONSTANT, MotorType.kBrushless);
         
        public Void stopMotor(){


        }
        
        public Void intake(){
          IntakeMotor.set(BallHandlerConstants.INTAKE_MOTOR_SPEED);
          FeedMotor.set(BallHandlerConstants.FEED_MOTOR_SPEED);


        }
         public Void launch(){
          IntakeMotor.set(BallHandlerConstants.INTAKE_MOTOR_SPEED);
          FeedMotor.set(-BallHandlerConstants.FEED_MOTOR_SPEED);


         }


        @Override
        public void close() throws Exception {
        IntakeMotor.close();
        FeedMotor.close();
        }
    



        
    
 }


  



    




