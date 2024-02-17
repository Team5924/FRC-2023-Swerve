// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.vision;

import org.first5924.frc2023swerve.subsystems.vision.LimelightHelpers;
import org.first5924.frc2023swerve.subsystems.vision.LimelightHelpers.LimelightResults;
import org.first5924.frc2023swerve.subsystems.vision.LimelightHelpers.LimelightTarget_Detector;
import org.first5924.frc2023swerve.constants.VisionConstants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class DetectorCam extends SubsystemBase {
    NetworkTable table = NetworkTableInstance.getDefault().getTable(VisionConstants.detectorLimelightName);
    // Vertical offset from crosshair to target
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");

    LimelightResults llresults;


    double x;
    double y;
    public DetectorCam() {
        
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        SmartDashboard.putString("table", table.toString());
        SmartDashboard.putNumber("distance", getDistanceToTargetInches());
        SmartDashboard.putNumber("nomuber of targets in view", GetNumberOfTargets());
        // System.out.println("BANANA - table: " + table.containsKey("tx") + " / pos: (" + x + ", " + y + ")");
    }

    public int GetNumberOfTargets()
    {
        llresults = LimelightHelpers.getLatestResults("");

        LimelightTarget_Detector[] targets = llresults.targetingResults.targets_Detector;
        return targets.length;
    }

    public final Pose2d robotPose() {
        return LimelightHelpers.getBotPose2d(getName());
    }

    /**
     * Distance from the point the limelight is looking at to the target (🍩)
     * @return the distance ^
     */
    public double getVerticalDistanceToTargetInches()
    {
        // angle from crosshair to 🍩 (vertical)
        double targetOffsetAngleVerticalRadians = ty.getDouble(0) * VisionConstants.degreesToRadiansMultiplier;

        // angle from limelight to 🍩 (vertical)
        double angleToGoal = VisionConstants.limelightMountAngleRadians + targetOffsetAngleVerticalRadians;

        return VisionConstants.limelightHeight * Math.tan(angleToGoal);
    }

    public double getDistanceToTargetInches()
    {
        // angle from crosshair to 🍩 (vertical)
        double targetOffsetAngleVerticalRadians = ty.getDouble(0) * VisionConstants.degreesToRadiansMultiplier;

        // angle from limelight to 🍩 (vertical)
        double angleToGoal = VisionConstants.limelightMountAngleRadians + targetOffsetAngleVerticalRadians;

        return VisionConstants.limelightHeight / Math.tan(angleToGoal);
    }
}


