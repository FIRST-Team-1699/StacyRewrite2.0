package frc.team1699.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.SwerveSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
<<<<<<< Updated upstream
import frc.robot.Constants.PhotonConstants;
=======
import frc.team1699.subsystems.VisionSubsystem.TagWaypoint;
import frc.utils.BeamBreak;
>>>>>>> Stashed changes

public class AimToTagCommand extends Command {
    double distance;

    PivotSubsystem pivot;
    SwerveSubsystem drivetrain;

    PIDController headingController;

    public AimToTagCommand (PivotSubsystem pivot, SwerveSubsystem drivetrain) {
        this.pivot = pivot;
        this.drivetrain = drivetrain;
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        this.headingController = new PIDController(.15, 0, 0);
    }

    @Override
    public void execute() {
<<<<<<< Updated upstream
        var results=PhotonConstants.cam.getAllUnreadResults();
        if(!results.isEmpty()) {
            var result = results.get(results.size() - 1);
            if (result.hasTargets()) {
                var temp = result.targets.get(0);
                double rotationalOutput = temp.pitch;
                drivetrain.setChassisSpeeds(new ChassisSpeeds(0, 0, rotationalOutput));
            }
        }
=======
        // if (!vision.getHasTag()) {
        //     return;
        // }
        vision.setWaypoint(TagWaypoint.BASKET_PRACTICE);
        double rotationOutput = headingController.calculate(vision.getYaw(), 0);
        drivetrain.setChassisSpeeds(new ChassisSpeeds(0, 0, rotationOutput));
>>>>>>> Stashed changes
    }

    @Override
    public boolean isFinished() {
        // return pivot.isInTolerance().getAsBoolean();
        return true;
    }

    @Override
    public void end(boolean isInterupted) {
<<<<<<< Updated upstream
        System.out.println("Cam to tag: " + distance);
=======
        if(isInterupted) {
            return;
        }
        // pivot.setPosition(PivotPositions.AIMING);
        // new ShootCommand(shoot, indexer).schedule();

        vision.setWaypoint(TagWaypoint.NONE);
>>>>>>> Stashed changes
    }
}
