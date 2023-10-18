// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.first5924.frc2023swerve.subsystems.intake.Intake;

public class RunIntake extends CommandBase {
  /** Creates a new RunIntake. */
  private final Intake Intake;
  private final double Speed;
  public RunIntake(Intake intake, double speed) {
    Intake = intake;
    Speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Intake.setPercent(Speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
