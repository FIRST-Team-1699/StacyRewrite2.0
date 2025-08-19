package frc.team1699.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Configs.IndexerConfigs;
import frc.robot.Constants.IndexerConstants;
import frc.utils.BeamBreak;

public class IndexerSubsystem extends SubsystemBase {
    private SparkMax motor;

    public IndexerSubsystem() {
        motor= new SparkMax(IndexerConstants.motorID, MotorType.kBrushless);
        motor.configureAsync(IndexerConfigs.motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void intake() {
        motor.set(IndexerConstants.kIntakeSpeed);
    }

    public void outake() {
        motor.set(IndexerConstants.kOutakeSpeed);
    }

    public void stop() {
        motor.set(0);
    }

    public Command waitUntilLoaded() {
        return new WaitUntilCommand(BeamBreak.loaded());
    }
}