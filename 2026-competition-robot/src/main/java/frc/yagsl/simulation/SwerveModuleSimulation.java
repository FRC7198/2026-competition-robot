package frc.yagsl.simulation;

import static edu.wpi.first.units.Units.Amps;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.yagsl.simulation.ironmaple.simulation.drivesims.SelfControlledSwerveDriveSimulation;
import frc.yagsl.SwerveDrive;
import frc.yagsl.parser.SwerveModulePhysicalCharacteristics;

/**
 * Class that wraps around {@link frc.yagsl.simulation.ironmaple.simulation.drivesims.SwerveModuleSimulation}
 */
public class SwerveModuleSimulation
{

  /**
   * MapleSim module.
   */
  public SelfControlledSwerveDriveSimulation.SelfControlledModuleSimulation mapleSimModule = null;

  /**
   * Configure the maple sim module
   *
   * @param simModule               the {@link frc.yagsl.simulation.ironmaple.simulation.drivesims.SwerveModuleSimulation} object for
   *                                simulation
   * @param physicalCharacteristics Physical characteristics of the swerve drive from the JSON or built.
   */
  public void configureSimModule(frc.yagsl.simulation.ironmaple.simulation.drivesims.SwerveModuleSimulation simModule,
                                 SwerveModulePhysicalCharacteristics physicalCharacteristics)
  {
    this.mapleSimModule = new SelfControlledSwerveDriveSimulation.SelfControlledModuleSimulation(simModule);
    this.mapleSimModule.withCurrentLimits(
        Amps.of(physicalCharacteristics.driveMotorCurrentLimit),
        Amps.of(physicalCharacteristics.angleMotorCurrentLimit));
  }

  /**
   * Update the position and state of the module. Called from {@link frc.yagsl.SwerveModule#setDesiredState} function
   * when simulated.
   *
   * @param desiredState State the swerve module is set to.
   */
  public void updateStateAndPosition(SwerveModuleState desiredState)
  {
    mapleSimModule.runModuleState(desiredState);
  }

  /**
   * Runs a drive motor characterization on the sim module. This is called from
   * {@link frc.yagsl.SwerveDriveTest#runDriveMotorsCharacterizationOnSimModules(SwerveDrive, double, boolean)}  to run
   * sysId during simulation
   *
   * @param desiredFacing the desired facing of the module
   * @param volts         the voltage to run
   */
  public void runDriveMotorCharacterization(Rotation2d desiredFacing, double volts)
  {
    mapleSimModule.runDriveMotorCharacterization(desiredFacing, volts);
  }

  /**
   * Runs a drive motor characterization on the sim module. This method is called from
   * {@link frc.yagsl.SwerveDriveTest#runAngleMotorsCharacterizationOnSimModules(SwerveDrive, double)} to run sysId
   * during simulation
   *
   * @param volts the voltage to run
   */
  public void runAngleMotorCharacterization(double volts)
  {
    mapleSimModule.runSteerMotorCharacterization(volts);
  }

  /**
   * Get the simulated swerve module position.
   *
   * @return {@link SwerveModulePosition} of the simulated module.
   */
  public SwerveModulePosition getPosition()
  {
    return mapleSimModule.getModulePosition();
  }

  /**
   * Get the {@link SwerveModuleState} of the simulated module.
   *
   * @return {@link SwerveModuleState} of the simulated module.
   */
  public SwerveModuleState getState()
  {
    if (mapleSimModule == null)
    {
      return new SwerveModuleState();
    }
    SwerveModuleState state = mapleSimModule.getMeasuredState();
    state.angle = state.angle.minus(Rotation2d.kZero);
    return state;
  }
}
