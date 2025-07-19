package frc.team1699.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Configs;
import frc.robot.Constants;

public class ShooterSubsystem implements Subsystem {
    TalonFX topMotor, bottomMotor;
    TalonFXConfigurator leadConfigurator, followConfigurator;

    public ShooterSubsystem () {
        topMotor = new TalonFX(Constants.ShooterConstants.topMotorID);
        bottomMotor = new TalonFX(Constants.ShooterConstants.bottomMotorID);

        leadConfigurator = topMotor.getConfigurator();
        leadConfigurator.apply(Configs.ShooterSubsystem.shooterConfig);
        
        followConfigurator = bottomMotor.getConfigurator();
        followConfigurator.apply(Configs.ShooterSubsystem.shooterConfig);

        bottomMotor.setControl(new Follower(Constants.ShooterConstants.topMotorID, false));
    }

    public Command intake() {
        return runOnce(() -> {
            topMotor.set(.25);
        });
    }

    public Command shoot() {
        return runOnce(() -> {
            topMotor.set(-.25);
        });
    }

    public Command stop() {
        return runOnce(() -> {
            topMotor.set(0);
        });
    }

    public BooleanSupplier isMoving() {
        return (() -> {
            return topMotor.get() > 0 || topMotor.get() < 0;
        });
    }
}
