// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.photonvision.PhotonCamera;

import com.pathplanner.lib.config.PIDConstants;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public final class Constants {
    public static final class SwerveConstants {
    public static final double kMaxSpeed = Units.feetToMeters(4.0);
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;

    public static final double TURN_CONSTANT = 6;
    public static final double LEFT_Y_DEADBAND = .1;
    public static final double RIGHT_X_DEADBAND = .1;
    public static final double DEADBAND = .1;
  }

  public static class AutoConstants {
    public static final PIDConstants TRANSLATION_PID = new PIDConstants(.7, 0, 0);
    public static final PIDConstants ANGLE_PID = new PIDConstants(.4, 0, 0.01);

  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  public static final class ShooterConstants {
    // Subsystem Constants
    public static final int kTopMotorID = 33;
    public static final int kBottomMotorID = 34;

    public static final double kIntakeSpeed = .2;
    // public static final double kOutakeSpeed = -.5;
    public static final double kOutakeSpeed = -.4;
    public static final double kShootSpeed = -.75;

    // Config Constants
    public static final double kForwardLimit = .80;
    public static final double kReverseLimit = -.80;
  }

  public static final class IntakeConstants {
    // Subsystem Constants
    public static final int kTopMotorID = 31;
    public static final int kBottomMotorID = 32;

    public static final double kIntakeSpeed = .3;
    public static final double kOutakeSpeed = -.25;

    // Config Constants
    public static final double kForwardLimit = .80;
    public static final double kReverseLimit = -.80;
  }


  public static final class IndexerConstants {
    public static final int motorID=16;

    public static final double kIntakeSpeed = .25;
    public static final double kOutakeSpeed = -.35;
  }

  public static final class PivotConstants {
    // Subsystem Constants
    public static final int kPivotMotorID = 11;

    // Position (enum) Constants
    // TODO: TUNE
    public static final double kStoredPoint = 10;
    public static final double kIntakePoint = 30;
    public static final double kShootPoint = 40;
    public static final double kShootPointTwo = 37;
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

  public static class PhotonvisionConstants {
    public static final String kCamOneName = "Cool_Cam";

    public static final double cam1XOffset = 0;
    public static final double cam1YOffset = 0.23;

    public static final InterpolatingDoubleTreeMap score =new InterpolatingDoubleTreeMap();

  }
}
