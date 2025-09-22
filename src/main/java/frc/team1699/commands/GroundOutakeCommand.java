package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.IntakeSubsystem;

public class GroundOutakeCommand extends Command {
    IntakeSubsystem intake;
    IndexerSubsystem indexer;
    public GroundOutakeCommand(IntakeSubsystem intake, IndexerSubsystem indexer) {
        this.intake = intake;
        this.indexer = indexer;
        addRequirements(intake,indexer);
    }

    @Override
    public void initialize() {
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
