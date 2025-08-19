// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private PivotSubsystem pivot = new PivotSubsystem();

  private final CommandXboxController operatorController =
      new CommandXboxController(OIConstants.kOperatorControllerPort);

  private final CommandXboxController driverController =
      new CommandXboxController(OIConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
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
  }

  public Command getAutonomousCommand() {
    return new RunCommand(() -> {
      System.out.println("Running auto");
    });
  }

  public void pivotToStored() {
    pivot.setPosition(PivotPositions.STORED).schedule();
}
}
