// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import org.first5924.frc2023swerve.subsystems.intake.Intake;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

public class RunIntake extends Command {
  /** Creates a new RunIntake. */
  private final Intake intake;

  private final Pivot pivot;

  public RunIntake(Intake intake, Pivot pivot) {
    this.intake = intake;
    this.pivot = pivot;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.setVoltage(pivot.getPivotState().getIntakeVoltage());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setVoltage(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
