package frc.yagsl.parser.json;

import frc.yagsl.parser.PIDFConfig;

/**
 * {@link frc.yagsl.SwerveModule} PID with Feedforward for the drive motor and angle motor.
 */
public class PIDFPropertiesJson
{

  /**
   * The PIDF with Integral Zone used for the drive motor.
   */
  public PIDFConfig drive;
  /**
   * The PIDF with Integral Zone used for the angle motor.
   */
  public PIDFConfig angle;
}
