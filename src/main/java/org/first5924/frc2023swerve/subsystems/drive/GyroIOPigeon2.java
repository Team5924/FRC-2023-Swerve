// Copyright (c) 2023 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package org.first5924.frc2023swerve.subsystems.drive;

import org.first5924.frc2023swerve.constants.RobotConstants;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.util.Units;

/** IO implementation for Pigeon2 */
public class GyroIOPigeon2 implements GyroIO {
  private final Pigeon2 mPigeon2 = new Pigeon2(RobotConstants.kPigeonId);

  public GyroIOPigeon2() {
    mPigeon2.setYaw(0.0);
  }

  public void updateInputs(GyroIOInputs inputs) {
    inputs.pitchPositionRad = Units.degreesToRadians(mPigeon2.getPitch().getValue());
    inputs.yawPositionRad = Units.degreesToRadians(mPigeon2.getYaw().getValue());
    inputs.rollPositionRad = Units.degreesToRadians(mPigeon2.getYaw().getValue());
    inputs.pitchVelocityRadPerSec = Units.degreesToRadians(mPigeon2.getAngularVelocityX().getValue());
    inputs.yawVelocityRadPerSec = Units.degreesToRadians(mPigeon2.getAngularVelocityY().getValue());
    inputs.rollVelocityRadPerSec = Units.degreesToRadians(mPigeon2.getAngularVelocityZ().getValue());
  }

  public void zeroGyroYaw() {
    mPigeon2.setYaw(0);
  }
}
