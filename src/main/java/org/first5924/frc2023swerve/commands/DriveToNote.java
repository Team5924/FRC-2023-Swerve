// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.commands;

import org.first5924.frc2023swerve.subsystems.drive.Drive;
import org.first5924.frc2023swerve.subsystems.vision.Vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveToNote extends Command {
  /** Creates a new LookAtNote. */
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    // Vertical offset from crosshair to target
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");

  private final Vision vision;
  private final Drive drive;
  public DriveToNote(Vision vision, Drive drive) {
    this.vision = vision;
    this.drive = drive;
    addRequirements(vision);
    addRequirements(drive);
    //PIDController visionController = new PIDController(.1, 0, 0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = tx.getDouble(0);
    double y = ty.getDouble(0);
    //visionController.calculate()
    if(x > 1){
      drive.drive(.1, .1, .1, false);
    }
    if(x < -1){
      drive.drive(.1,.1,-.1,false);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
