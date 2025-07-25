// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;

    public static final double kDriveDeadband = 0.05;
  }

  public static final class ShooterConstants {
    // Subsystem Constants
    public static final int kTopMotorID = 31;
    public static final int kBottomMotorID = 32;

    public static final double kIntakeSpeed = .25;
    public static final double kOutakeSpeed = -.25;

    // Config Constants
    public static final double kForwardLimit = .50;
    public static final double kReverseLimit = -.50;
  }
}
