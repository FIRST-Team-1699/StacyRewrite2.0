package frc.team1699.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.LimelightConstants;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import frc.utils.LimelightHelpers;
import frc.utils.LimelightHelpers.RawFiducial;

public class PivotToTagCommand extends Command {
    RawFiducial[] fiducials;
    double distance;

    PivotSubsystem pivot;

    public PivotToTagCommand (PivotSubsystem pivot) {
        this.pivot = new PivotSubsystem();
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        // Commented out code that adds functionality w/ pivot. Using to tune angle v. distance
        if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1) {
            fiducials = LimelightHelpers.getRawFiducials("");
            for (RawFiducial fiducial : fiducials) {
                // Tooo lazy to actually measure position (I'll do this pre-season with Carrrrrlllll)
                distance = fiducial.distToCamera;
            }
            // PivotPositions.AIMING.value = LimelightConstants.pivotToTag.get(distance);
            // if (PivotPositions.AIMING.value <= 0 ) {
            //     System.out.println("InterpolatingDoubleTreeMap Error: Pivot defaults to 5");
            //     PivotPositions.AIMING.value = 5;
            //     pivot.setPosition(PivotPositions.AIMING);
            // } else {
            //     pivot.setPosition(PivotPositions.AIMING);
            // }
        } else {
            System.out.println("Error: Limelight isn't visible or cannot see target");
        }
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        // return pivot.isInTolerance().getAsBoolean();
        return true;
    }

    @Override
    public void end(boolean isInterupted) {
        System.out.println("Cam to tag: " + distance);
    }
}
