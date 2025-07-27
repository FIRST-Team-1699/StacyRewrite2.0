package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;

public class AimToTagCommand extends Command {
    private PivotSubsystem pivot;

    public AimToTagCommand(PivotSubsystem pivot) {
        this.pivot = pivot;
        addRequirements(pivot);
    }

    @Override
    public void initialize() {
        // TODO: USE LIMELIGHT, INTERPOLATING LOGIC AND SET AIMING VALUE
    }

    @Override
    public void execute() {
        pivot.setPosition(PivotPositions.AIMING);
        pivot.waitUntilTolerance();
    }

    @Override
    public boolean isFinished() {
        return pivot.isInTolerance().getAsBoolean();
    }

    @Override
    public void end(boolean isInterupted) {
        PivotPositions.AIMING.value = 0;
    }
}
