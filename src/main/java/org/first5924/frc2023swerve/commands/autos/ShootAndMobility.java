// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.first5924.frc2023swerve.commands.drive.DriveXDirectionFieldCentric;
import org.first5924.frc2023swerve.commands.drive.SetGyroYaw;
import org.first5924.frc2023swerve.commands.intake.RunIntake;
import org.first5924.frc2023swerve.commands.pivot.SetPivotState;
import org.first5924.frc2023swerve.constants.PivotConstants.PivotState;
import org.first5924.frc2023swerve.subsystems.drive.Drive;
import org.first5924.frc2023swerve.subsystems.intake.Intake;
import org.first5924.frc2023swerve.subsystems.pivot.Pivot;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAndMobility extends SequentialCommandGroup {
  /** Creates a new ScoreAndBalance. */
  public ShootAndMobility(Drive drive, Pivot pivot, Intake intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SetGyroYaw(drive, 180),
        new SetPivotState(pivot, PivotState.LOW),
        new WaitCommand(1),
        new ParallelDeadlineGroup(new WaitCommand(0.5), new RunIntake(intake, pivot)),
        new SetPivotState(pivot, PivotState.STOW),
        new WaitCommand(1),
        new ParallelDeadlineGroup(
            new WaitCommand(3.75), new DriveXDirectionFieldCentric(drive, 1)));
  }
}
