// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.pivot;


import org.first5924.frc2023swerve.constants.PivotConstants;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/** Add your docs here. */
public class PivotIOTalonFX implements PivotIO {
    private final TalonFX pivotTalon = new TalonFX(0);

    

    public PivotIOTalonFX() {

    }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
        inputs.pivotPositionDegrees = pivotTalon.getPosition().getValue() * 360 / PivotConstants.kGearRatio;
        inputs.pivotVelocityDegreesPerSecond = pivotTalon.getVelocity().getValue() / 60 * 360 / PivotConstants.kGearRatio;
        inputs.outputCurrent = pivotTalon.getSupplyCurrent().getValue();
    }

    @Override
    public void setPercent(double percent) {
        pivotTalon.set(percent);
    }

    @Override
    public void setVoltage(double volts) {
        pivotTalon.setVoltage(volts);
    }

    @Override
    public void setEncoderPosition(double position) {
        pivotTalon.setRotorPosition(position);
    }
}
