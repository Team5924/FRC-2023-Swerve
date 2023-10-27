// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.autos;

import org.first5924.frc2023swerve.commands.drive.EngageChargeStation;
import org.first5924.frc2023swerve.commands.intake.RunIntake;
import org.first5924.frc2023swerve.commands.pivot.SetPivotState;
import org.first5924.frc2023swerve.constants.PivotConstants.PivotState;
import org.first5924.frc2023swerve.commands.drive.DriveXDirectionFieldCentric;
import org.first5924.frc2023swerve.subsystems.drive.Drive;
import org.first5924.frc2023swerve.subsystems.intake.Intake;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootCrossAndBalance extends SequentialCommandGroup {
  /** Creates a new ScoreAndBalance. */
  public ShootCrossAndBalance(Drive drive, Pivot pivot, Intake intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SetPivotState(pivot, PivotState.MID),
      new WaitCommand(1),
      new RunIntake(intake, pivot),
      new ParallelDeadlineGroup(
        new WaitCommand(5),
        new DriveXDirectionFieldCentric(drive, 0.85)
      ),
      new WaitCommand(0.05),
      new ParallelDeadlineGroup(
        new WaitCommand(1.75),
        new DriveXDirectionFieldCentric(drive, -0.75)
      ),
      new EngageChargeStation(drive)
    );
  }
}
