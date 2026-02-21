package frc.yagsl.parser.json;

import static frc.yagsl.telemetry.SwerveDriveTelemetry.canIdWarning;
import static frc.yagsl.telemetry.SwerveDriveTelemetry.i2cLockupWarning;
import static frc.yagsl.telemetry.SwerveDriveTelemetry.serialCommsIssueWarning;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import frc.yagsl.encoders.AnalogAbsoluteEncoderSwerve;
import frc.yagsl.encoders.CanAndMagSwerve;
import frc.yagsl.encoders.DIODutyCycleEncoderSwerve;
import frc.yagsl.encoders.SparkFlexEncoderSwerve;
import frc.yagsl.encoders.SparkMaxAnalogEncoderSwerve;
import frc.yagsl.encoders.SparkMaxEncoderSwerve;
import frc.yagsl.encoders.SwerveAbsoluteEncoder;
import frc.yagsl.imu.ADIS16448Swerve;
import frc.yagsl.imu.ADIS16470Swerve;
import frc.yagsl.imu.ADXRS450Swerve;
import frc.yagsl.imu.AnalogGyroSwerve;
import frc.yagsl.imu.CanandgyroSwerve;
import frc.yagsl.imu.SwerveIMU;
import frc.yagsl.motors.SparkFlexSwerve;
import frc.yagsl.motors.SparkMaxBrushedMotorSwerve;
import frc.yagsl.motors.SparkMaxBrushedMotorSwerve.Type;
import frc.yagsl.motors.SparkMaxSwerve;
import frc.yagsl.motors.SwerveMotor;
import frc.yagsl.motors.ThriftyNovaSwerve;
import frc.yagsl.parser.deserializer.ReflectionsManager;
import frc.yagsl.parser.deserializer.ReflectionsManager.VENDOR;

/**
 * Device JSON parsed class. Used to access the JSON data.
 */
public class DeviceJson
{

  /**
   * The device type, e.g. pigeon/pigeon2/sparkmax/talonfx/navx
   */
  public String type;
  /**
   * The CAN ID or pin ID of the device.
   */
  public int    id;
  /**
   * The CAN bus name which the device resides on if using CAN.
   */
  public String canbus = "";

  /**
   * Create a {@link SwerveAbsoluteEncoder} from the current configuration.
   *
   * @param motor {@link SwerveMotor} of which attached encoders will be created from, only used when the type is
   *              "attached" or "canandencoder".
   * @return {@link SwerveAbsoluteEncoder} given.
   */
  public SwerveAbsoluteEncoder createEncoder(SwerveMotor motor)
  {
    if (id > 40)
    {
      canIdWarning.set(true);
    }
    switch (type)
    {
      case "none":
        return null;
      case "integrated":
      case "attached":
      case "canandmag":
      case "canandcoder":
        return new SparkMaxEncoderSwerve(motor, 360);
      case "sparkmax_analog":
        return new SparkMaxAnalogEncoderSwerve(motor, 3.3);
      case "sparkmax_analog5v":
        return new SparkMaxAnalogEncoderSwerve(motor, 5);
      case "sparkflex_integrated":
      case "sparkflex_attached":
      case "sparkflex_canandmag":
      case "sparkflex_canandcoder":
        return new SparkFlexEncoderSwerve(motor, 360);
      case "talonfxs_analog":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.PHOENIX6,
                                                                "frc.yagsl.encoders.TalonFXSEncoderAnalogSwerve",
                                                                new Class[]{SwerveMotor.class},
                                                                new Object[]{motor});
//        return new TalonFXSEncoderAnalogSwerve(motor);
      case "canandcoder_can":
      case "canandmag_can":
        return new CanAndMagSwerve(id);
      case "ctre_mag":
      case "rev_hex":
      case "throughbore":
      case "am_mag":
      case "dutycycle":
        return new DIODutyCycleEncoderSwerve(id);
      case "thrifty":
      case "ma3":
      case "analog":
        return new AnalogAbsoluteEncoderSwerve(id);
      case "cancoder":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.PHOENIX6,
                                                                "frc.yagsl.encoders.CANCoderSwerve",
                                                                new Class[]{int.class, String.class},
                                                                new Object[]{id, canbus != null ? canbus : ""});
//        return new CANCoderSwerve(id, canbus != null ? canbus : "");
      case "srxmag_standalone":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.PHOENIX6,
                                                                "frc.yagsl.encoders.TalonSRXEncoderSwerve",
                                                                new Class[]{SwerveMotor.class, String.class},
                                                                new Object[]{
                                                                    ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX5,
                                                                                                           "frc.yagsl.motors.TalonSRXSwerve",
                                                                                                           new Class[]{
                                                                                                               int.class,
                                                                                                               boolean.class,
                                                                                                               DCMotor.class},
                                                                                                           new Object[]{
                                                                                                               id,
                                                                                                               false,
                                                                                                               DCMotor.getCIM(
                                                                                                                   1)}),
                                                                    "PulseWidthEncodedPosition"});
