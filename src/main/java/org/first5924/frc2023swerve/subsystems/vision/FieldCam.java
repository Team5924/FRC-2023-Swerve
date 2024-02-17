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
  NetworkTable botPoseTable = NetworkTableInstance.getDefault().getTable(VisionConstants.aprilTagLimelightName);

  public static double xBotPose = -1;
  public static double yBotPose = -1;

    LimelightResults llresults;
  public FieldCam() {}

  @Override
  public void periodic() {
    double[] botPoseArray = botPoseTable.getEntry("botpose").getDoubleArray(new double[6]);
    xBotPose = botPoseArray[0];
    yBotPose = botPoseArray[1];
    SmartDashboard.putNumber("Bot Pose X", xBotPose);
    SmartDashboard.putNumber("Bot Pose y", yBotPose);
    
  }
}
