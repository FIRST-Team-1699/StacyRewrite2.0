package frc.team1699.subsystems;

import java.util.function.BooleanSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Configs.PivotConfigs;
import frc.robot.Constants.PivotConstants;

public class PivotSubsystem extends SubsystemBase {
    private static PivotPositions currentSetpoint=PivotPositions.STORED;

    private SparkMax motor;
    private RelativeEncoder encoder;
    private SparkClosedLoopController pidController;

    public PivotSubsystem() {
        motor = new SparkMax(PivotConstants.kPivotMotorID,MotorType.kBrushless);

        encoder = motor.getAlternateEncoder();
        pidController = motor.getClosedLoopController();
        motor.configureAsync(PivotConfigs.motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        setPosition(PivotPositions.STORED);
    }

    public Command setRaw(double heightValue) {
        return runOnce(() -> {
            motor.set(heightValue);
        });
    }

    public Command setPosition(PivotPositions target) {
        return runOnce(() -> {
            currentSetpoint = target;
            pidController.setReference(currentSetpoint.value, SparkBase.ControlType.kPosition, ClosedLoopSlot.kSlot0);
        });
    }

    public WaitUntilCommand waitUntilTolerance() {
        return new WaitUntilCommand(isInTolerance());
    }

    public BooleanSupplier isInTolerance() {
        return (() -> {
            return Math.abs(currentSetpoint.value-encoder.getPosition()) < PivotConstants.kTolerance;
        });
    }

    @Override
    public void periodic() {
        try {
            System.out.println("Pivot Position: " + encoder.getPosition());
            System.out.println("Is in tolerance: " + isInTolerance().getAsBoolean());
        } catch (Exception e) {
        }
    }

    // TODO: TEST BEFORE USING POSITIONS
    public enum PivotPositions {
        STORED(PivotConstants.kStoredPoint),
        AMP(PivotConstants.kAmpPoint),
        INTAKE(PivotConstants.kIntakePoint),
        AIMING();

        /**
         * Determines the position the the pivot moves to. This is not final so that AimToTagCommand can change the value of AIMING.
         */
        public double value;
        private PivotPositions(double value) {
            this.value = value;
        }   
        private PivotPositions() {
            this.value = 0;
        }   
    }
}