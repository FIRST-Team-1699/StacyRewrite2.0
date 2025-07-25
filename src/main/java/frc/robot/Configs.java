package frc.robot;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants.ShooterConstants;

public final class Configs {
    public static final class ShooterConfigs {
        public static final MotorOutputConfigs leadConfig = new MotorOutputConfigs();

        static {
                leadConfig.Inverted = InvertedValue.Clockwise_Positive;
                leadConfig.PeakForwardDutyCycle = ShooterConstants.kForwardLimit;
                leadConfig.PeakReverseDutyCycle = ShooterConstants.kReverseLimit;
                leadConfig.NeutralMode = NeutralModeValue.Brake;
        }
    }
}
