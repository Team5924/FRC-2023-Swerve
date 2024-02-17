// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.vision;

import org.first5924.frc2023swerve.constants.VisionConstants;
import org.first5924.frc2023swerve.subsystems.vision.LimelightHelpers.LimelightResults;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FieldCam extends SubsystemBase {
  NetworkTable table = NetworkTableInstance.getDefault().getTable(VisionConstants.aprilTagLimelightName);
    // Vertical offset from crosshair to target
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");

    LimelightResults llresults;


    double x;
    double y;
  public FieldCam() {}

  @Override
  public void periodic() {
    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    SmartDashboard.putString("table", table.toString());
    
  }
}
