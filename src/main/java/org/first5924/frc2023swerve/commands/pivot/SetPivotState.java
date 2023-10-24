// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.pivot;

import org.first5924.frc2023swerve.constants.PivotConstants.PivotState;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetPivotState extends InstantCommand {
  private final Pivot pivot;
  private final PivotState pivotState;

  public SetPivotState(Pivot pivot, PivotState pivotState) {
    this.pivot = pivot;
    this.pivotState = pivotState;
    // Use addRequirements() here to declare subsystem dependencies.
    // No requirements because doesn't actually use pivot subsystem
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pivot.setPivotState(pivotState);
  }
}
