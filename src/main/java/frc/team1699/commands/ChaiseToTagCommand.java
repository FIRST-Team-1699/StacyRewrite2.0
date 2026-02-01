package frc.team1699.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.SwerveSubsystem;
import frc.team1699.subsystems.VisionSubsystem;
import frc.team1699.subsystems.VisionSubsystem.TagWaypoint;

public class ChaiseToTagCommand extends Command {
    private SwerveSubsystem drivetrain;
    private VisionSubsystem vision;
    private static PIDController headingController = new PIDController(.15, 0, 0);
    private static PIDController movementController = new PIDController(.25, 0, 0);
    public ChaiseToTagCommand(SwerveSubsystem drivetrain, VisionSubsystem vision) {
        this.drivetrain=drivetrain;
        this.vision=vision;
        addRequirements(drivetrain, vision);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        if (!vision.getHasTag()) {
            return;
        }
        vision.setWaypoint(TagWaypoint.CAMERA_TUNE);
        double rotationOutput = headingController.calculate(vision.getYaw(), 0);
        double forwardOutput = movementController.calculate(vision.getX(), 2);
        drivetrain.setChassisSpeeds(new ChassisSpeeds(forwardOutput, 0, rotationOutput));
    }

    @Override
    public boolean isFinished() {
        return !vision.getHasTag();
    }

    @Override
    public void end(boolean isInterupted) {
        vision.setWaypoint(TagWaypoint.NONE);
    }
}
