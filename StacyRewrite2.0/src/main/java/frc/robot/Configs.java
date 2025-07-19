package frc.robot;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants.ModuleConstants;

public final class Configs {
    public static class ShooterSubsystem {
        public static final MotorOutputConfigs shooterConfig = new MotorOutputConfigs();

        static {
                shooterConfig.Inverted = InvertedValue.Clockwise_Positive;
                shooterConfig.NeutralMode = NeutralModeValue.Coast;
                shooterConfig.PeakForwardDutyCycle = .25;
        }
    }
}