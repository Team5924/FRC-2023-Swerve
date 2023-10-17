// Copyright (c) 2023 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.first5924.frc2023swerve.subsystems.drive;

import org.first5924.frc2023swerve.constants.DriveConstants;
import org.first5924.frc2023swerve.constants.RobotConstants;

import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.AbsoluteSensorRangeValue;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.util.Units;

public class ModuleIOSparkMax implements ModuleIO {
  private final CANSparkMax driveSparkMax;
  private final CANSparkMax turnSparkMax;

  private final RelativeEncoder driveEncoder;
  private final RelativeEncoder turnEncoder;
  private final CANcoder turnAbsoluteEncoder;

  private final boolean isTurnMotorInverted = true;
  private final double absoluteEncoderOffsetRad;

  public ModuleIOSparkMax(int index) {
    switch (index) {
      case 0:
        driveSparkMax = new CANSparkMax(DriveConstants.kLeftFrontDriveSparkId, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(DriveConstants.kLeftFrontTurnSparkId, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.kLeftFrontCANCoderId);
        absoluteEncoderOffsetRad = DriveConstants.kLeftFrontAbsoluteEncoderOffsetRad;
        break;
      case 1:
        driveSparkMax = new CANSparkMax(DriveConstants.kRightFrontDriveSparkId, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(DriveConstants.kRightFrontTurnSparkId, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.kRightFrontCANCoderId);
        absoluteEncoderOffsetRad = DriveConstants.kRightFrontAbsoluteEncoderOffsetRad;
        break;
      case 2:
        driveSparkMax = new CANSparkMax(DriveConstants.kLeftBackDriveSparkId, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(DriveConstants.kLeftBackTurnSparkId, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.kLeftBackCANCoderId);
        absoluteEncoderOffsetRad = DriveConstants.kLeftBackAbsoluteEncoderOffsetRad;
        break;
      case 3:
        driveSparkMax = new CANSparkMax(DriveConstants.kRightBackDriveSparkId, MotorType.kBrushless);
        turnSparkMax = new CANSparkMax(DriveConstants.kRightBackTurnSparkId, MotorType.kBrushless);
        turnAbsoluteEncoder = new CANcoder(DriveConstants.kRightBackCANCoderId);
        absoluteEncoderOffsetRad = DriveConstants.kRightBackAbsoluteEncoderOffsetRad;
        break;
      default:
        throw new RuntimeException("Invalid module index for ModuleIOSparkMax");
    }

    driveEncoder = driveSparkMax.getEncoder();
    turnEncoder = turnSparkMax.getEncoder();

    MagnetSensorConfigs magnetSensorConfigs = new MagnetSensorConfigs();
    magnetSensorConfigs.AbsoluteSensorRange = AbsoluteSensorRangeValue.Signed_PlusMinusHalf;
    magnetSensorConfigs.MagnetOffset = Units.radiansToRotations(absoluteEncoderOffsetRad);
    turnAbsoluteEncoder.getConfigurator().apply(magnetSensorConfigs);

    turnSparkMax.setInverted(isTurnMotorInverted);

    driveSparkMax.setSmartCurrentLimit(40);
    turnSparkMax.setSmartCurrentLimit(30);
    driveSparkMax.enableVoltageCompensation(RobotConstants.kNominalVoltage);
    turnSparkMax.enableVoltageCompensation(RobotConstants.kNominalVoltage);

    driveEncoder.setPosition(0.0);
    driveEncoder.setMeasurementPeriod(10);
    driveEncoder.setAverageDepth(2);

    turnEncoder.setPosition(0.0);
    turnEncoder.setMeasurementPeriod(10);
    turnEncoder.setAverageDepth(2);

    driveSparkMax.setCANTimeout(0);
    turnSparkMax.setCANTimeout(0);
  }

  public void updateInputs(ModuleIOInputs inputs) {
    inputs.drivePositionRad = Units.rotationsToRadians(driveEncoder.getPosition()) * DriveConstants.kEncoderToDriveReduction;
    inputs.driveVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(driveEncoder.getVelocity()) * DriveConstants.kEncoderToDriveReduction;
    inputs.driveCurrentAmps = driveSparkMax.getOutputCurrent();
    inputs.driveTempCelcius = driveSparkMax.getMotorTemperature();

    inputs.turnAbsolutePositionRad = Units.rotationsToRadians(turnAbsoluteEncoder.getAbsolutePosition().getValue());
    inputs.turnPositionRad = Units.rotationsToRadians(turnEncoder.getPosition()) * DriveConstants.kEncoderToTurnReduction;
    inputs.turnVelocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(turnEncoder.getVelocity()) * DriveConstants.kEncoderToTurnReduction;
    inputs.turnCurrentAmps = turnSparkMax.getOutputCurrent();
    inputs.turnTempCelcius = turnSparkMax.getMotorTemperature();
  }

  public void setDriveVoltage(double volts) {
    driveSparkMax.setVoltage(volts);
  }

  public void setTurnVoltage(double volts) {
    turnSparkMax.setVoltage(volts);
  }

  public void setDriveBrakeMode(boolean enable) {
    driveSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  public void setTurnBrakeMode(boolean enable) {
    turnSparkMax.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }
}
