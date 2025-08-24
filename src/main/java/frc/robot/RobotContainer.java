// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.SwerveSubsystem;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import swervelib.SwerveInputStream;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.trajectory.ExponentialProfile.Constraints;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;

import java.io.File;

import com.pathplanner.lib.auto.NamedCommands;

public class RobotContainer {

    private final CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);

    private final SwerveSubsystem drivetrain = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
        "swerve"));

    private PivotSubsystem pivot = new PivotSubsystem();
    private IndexerSubsystem indexer = new IndexerSubsystem();
    private ShooterSubsystem shoot = new ShooterSubsystem();

    SwerveInputStream driveAngularVelocity = SwerveInputStream
        .of(drivetrain.getSwerveDrive(),
            (() -> {
                return driverController.getLeftY() * -1;
            }),
            () -> {
                return driverController.getLeftX() * -1;
            })
        .withControllerRotationAxis(driverController::getRightX)
        .deadband(OIConstants.DEADBAND)
        .scaleTranslation(0.8)
        .allianceRelativeControl(true);

    /**
     * Clone's the angular velocity input stream and converts it to a fieldRelative
     * input stream.
     */
    SwerveInputStream driveDirectAngle = driveAngularVelocity.copy()
        .withControllerHeadingAxis(driverController::getRightX,
            driverController::getRightY)
        .headingWhile(true);

    /**
     * Clone's the angular velocity input stream and converts it to a robotRelative
     * input stream.
     */
    SwerveInputStream driveRobotOriented = driveAngularVelocity.copy().robotRelative(true)
        .allianceRelativeControl(false);

    SwerveInputStream driveAngularVelocityKeyboard = SwerveInputStream
        .of(drivetrain.getSwerveDrive(),
            () -> -driverController.getLeftY(),
            () -> -driverController.getLeftX())
        .withControllerRotationAxis(() -> driverController.getRawAxis(
            2))
        .deadband(OIConstants.DEADBAND)
        .scaleTranslation(0.8)
        .allianceRelativeControl(true);
    // Derive the heading axis with math!
    SwerveInputStream driveDirectAngleKeyboard = driveAngularVelocityKeyboard.copy()
        .withControllerHeadingAxis(() -> Math.sin(driverController.getRawAxis(2) * Math.PI) 
            * (Math.PI * 2),
            () -> Math.cos(driverController.getRawAxis(2) * Math.PI)
            * (Math.PI * 2))
        .headingWhile(true)
        .translationHeadingOffset(true)
        .translationHeadingOffset(Rotation2d.fromDegrees(0));

    public RobotContainer() {
        configureBindings();
        DriverStation.silenceJoystickConnectionWarning(true);
        NamedCommands.registerCommand("test", Commands.print("I EXIST"));
    }

    private void configureBindings() {
        // REFER TO https://gist.github.com/calebreister/8018231 FOR INDEXS-TO-BUTTONS. ALSO IGNORING TEST BUTTONS
        // -------------------------
        // DRIVER CONTROLLER BUTTONS
        // -------------------------
        // Left Joystick : Movement (AngularVelocity)
        // A : Zero's swerve gyro towards given direction
        // X : Fakes vision/ Limelight (eventually) reading
        // Start && Back && right-bumper : None
        // left bumper : Locks drivetrain

        Command driveFieldOrientedDirectAngle = drivetrain.driveFieldOriented(driveDirectAngle);

        Command driveFieldOrientedAnglularVelocity = drivetrain.driveFieldOriented(driveAngularVelocity);

        Command driveRobotOrientedAngularVelocity = drivetrain.driveFieldOriented(driveRobotOriented);

        Command driveSetpointGen = drivetrain.driveWithSetpointGeneratorFieldRelative(driveDirectAngle);

        Command driveFieldOrientedDirectAngleKeyboard = drivetrain.driveFieldOriented(driveDirectAngleKeyboard);

        Command driveFieldOrientedAnglularVelocityKeyboard = drivetrain.driveFieldOriented(driveAngularVelocityKeyboard);

        Command driveSetpointGenKeyboard = drivetrain.driveWithSetpointGeneratorFieldRelative(driveDirectAngleKeyboard);

        if (RobotBase.isSimulation()) {
            drivetrain.setDefaultCommand(driveFieldOrientedDirectAngleKeyboard);
        } else {
            drivetrain.setDefaultCommand(driveFieldOrientedAnglularVelocity);
        }

        if (Robot.isSimulation()) {
            Pose2d target = new Pose2d(new Translation2d(1, 4),
                Rotation2d.fromDegrees(90));

            drivetrain.getSwerveDrive().field.getObject("targetPose").setPose(target);

            driveDirectAngleKeyboard.driveToPose(() -> target,
                new ProfiledPIDController(5.0,
                    0.0,
                    0.0,
                    new TrapezoidProfile.Constraints(5, 2)),
                new ProfiledPIDController(5,
                    0,
                    0,
                    new TrapezoidProfile.Constraints(Units.degreesToRadians(360),
                            Units.degreesToRadians(180))));
            driverController.start()
                .onTrue(Commands.runOnce(() -> drivetrain.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
            driverController.button(1).whileTrue(drivetrain.sysIdDriveMotorCommand());
            driverController.button(2)
                .whileTrue(Commands.runEnd(() -> driveDirectAngleKeyboard.driveToPoseEnabled(true),
                    () -> driveDirectAngleKeyboard.driveToPoseEnabled(false)));

            // driverController.b().whileTrue(
            // drivebase.driveToPose(
            // new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(0)))
            // );

        }
        if (DriverStation.isTest()) {
            drivetrain.setDefaultCommand(driveFieldOrientedAnglularVelocity); // Overrides drive command above!

            driverController.x().whileTrue(Commands.runOnce(drivetrain::lock, drivetrain).repeatedly());
            driverController.y().whileTrue(drivetrain.driveToDistanceCommand(1.0, 0.2));
            driverController.start().onTrue((Commands.runOnce(drivetrain::zeroGyro)));
            driverController.back().whileTrue(drivetrain.centerModulesCommand());
            driverController.leftBumper().onTrue(Commands.none());
            driverController.rightBumper().onTrue(Commands.none());
        } else {
            driverController.a().onTrue((Commands.runOnce(drivetrain::zeroGyro)));
            driverController.x().onTrue(Commands.runOnce(drivetrain::addFakeVisionReading));
            driverController.start().whileTrue(Commands.none());
            driverController.back().whileTrue(Commands.none());
            driverController.leftBumper().whileTrue(Commands.runOnce(drivetrain::lock, drivetrain).repeatedly());
            driverController.rightBumper().onTrue(Commands.none());
        }

    operatorController.povDown()
        .onTrue(
            pivot.setPosition(PivotPositions.STORED)
            .andThen(pivot.waitUntilTolerance())
        );

    operatorController.povLeft()
        .onTrue(
            pivot.setPosition(PivotPositions.INTAKE)
            .andThen(pivot.waitUntilTolerance())
        );

    operatorController.povUp()
        .onTrue(
            pivot.setPosition(PivotPositions.AMP)
            .andThen(pivot.waitUntilTolerance())
        );
    operatorController.leftTrigger()
        .whileTrue(new IntakeCommand(shoot, indexer));
            
    operatorController.leftBumper()
        .whileTrue(new ShootCommand(shoot, indexer));
  }

  public Command getAutonomousCommand() {
    return new RunCommand(() -> {
      System.out.println("Running auto");
    });
  }

    public void pivotToStored() {
        pivot.setPosition(PivotPositions.STORED).schedule();
    }

    public void setMotorBrake(boolean brake) {
        drivetrain.setMotorBrake(brake);
    }
}
