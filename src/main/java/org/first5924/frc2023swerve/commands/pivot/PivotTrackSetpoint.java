// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.pivot;

import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class PivotTrackSetpoint extends CommandBase {
  private final Pivot pivot;

  /** Creates a new SetPivot. */
  public PivotTrackSetpoint(Pivot pivot) {
    this.pivot = pivot;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pivot.setPosition(pivot.getPivotState().getPivotAngle());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    pivot.setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
