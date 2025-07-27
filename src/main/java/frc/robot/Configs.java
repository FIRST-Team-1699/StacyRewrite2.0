package frc.robot;

import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants.PivotConstants;



public final class Configs {
    public static final class PivotConfigs {
        public static final SparkMaxConfig motorConfig = new SparkMaxConfig();

        static {
            motorConfig
                .inverted(PivotConstants.kMotorInverted)
                .idleMode(PivotConstants.kIdleMode);
                // .smartCurrentLimit(PivotConstants.kStallLimit, PivotConstants.kFreeLimit);
            motorConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAlternateOrExternalEncoder)
                .pidf(PivotConstants.kP, PivotConstants.kI, PivotConstants.kD, PivotConstants.kFF, ClosedLoopSlot.kSlot0)
                .outputRange(PivotConstants.kReverseLimit, PivotConstants.kForwardLimit, ClosedLoopSlot.kSlot0);
            motorConfig.alternateEncoder
                .positionConversionFactor(PivotConstants.kConversionFactor)
                .velocityConversionFactor(PivotConstants.kConversionFactor) 
                .inverted(PivotConstants.kEncoderInverted);
            motorConfig.softLimit
                .forwardSoftLimit(PivotConstants.kMaximumRotation)
                .forwardSoftLimitEnabled(true)
                .reverseSoftLimit(PivotConstants.kMinimumRotation)
                .reverseSoftLimitEnabled(true);
        }
    }
}