//        return new TalonSRXEncoderSwerve(new TalonSRXSwerve(id, false, DCMotor.getCIM(1)),
//                                         FeedbackDevice.PulseWidthEncodedPosition);
      case "talonsrx_pwm":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.PHOENIX5,
                                                                "frc.yagsl.encoders.TalonSRXEncoderSwerve",
                                                                new Class[]{SwerveMotor.class, String.class},
                                                                new Object[]{motor, "PulseWidthEncodedPosition"});
//        return new TalonSRXEncoderSwerve(motor, FeedbackDevice.PulseWidthEncodedPosition);
      case "talonsrx_analog":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.PHOENIX5,
                                                                "frc.yagsl.encoders.TalonSRXEncoderSwerve",
                                                                new Class[]{SwerveMotor.class, String.class},
                                                                new Object[]{motor, "Analog"});
//        return new TalonSRXEncoderSwerve(motor, FeedbackDevice.Analog);
      case "thrifty_nova_rev":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.THRIFTYBOT,
                                                                "frc.yagsl.encoders.ThriftyNovaEncoderSwerve",
                                                                new Class[]{SwerveMotor.class, String.class},
                                                                new Object[]{motor, "REV_ENCODER"});
      case "thrifty_nova_redux":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.THRIFTYBOT,
                                                                "frc.yagsl.encoders.ThriftyNovaEncoderSwerve",
                                                                new Class[]{SwerveMotor.class, String.class},
                                                                new Object[]{motor, "REDUX_ENCODER"});
      case "thrifty_nova_srx_mag":
        return ReflectionsManager.<SwerveAbsoluteEncoder>create(VENDOR.THRIFTYBOT,
                                                                "frc.yagsl.encoders.ThriftyNovaEncoderSwerve",
                                                                new Class[]{SwerveMotor.class, String.class},
                                                                new Object[]{motor, "SRX_MAG_ENCODER"});
      default:
        throw new RuntimeException(type + " is not a recognized absolute encoder type.");
    }
  }

  /**
   * Create a {@link SwerveIMU} from the given configuration.
   *
   * @return {@link SwerveIMU} given.
   */
  public SwerveIMU createIMU()
  {
    if (id > 40)
    {
      canIdWarning.set(true);
    }
    switch (type)
    {
      case "adis16448":
        return new ADIS16448Swerve();
      case "adis16470":
        return new ADIS16470Swerve();
      case "adxrs450":
        return new ADXRS450Swerve();
      case "analog":
        return new AnalogGyroSwerve(id);
      case "canandgyro":
        return new CanandgyroSwerve(id);
      case "navx":
      case "navx_spi":
        return ReflectionsManager.<SwerveIMU>create(VENDOR.STUDICA,
                                                    "frc.yagsl.imu.NavXSwerve",
                                                    new Class[]{String.class},
                                                    new Object[]{"kMXP_SPI"});
      case "navx3":
        return ReflectionsManager.<SwerveIMU>create(VENDOR.STUDICA2,
                                                    "frc.yagsl.imu.NavX3Swerve",
                                                    new Class[]{Integer.class},
                                                    new Object[]{id});
      case "navx_i2c":
        DriverStation.reportWarning(
            "WARNING: There exists an I2C lockup issue on the roboRIO that could occur, more information here: " +
            "\nhttps://docs.wpilib.org/en/stable/docs/yearly-overview/known-issues" +
            ".html#onboard-i2c-causing-system-lockups",
            false);
        i2cLockupWarning.set(true);
        return ReflectionsManager.<SwerveIMU>create(VENDOR.STUDICA,
                                                    "frc.yagsl.imu.NavXSwerve",
                                                    new Class[]{String.class},
                                                    new Object[]{"kI2C"});
      case "navx_usb":
        DriverStation.reportWarning("WARNING: There is issues when using USB camera's and the NavX like this!\n" +
                                    "https://pdocs.kauailabs.com/navx-mxp/guidance/selecting-an-interface/", false);
        serialCommsIssueWarning.set(true);
        return ReflectionsManager.<SwerveIMU>create(VENDOR.STUDICA,
                                                    "frc.yagsl.imu.NavXSwerve",
                                                    new Class[]{String.class},
                                                    new Object[]{"kUSB1"});
      case "navx_mxp_serial":
        serialCommsIssueWarning.set(true);
        return ReflectionsManager.<SwerveIMU>create(VENDOR.STUDICA,
                                                    "frc.yagsl.imu.NavXSwerve",
                                                    new Class[]{String.class},
                                                    new Object[]{"kMXP_UART"});
      case "pigeon":
        ReflectionsManager.<SwerveIMU>create(VENDOR.PHOENIX5,
                                             "frc.yagsl.imu.PigeonSwerve",
                                             new Class[]{int.class},
                                             new Object[]{id});
      case "pigeon_via_talonsrx":
        return ReflectionsManager.<SwerveIMU>create(VENDOR.PHOENIX5,
                                                    "frc.yagsl.imu.PigeonViaTalonSRXSwerve",
                                                    new Class[]{int.class},
                                                    new Object[]{id});
      case "pigeon2":
        return ReflectionsManager.<SwerveIMU>create(VENDOR.PHOENIX6,
                                                    "frc.yagsl.imu.Pigeon2Swerve",
                                                    new Class[]{int.class, String.class},
                                                    new Object[]{id, canbus != null ? canbus : ""});
      default:
        throw new RuntimeException(type + " is not a recognized imu/gyroscope type.");
    }
  }

  /**
   * Create a {@link SwerveMotor} from the given configuration.
   *
   * @param isDriveMotor If the motor being generated is a drive motor.
   * @return {@link SwerveMotor} given.
   */
  public SwerveMotor createMotor(boolean isDriveMotor)
  {
    if (id > 40)
    {
      canIdWarning.set(true);
    }
    switch (type)
    {
      case "talonfxs_neo":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getNEO(1)});
      case "talonfxs_neo550":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getNeo550(1)});
      case "talonfxs_vortex":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getNeoVortex(1)});
      case "talonfxs_minion":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getMinion(1)});
      case "talonfxs_pulsar":
        throw new UnsupportedOperationException("Cannot create pulsar combination");
      case "sparkmax_neo":
      case "neo":
      case "sparkmax":
        return new SparkMaxSwerve(id, isDriveMotor, DCMotor.getNEO(1));
      case "sparkmax_vortex":
        return new SparkMaxSwerve(id, isDriveMotor, DCMotor.getNeoVortex(1));
      case "sparkmax_minion":
        return new SparkMaxSwerve(id, isDriveMotor, DCMotor.getMinion(1));
      case "sparkmax_neo550":
      case "neo550":
        return new SparkMaxSwerve(id, isDriveMotor, DCMotor.getNeo550(1));
      case "sparkmax_pulsar":
        throw new UnsupportedOperationException("Cannot create pulsar combination");
      case "sparkflex_vortex":
      case "sparkflex":
        return new SparkFlexSwerve(id, isDriveMotor, DCMotor.getNeoVortex(1));
      case "sparkflex_neo":
        return new SparkFlexSwerve(id, isDriveMotor, DCMotor.getNEO(1));
      case "sparkflex_neo550":
        return new SparkFlexSwerve(id, isDriveMotor, DCMotor.getNeo550(1));
      case "sparkflex_minion":
        return new SparkFlexSwerve(id, isDriveMotor, DCMotor.getMinion(1));
      case "sparkflex_pulsar":
        throw new UnsupportedOperationException("Cannot create pulsar combination");
      case "falcon500":
      case "falcon":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getFalcon500(1)});
