// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.vision;

import org.first5924.frc2023swerve.constants.FieldConstants;
import org.first5924.frc2023swerve.constants.VisionConstants;
import org.first5924.frc2023swerve.subsystems.vision.LimelightHelpers.LimelightResults;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FieldCam extends SubsystemBase {
  NetworkTable botPoseTable = NetworkTableInstance.getDefault().getTable(VisionConstants.aprilTagLimelightName);

  private double xBotPose = -1;
  private double yBotPose = -1;

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

  public double getBotPoseX(){
    return xBotPose;
  }

  public double getBotPoseY(){
    return yBotPose;
  }

  public double getDistanceToBlueSpeakerMeters(){
    double x = FieldConstants.xBlueSpeakerMeters - xBotPose;
    double y = FieldConstants.ySpeakerMeters - yBotPose;

    //pythagorean theorem to return distance to speaker as the crow flies
    return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

  }

  public double getDistanceToRedSpeakerMeters(){
    double x = FieldConstants.xRedSpeakerMeters - xBotPose;
    double y = FieldConstants.ySpeakerMeters - yBotPose;

    return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  }

  public double getRedShooterAngle(){

    // estimate the amount that the note will drop due to gravity using Δx = 1/2 * a * t^2
    double time = getDistanceToRedSpeakerMeters()/FieldConstants.shooterExitVeloMPS;
    double drop = .5 * -9.81 * Math.pow(time, 2);

    // set the x and y values for the arctan
    double y = FieldConstants.speakerHeight - FieldConstants.exampleShooterHeight + drop;
    double x = getDistanceToRedSpeakerMeters();
    Math.atan(y/x);
  }

  public double getBlueShooterAngle(){

    // estimate the amount that the note will drop due to gravity using Δx = 1/2 * a * t^2
    double time = getDistanceToBlueSpeakerMeters()/FieldConstants.shooterExitVeloMPS;
    double drop = .5 * -9.81 * Math.pow(time, 2);

    // set the x and y values for the arctan
    double y = FieldConstants.speakerHeight - FieldConstants.exampleShooterHeight + drop;
    double x = getDistanceToBlueSpeakerMeters();
    Math.atan(y/x);
  }
  

}
