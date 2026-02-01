package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.team1699.subsystems.PivotSubsystem;
import frc.team1699.subsystems.PivotSubsystem.PivotPositions;

public class GroundOutakeCommand extends Command {
    private IntakeSubsystem intake;
    private IndexerSubsystem indexer;
    private PivotSubsystem pivot;
    public GroundOutakeCommand(IntakeSubsystem intake, IndexerSubsystem indexer, PivotSubsystem pivot) {
        this.intake = intake;
        this.indexer = indexer;
        this.pivot = pivot;
        addRequirements(intake,indexer, pivot);
    }

    @Override
    public void initialize() {
        pivot.setPosition(PivotPositions.STORED);
        indexer.intake();
        intake.outake();
    }

    @Override
    public void execute() {}

    @Override 
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterupted) {
        indexer.stop();
        intake.stop();
    }
}
