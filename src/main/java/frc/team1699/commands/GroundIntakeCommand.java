package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;
import frc.utils.BeamBreak;

public class GroundIntakeCommand extends Command {
    private IntakeSubsystem intake;
    private IndexerSubsystem indexer;
    private PivotSubsystem pivot;
    public GroundIntakeCommand(IntakeSubsystem intake, IndexerSubsystem indexer, PivotSubsystem pivot) {
        this.intake = intake;
        this.indexer = indexer;
        this.pivot = pivot;
        addRequirements(intake,indexer, pivot);
    }

    @Override
    public void initialize() {
        pivot.setPosition(PivotPositions.INTAKE);
        intake.intake();
        indexer.outake();
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return BeamBreak.loaded().getAsBoolean();
    }

    @Override
    public void end(boolean isInterupted) {
        intake.stop();
        if(isInterupted) {
            indexer.stop();
        } else {
            indexer.outake();
            indexer.waitUntilLoaded()
                .andThen(indexer.runOnce(indexer::stop)).schedule();
        }
    }
}
