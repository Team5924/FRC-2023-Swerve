// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.constants;

/** Add your docs here. */
public class PivotConstants {
    public static final int kTalonId = 13;

    public static final double kGearRatio = 118.8;
    public static final double kStartingDegrees = 0;

    public static final double kMaxVoltage = 8;

    public static final double kPickupAngle = 0;
    public static final double kLowAngle = 0;
    public static final double kMidAngle = 0;
    public static final double kHighAngle = 0;
    public static final double kChargeAngle = 0;

    public static enum PivotState {
        PICKUP(kPickupAngle, IntakeConstants.kPickupVoltage),
        LOW(kLowAngle, IntakeConstants.kLowVoltage),
        MID(kMidAngle, IntakeConstants.kMidVoltage),
        HIGH(kHighAngle, IntakeConstants.kHighVoltage),
        CHARGE(kChargeAngle, IntakeConstants.kChargeVoltage);

        private final double pivotAngle;
        private final double intakeVoltage;

        private PivotState(double pivotAngle, double intakeVoltage) {
            this.pivotAngle = pivotAngle;
            this.intakeVoltage = intakeVoltage;
        }

        public double getPivotAngle() {
            return this.pivotAngle;
        }

        public double getIntakeVoltage() {
            return this.intakeVoltage;
        }
    }
}