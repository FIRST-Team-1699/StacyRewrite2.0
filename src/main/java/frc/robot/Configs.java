package frc.robot;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.ShooterConstants;
import frc.utils.LimelightHelpers;



public final class Configs {
    public static final class PivotConfigs {
        public static final SparkMaxConfig motorConfig = new SparkMaxConfig();

        static {
            motorConfig
                .inverted(PivotConstants.kMotorInverted)
                .idleMode(PivotConstants.kIdleMode);
            motorConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAlternateOrExternalEncoder)
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

    public static final class LimelgihtConfigs {
        static {
            LimelightHelpers.setPipelineIndex("",0);
            LimelightHelpers.setLEDMode_ForceOn("");
            // LimelightHelpers.setCameraPose_RobotSpace("", 
            //     0.5,    // Forward offset (meters)
            //     0.0,    // Side offset (meters)
            //     0.5,    // Height offset (meters)
            //     0.0,    // Roll (degrees)
            //     30.0,   // Pitch (degrees)
            //     0.0     // Yaw (degrees)
            // );
            // LimelightHelpers.setFiducial3DOffset("", 
            //     0.0,    // Forward offset
            //     0.0,    // Side offset  
            //     0.5     // Height offset
            // );
        }
    }
}
