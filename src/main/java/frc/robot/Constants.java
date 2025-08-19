// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public final class Constants {
  public static class OIConstants {
    public static final int kOperatorControllerPort = 0;
    public static final int kDriverControllerPort = 1;
  }

  public static class SwerveConstants {
    
  }

  public static final class ShooterConstants {
    // Subsystem Constants
    public static final int kTopMotorID = 33;
    public static final int kBottomMotorID = 34;

    public static final double kIntakeSpeed = .15;
    public static final double kOutakeSpeed = -.25;
    public static final double kShootSpeed = -.75;

    // Config Constants
    public static final double kForwardLimit = .80;
    public static final double kReverseLimit = -.80;
  }

  public static final class IndexerConstants {
    public static final int motorID=16;

    public static final double kIntakeSpeed = .25;
    public static final double kOutakeSpeed = -.25;
  }

  public static final class PivotConstants {
    // Subsystem Constants
    public static final int kPivotMotorID = 11;

    // Position (enum) Constants
    // TODO: TUNE
    public static final double kStoredPoint = 10;
    public static final double kIntakePoint = 30;
    public static final double kAmpPoint = 65;

    public static final double kTolerance = 0.5;

    // Config Constants
    // TODO: TUNE
    public static final double kForwardLimit = 0.8;
    public static final double kReverseLimit = -0.8;

    public static final double kMaximumRotation = 66;
    public static final double kMinimumRotation = 0;

    public static final boolean kMotorInverted = true;
    public static final boolean kEncoderInverted = true;

    public static final IdleMode kIdleMode = IdleMode.kBrake;

    public static final double kP = 0.025;
    public static final double kI = 0;
    public static final double kD = 0.016;
    public static final double kFF = 0;

    public static final double kConversionFactor = 360;
    public static final double kOffset = 0;
  }

  public static class LimelightHelperConstants {

  }
}
