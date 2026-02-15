package frc.team1699.commands;

import java.lang.annotation.Target;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.team1699.subsystems.SwerveSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import frc.team1699.subsystems.VisionSubsystem.TagWaypoint;
import frc.utils.BeamBreak;
import frc.utils.WaypointManagment.Waypoint;

public class AimToTagCommand extends Command {
    private SwerveSubsystem drivetrain;
    private PivotSubsystem pivot;
    private VisionSubsystem vision;

    private TagWaypoint waypoint;
    private InterpolatingDoubleTreeMap score =new InterpolatingDoubleTreeMap();

    private static PIDController headingController = new PIDController(.15, 0, 0);
    public AimToTagCommand(SwerveSubsystem drivetrain, PivotSubsystem pivot, VisionSubsystem vision, TagWaypoint waypoint) {
        this.drivetrain=drivetrain;
        this.pivot=pivot;
        this.vision=vision;
        this.waypoint=waypoint;
        score.put(3.5, 35.0);
        score.put(1.74, 40.0);
        score.put(1.0, 50.0);

        addRequirements(pivot,vision,drivetrain);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        // if (!vision.getHasTag()) {
        //     return;
        // }
        vision.setWaypoint(waypoint);
        RobotContainer.rotationalAlignOutput = headingController.calculate(vision.getYaw(), 0);
        drivetrain.setChassisSpeeds(new ChassisSpeeds(0, 0, RobotContainer.rotationalAlignOutput));
        pivot.setVisionPosition(score.get(vision.getDistanceToTag()));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterupted) {
        RobotContainer.rotationalAlignOutput=0;
        vision.disableStickyCam();
        if(isInterupted) {
            return;
        }
        // pivot.setPosition(PivotPositions.AIMING);
        // new ShootCommand(shoot, indexer).schedule();

        // vision.setWaypoint(TagWaypoint.NONE);
    }
}
