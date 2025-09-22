package frc.team1699.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Configs.IntakeConfigs;
import frc.robot.Constants.IntakeConstants;
import frc.utils.BeamBreak;

public class IntakeSubsystem extends SubsystemBase {
    private SparkMax leadMotor, follwMotor;

    public IntakeSubsystem() {
        leadMotor= new SparkMax(IntakeConstants.kTopMotorID, MotorType.kBrushless);

        leadMotor.configureAsync(IntakeConfigs.leadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        follwMotor.configureAsync(IntakeConfigs.followConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void intake() {
        leadMotor.set(IntakeConstants.kIntakeSpeed);
    }

    public void outake() {
        leadMotor.set(IntakeConstants.kOutakeSpeed);
    }

    public void stop() {
        leadMotor.set(0);
    }
}