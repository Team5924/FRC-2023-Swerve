// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.first5924.frc2023swerve.subsystems.intake;


import org.first5924.frc2023swerve.constants.IntakeConstants;


import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.ParentDevice;

import com.ctre.phoenix6.hardware.TalonFX;



/** Add your docs here. */
public class IntakeIOTalonFX implements IntakeIO {
    private final TalonFX intakeTalon = new TalonFX(0);

    public IntakeIOTalonFX() {

    }

    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.intakePositionDegrees = intakeTalon.getRotorPosition().getValue() * 360 / IntakeConstants.kGearRatio;
        inputs.intakeVelocityDegreesPerSecond = intakeTalon.getVelocity().getValue() / 60 * 360 / IntakeConstants.kGearRatio;
        inputs.outputCurrent = intakeTalon.getSupplyVoltage().getValue();
    }

    @Override
    public void setPercent(double percent) {
        intakeTalon.set(percent);
    }


    @Override
    public void setEncoderPosition(double position) {
        intakeTalon.setRotorPosition(position);
    }
}
