package frc.team1699.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Configs.ShooterConfigs;

public class ShooterSubsystem extends SubsystemBase {
    // NOTE: leadMotor = TOP MOTOR
    //       followMotor = BOTTOM MOTOR
    private TalonFX leadMotor, followMotor;

    private TalonFXConfigurator leadConfigurator;

    public ShooterSubsystem() {
        leadMotor = new TalonFX(ShooterConstants.kTopMotorID);
        followMotor = new TalonFX(ShooterConstants.kBottomMotorID);

        configureMotors();
    }

    public void configureMotors() {
        leadConfigurator = leadMotor.getConfigurator();
        
        leadConfigurator.apply(ShooterConfigs.leadConfig);
        followMotor.setControl(new Follower(ShooterConstants.kTopMotorID, false));
    }

    public Command intake() {
        return runOnce(() -> {
            leadMotor.set(ShooterConstants.kIntakeSpeed);
        });
    }

    public Command outaketake() {
        return runOnce(() -> {
            leadMotor.set(ShooterConstants.kOutakeSpeed);
        });
    }

    public Command stop() {
        return runOnce(() -> {
            leadMotor.set(0);
        });
    }

    public BooleanSupplier isRunning() {
        return (() -> {
            return leadMotor.get() != 0;
        });
    }
}
