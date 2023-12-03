// Copyright (c) 2023 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.first5924.frc2023swerve.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.first5924.frc2023swerve.constants.DriveConstants;
import org.littletonrobotics.junction.Logger;

public class Drive extends SubsystemBase {
  private static final double coastThresholdMetersPerSec =
      0.05; // Need to be under this to switch to coast when disabling
  private static final double coastThresholdSecs =
      10.0; // Need to be under the above speed for this length of time to switch to coast

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final Module[] modules = new Module[4]; // FL, BL, BR, FR

  private SwerveDriveKinematics kinematics =
      new SwerveDriveKinematics(
          new Translation2d(DriveConstants.kTrackWidthX / 2, DriveConstants.kTrackWidthY / 2),
          new Translation2d(DriveConstants.kTrackWidthX / 2, -DriveConstants.kTrackWidthY / 2),
          new Translation2d(-DriveConstants.kTrackWidthX / 2, DriveConstants.kTrackWidthY / 2),
          new Translation2d(-DriveConstants.kTrackWidthX / 2, -DriveConstants.kTrackWidthY / 2));

  private boolean isBrakeMode = false;
  private Timer lastMovementTimer = new Timer();

  public Drive(
      GyroIO gyroIO,
      ModuleIO flModuleIO,
      ModuleIO frModuleIO,
      ModuleIO blModuleIO,
      ModuleIO brModuleIO) {
    this.gyroIO = gyroIO;
    modules[0] = new Module(flModuleIO, 0);
    modules[1] = new Module(frModuleIO, 1);
    modules[2] = new Module(blModuleIO, 2);
    modules[3] = new Module(brModuleIO, 3);
    lastMovementTimer.start();
    for (var module : modules) {
      module.setBrakeMode(false);
    }
  }

  public void drive(
      double vxMetersPerSecond,
      double vyMetersPerSecond,
      double omegaRadiansPerSecond,
      boolean fieldCentric) {
    ChassisSpeeds speeds =
        fieldCentric
            ? ChassisSpeeds.fromFieldRelativeSpeeds(
                vxMetersPerSecond,
                vyMetersPerSecond,
                omegaRadiansPerSecond,
                new Rotation2d(gyroInputs.yawPositionRad))
            : new ChassisSpeeds(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond);
    SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, DriveConstants.kMaxLinearSpeed);
    for (int i = 0; i < 4; i++) {
      modules[i].runSetpoint(moduleStates[i]);
    }
  }

  public void periodic() {
    SmartDashboard.putNumber("Pitch degrees", getPitch().getDegrees());
    gyroIO.updateInputs(gyroInputs);
    Logger.processInputs("Drive/Gyro", gyroInputs);
    for (var module : modules) {
      module.periodic();
    }

    // Run modules
    if (DriverStation.isDisabled()) {
      // Stop moving while disabled
      stop();
    } else {
      // Update brake mode
      boolean stillMoving = false;
      for (int i = 0; i < 4; i++) {
        if (Math.abs(modules[i].getVelocityMetersPerSec()) > coastThresholdMetersPerSec) {
          stillMoving = true;
        }
      }
      if (stillMoving) lastMovementTimer.reset();
      if (DriverStation.isEnabled()) {
        if (!isBrakeMode) {
          isBrakeMode = true;
          for (var module : modules) {
            module.setBrakeMode(true);
          }
        }
      } else {
        if (isBrakeMode && lastMovementTimer.hasElapsed(coastThresholdSecs)) {
          isBrakeMode = false;
          for (var module : modules) {
            module.setBrakeMode(false);
          }
        }
      }
    }
  }

  /** Stops the drive. */
  public void stop() {
    for (Module module : modules) {
      module.stop();
    }
  }

  public void setGyroYaw(double yaw) {
    gyroIO.setGyroYaw(yaw);
  }

  /** Returns the current pitch (Y rotation). */
  public Rotation2d getPitch() {
    return new Rotation2d(gyroInputs.pitchPositionRad);
  }

  /** Returns the current roll (X rotation). */
  public Rotation2d getRoll() {
    return new Rotation2d(gyroInputs.rollPositionRad);
  }

  /** Returns the current yaw velocity (Z rotation) in radians per second. */
  public double getYawVelocity() {
    return gyroInputs.yawVelocityRadPerSec;
  }

  /** Returns the current pitch velocity (Y rotation) in radians per second. */
  public double getPitchVelocity() {
    return gyroInputs.pitchVelocityRadPerSec;
  }

  /** Returns the current roll velocity (X rotation) in radians per second. */
  public double getRollVelocity() {
    return gyroInputs.rollVelocityRadPerSec;
  }
}
