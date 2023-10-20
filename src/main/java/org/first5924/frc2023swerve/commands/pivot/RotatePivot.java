// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.pivot;
import java.util.function.DoubleSupplier;

import org.first5924.frc2023swerve.constants.InputConstants;
import org.first5924.frc2023swerve.constants.PivotConstants;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotatePivot extends CommandBase {
  private final Pivot mPivot;
  private final DoubleSupplier mJoystickY;

  //private final PivotIOSparkMax m_pivot;
  //pr0ivate PivotIO m_percentage;
  /** Creates a new RotatePivot. */
  public RotatePivot(Pivot pivot, DoubleSupplier joystickY) {
    mPivot = pivot;
    mJoystickY = joystickY;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(mPivot);
  }



  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mPivot.setPercent(MathUtil.applyDeadband(-mJoystickY.getAsDouble(), InputConstants.kDeadband) * PivotConstants.kSpeedMultiplier);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
