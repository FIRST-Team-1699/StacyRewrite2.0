package frc.robot;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.VisionConstants;



public final class Configs {
    public static final class PivotConfigs {
        public static final SparkMaxConfig motorConfig = new SparkMaxConfig();

        static {
            motorConfig
                .inverted(PivotConstants.kMotorInverted)
                .idleMode(PivotConstants.kIdleMode);
            motorConfig.closedLoop
                .feedbackSensor(com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor.kAlternateOrExternalEncoder)
                .pidf(PivotConstants.kP, PivotConstants.kI, PivotConstants.kD, PivotConstants.kFF, ClosedLoopSlot.kSlot0)
                .outputRange(PivotConstants.kReverseLimit, PivotConstants.kForwardLimit, ClosedLoopSlot.kSlot0);
            motorConfig.alternateEncoder
                .positionConversionFactor(PivotConstants.kConversionFactor)
                .velocityConversionFactor(PivotConstants.kConversionFactor) 
                .countsPerRevolution(8192)
                .inverted(PivotConstants.kEncoderInverted);
            motorConfig.softLimit
                .forwardSoftLimit(PivotConstants.kMaximumRotation)
                .forwardSoftLimitEnabled(true)
                .reverseSoftLimit(PivotConstants.kMinimumRotation)
                .reverseSoftLimitEnabled(true);
        }
    }

    public static final class ShooterConfigs {
        public static final MotorOutputConfigs leadConfig = new MotorOutputConfigs();

        static {
            leadConfig.Inverted = InvertedValue.Clockwise_Positive;
            leadConfig.PeakForwardDutyCycle = ShooterConstants.kForwardLimit;
            leadConfig.PeakReverseDutyCycle = ShooterConstants.kReverseLimit;
            leadConfig.NeutralMode = NeutralModeValue.Coast;
        }
    }

    public static final class IndexerConfigs {
        public static final SparkMaxConfig motorConfig = new SparkMaxConfig();
        static {
            motorConfig
                .inverted(true)
                .idleMode(IdleMode.kCoast);
        }
    }

    public static final class IntakeConfigs {
        public static final SparkMaxConfig leadConfig = new SparkMaxConfig();
        public static final SparkMaxConfig followConfig = new SparkMaxConfig();

        static {
            leadConfig
                .inverted(true)
                .idleMode(IdleMode.kCoast);

            followConfig
                .apply(leadConfig)
                .follow(IntakeConstants.kTopMotorID,false);
        }
    }

    public static final class PhotonConfigs {
        static {
            // VisionConstants.score.put(4.5, 35.0);
            // VisionConstants.score.put(2.74, 40.0);
            // VisionConstants.score.put(2.0, 50.0);
        }
    }
}
