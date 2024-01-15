// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.pivot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetEncoderFromPivotDegrees extends InstantCommand {
  private final Pivot pivot;
  private final double degrees;

  public SetEncoderFromPivotDegrees(Pivot pivot, double degrees) {
    this.pivot = pivot;
    this.degrees = degrees;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pivot.setEncoderFromPivotDegrees(degrees);
  }
}
