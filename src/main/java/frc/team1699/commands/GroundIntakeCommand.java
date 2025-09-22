package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;
import frc.utils.BeamBreak;

public class GroundIntakeCommand extends Command {
    private IntakeSubsystem intake;
    private IndexerSubsystem indexer;
    public GroundIntakeCommand(IntakeSubsystem intake, IndexerSubsystem indexer) {
        this.intake = intake;
        this.indexer = indexer;
        addRequirements(intake,indexer);
    }

    @Override
    public void initialize() {
        intake.intake();
        indexer.intake();
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
