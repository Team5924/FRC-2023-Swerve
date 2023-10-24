// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.pivot;

import org.first5924.frc2023swerve.constants.PivotConstants;
import org.first5924.frc2023swerve.constants.PivotConstants.PivotState;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Pivot extends SubsystemBase {
  /** Creates a new PivotSubsystem. */
  private final PivotIO io;
  private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
  private final PIDController pidController = new PIDController(0.4, 0, 0);
  private PivotState pivotState = PivotState.HIGH;

  public Pivot(PivotIO io) {
    this.io = io;
    setEncoderFromPivotDegrees(PivotConstants.kStartingDegrees);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    io.updateInputs(inputs);
    Logger.getInstance().processInputs("Pivot", inputs);
    Logger.getInstance().recordOutput("Pivot/State", getPivotState().toString());
  }

  public double getPivotPositionDegrees() {
    return inputs.pivotPositionDegrees;
  }

  public double getOutputCurrent() {
    return inputs.supplyCurrent;
  }

  public void setVoltage(double voltage) {
    io.setVoltage(voltage);
  }

  public void setPosition(double position) {
    io.setVoltage(MathUtil.clamp(pidController.calculate(getPivotPositionDegrees(), position), -PivotConstants.kMaxVoltage, PivotConstants.kMaxVoltage));
  }

  public void setEncoderFromPivotDegrees(double pivotDegrees) {
    io.setEncoderPosition(pivotDegrees / 360 * PivotConstants.kGearRatio);
  }

  public void setPivotState(PivotState pivotState) {
    this.pivotState = pivotState;
  }

  public PivotState getPivotState() {
    return this.pivotState;
  }
}