//        return new TalonFXSwerve(id, canbus != null ? canbus : "", isDriveMotor, DCMotor.getFalcon500(1));
      case "falcon500foc":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getFalcon500Foc(1)});
//        return new TalonFXSwerve(id, canbus != null ? canbus : "", isDriveMotor, DCMotor.getFalcon500Foc(1));
      case "krakenx44":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getKrakenX44(1)});
//        return new TalonFXSwerve(id, canbus != null ? canbus : "", isDriveMotor, DCMotor.getKrakenX44(1));
      case "krakenx60":
      case "talonfx":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getKrakenX60(1)});
//        return new TalonFXSwerve(id, canbus != null ? canbus : "", isDriveMotor, DCMotor.getKrakenX60(1));
      case "krakenx60foc":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX6,
                                                      "frc.yagsl.motors.TalonFXSwerve",
                                                      new Class[]{int.class, String.class, boolean.class,
                                                                  DCMotor.class},
                                                      new Object[]{id, canbus != null ? canbus : "", isDriveMotor,
                                                                   DCMotor.getKrakenX60Foc(1)});
//        return new TalonFXSwerve(id, canbus != null ? canbus : "", isDriveMotor, DCMotor.getKrakenX60Foc(1));
      case "talonsrx":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.PHOENIX5,
                                                      "frc.yagsl.motors.TalonSRXSwerve",
                                                      new Class[]{int.class, boolean.class, DCMotor.class},
                                                      new Object[]{id, isDriveMotor, DCMotor.getCIM(1)});
