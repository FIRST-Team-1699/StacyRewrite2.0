// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.DriveSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final CommandXboxController operatorController =
      new CommandXboxController(OIConstants.kOperatorController);

  private final CommandXboxController driverController =
      new CommandXboxController(DirverConstants.kDriverControllerPort);
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    operatorController.leftTrigger()
      .whileTrue(shooterSubsystem.intake())
      .onFalse(shooterSubsystem.stop());   

    operatorController.rightTrigger()
      .whileTrue(shooterSubsystem.shoot())
      .onFalse(shooterSubsystem.stop());            
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  // public Command getAutonomousCommand() {
  //   // An example command will be run in autonomous
  //   // return Autos.exampleAuto(m_exampleSubsystem);
    
  // }
}
