// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.math.util.Units;

public final class Constants {
  public static final class SwerveConstants {
    public static final double kMaxSpeed = Units.feetToMeters(14.0);
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
}
