package frc.team1699.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.utils.BeamBreak;

public class ShootCommand extends Command {
    private ShooterSubsystem shoot;
    private IndexerSubsystem indexer;
    public ShootCommand(ShooterSubsystem shoot, IndexerSubsystem indexer) {
        this.shoot = shoot;
        this.indexer = indexer;
        addRequirements(shoot,indexer);
    }

    @Override
    public void initialize() {
        shoot.outake();
        indexer.outake();
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return !BeamBreak.loaded().getAsBoolean();
    }

    @Override
    public void end(boolean isInterupted) {
        shoot.stop();
        indexer.stop();
    }
}