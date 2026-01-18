package frc.team1699.commands;

import java.lang.annotation.Target;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.PhotonvisionConstants;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.SwerveSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import frc.utils.BeamBreak;

public class AimToTagCommand extends Command {
    private SwerveSubsystem drivetrain;
    private PivotSubsystem pivot;
    private VisionSubsystem vision;
    private static PIDController headingController = new PIDController(.15, 0, 0);
    public AimToTagCommand(SwerveSubsystem drivetrain, PivotSubsystem pivot, VisionSubsystem vision) {
        this.drivetrain=drivetrain;
        this.pivot=pivot;
        this.vision=vision;
        addRequirements(drivetrain,pivot,vision);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        // if (!vision.getHasTag()) {
        //     return;
        // }
        double rotationOutput = headingController.calculate(vision.getBasketYaw(), 0);
        drivetrain.setChassisSpeeds(new ChassisSpeeds(0, 0, rotationOutput));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterupted) {
        if(isInterupted) {
            return;
        }
        // pivot.setPosition(PivotPositions.AIMING);
        // new ShootCommand(shoot, indexer).schedule();
    }
}
