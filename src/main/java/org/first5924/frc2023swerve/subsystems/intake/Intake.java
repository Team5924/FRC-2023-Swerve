// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.intake;

import org.first5924.frc2023swerve.constants.IntakeConstants;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Intake extends SubsystemBase {
  /** Creates a new PivotSubsystem. */
  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();


  public Intake(IntakeIO io) {
    this.io = io;
    //setEncoderFromIntakeDegrees(IntakeConstants.kStartingDegrees);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    Logger.getInstance().processInputs("Intake", inputs);
  }

  public double getPivotPositionDegrees() {
    return inputs.intakePositionDegrees;
  }

  public double getPivotVelocityDegreesPerSecond() {
    return inputs.intakeVelocityDegreesPerSecond;
  }

  public double getOutputCurrent() {
    return inputs.outputCurrent;
  }

  public void setPercent(double percent) {
    io.setPercent(percent);
  }


  public void setEncoderFromPivotDegrees(double pivotDegrees) {
    io.setEncoderPosition(pivotDegrees / 360 * IntakeConstants.kGearRatio);
  }


}
