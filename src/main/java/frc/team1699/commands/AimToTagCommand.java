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
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import frc.utils.BeamBreak;

public class AimToTagCommand extends Command {
    private boolean isFinishedAiming = false;
    private SwerveSubsystem drivetrain;
    private PivotSubsystem pivot;
    private static PIDController headingController = new PIDController(.15, 0, 0);
    public AimToTagCommand(SwerveSubsystem drivetrain, PivotSubsystem pivot) {
        this.drivetrain=drivetrain;
        this.pivot=pivot;
        addRequirements(drivetrain,pivot);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        var results = PhotonvisionConstants.cam.getAllUnreadResults();
        if (!results.isEmpty()) {
            try {
                var result = results.get(results.size() - 1);
                if (result.hasTargets()) {
                    for (var target : result.getTargets()) {
                        double rotationOutput = headingController.calculate(((Math.atan(target.getBestCameraToTarget().getX()/Math.atan(target.getBestCameraToTarget().getY())))*180/Math.PI)+90, 0);
                        // System.out.println("X:" + target.getBestCameraToTarget().getX() + "| Y:" + target.getBestCameraToTarget().getY() + "|Z:" + target.getBestCameraToTarget().getZ());
                        drivetrain.setChassisSpeeds(new ChassisSpeeds(0, 0, rotationOutput));
                    } 
                }
            } catch (Exception e) {
                System.out.println("No target");
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isFinishedAiming;
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
