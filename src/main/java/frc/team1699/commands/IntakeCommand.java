package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.utils.BeamBreak;

public class IntakeCommand extends Command {
    private ShooterSubsystem shoot;
    private IndexerSubsystem indexer;
    public IntakeCommand(ShooterSubsystem shoot, IndexerSubsystem indexer) {
        this.shoot = shoot;
        this.indexer = indexer;
        addRequirements(shoot,indexer);
    }

    @Override
    public void initialize() {
        shoot.intake();
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
        shoot.stop();
        if(isInterupted) {
            indexer.stop();
        } else {
            indexer.outake();
            indexer.waitUntilLoaded()
                .andThen(indexer.runOnce(indexer::stop)).schedule();
        }
    }
}