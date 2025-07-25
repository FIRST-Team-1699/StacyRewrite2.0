// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.team1699.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
    ShooterSubsystem shoot = new ShooterSubsystem();

    CommandXboxController driverController = new CommandXboxController(OIConstants.kDriverControllerPort);
    CommandXboxController operatorController = new CommandXboxController(OIConstants.kOperatorControllerPort);

    public RobotContainer() {
        configureButtonBindings();

    }


    private void configureButtonBindings() {
        operatorController.leftTrigger()
            .whileTrue(shoot.intake())
            .onFalse(shoot.stop());
         operatorController.leftBumper()
            .whileTrue(shoot.outaketake())
            .onFalse(shoot.stop());
    }

    public Command getAutonomousCommand() {
        return new RunCommand(() -> {
            System.out.println("Hello World");
        });
    }
}
