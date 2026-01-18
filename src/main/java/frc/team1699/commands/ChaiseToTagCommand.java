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

public class ChaiseToTagCommand extends Command {
    private boolean isFinishedAiming = false;
    private SwerveSubsystem drivetrain;
    private static PIDController headingController = new PIDController(.15, 0, 0);
    private static PIDController movementController = new PIDController(.25, 0, 0);
    public ChaiseToTagCommand(SwerveSubsystem drivetrain) {
        this.drivetrain=drivetrain;
        addRequirements(drivetrain);
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
                        double rotationOutput = headingController.calculate(target.yaw, 0);
                        System.out.println("Output works :3");
                        double forwardOutput = movementController.calculate(target.getBestCameraToTarget().getX(), 2);
                        drivetrain.setChassisSpeeds(new ChassisSpeeds(forwardOutput, 0, rotationOutput));

                //         double degrees =(((Math.atan(Math.abs(target.getBestCameraToTarget().getX())/Math.abs(target.getBestCameraToTarget().getY())))*180/Math.PI));
                //         System.out.println("Actual Yaw:" + target.yaw + "\nCalc Yaw:" + (degrees <0.0 ? Math.abs(degrees)+180 : degrees )  );
                    } 
                }
            } catch (Exception e) {
                System.out.println("No target");
            }
        }
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
    }
}