//        return new TalonSRXSwerve(id, isDriveMotor, DCMotor.getCIM(1));
      case "sparkmax_brushed":
        if (canbus == null)
        {
          canbus = "";
        }
        switch (canbus)
        {
          case "greyhill_63r256":
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kQuadrature, 1024, false, DCMotor.getCIM(1));
          case "srx_mag_encoder":
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kQuadrature, 4096, false, DCMotor.getCIM(1));
          case "throughbore":
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kQuadrature, 8192, false, DCMotor.getCIM(1));
          case "throughbore_dataport":
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kNoSensor, 8192, true, DCMotor.getCIM(1));
          case "greyhill_63r256_dataport":
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kQuadrature, 1024, true, DCMotor.getCIM(1));
          case "srx_mag_encoder_dataport":
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kQuadrature, 4096, true, DCMotor.getCIM(1));
          default:
            if (isDriveMotor)
            {
              throw new RuntimeException(
                  "Spark MAX " + id + " MUST have a encoder attached to the motor controller.");
            }
            // We are creating a motor for an angle motor which will use the absolute encoder attached to the data port.
            return new SparkMaxBrushedMotorSwerve(id, isDriveMotor, Type.kNoSensor, 0, false, DCMotor.getCIM(1));
        }
      case "nova_neo":
        return ReflectionsManager.<SwerveMotor>create(VENDOR.THRIFTYBOT,
                                                      "frc.yagsl.motors.ThriftyNovaSwerve",
                                                      new Class[]{int.class, boolean.class, DCMotor.class},
                                                      new Object[]{id, isDriveMotor, DCMotor.getNEO(1)});

      case "nova_neo550":

        return ReflectionsManager.<SwerveMotor>create(VENDOR.THRIFTYBOT,
                                                      "frc.yagsl.motors.ThriftyNovaSwerve",
                                                      new Class[]{int.class, boolean.class, DCMotor.class},
                                                      new Object[]{id, isDriveMotor, DCMotor.getNeo550(1)});

      case "nova_vortex":

        return ReflectionsManager.<SwerveMotor>create(VENDOR.THRIFTYBOT,
                                                      "frc.yagsl.motors.ThriftyNovaSwerve",
                                                      new Class[]{int.class, boolean.class, DCMotor.class},
                                                      new Object[]{id, isDriveMotor, DCMotor.getNeoVortex(1)});

      case "nova_minion":
        return new ThriftyNovaSwerve(id, isDriveMotor, DCMotor.getMinion(1));
      case "nova_pulsar":
        throw new UnsupportedOperationException("Cannot create pulsar combination");
      default:
        throw new RuntimeException(type + " is not a recognized motor type.");
    }

  }
}
