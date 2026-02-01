package frc.team1699.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.team1699.subsystems.IndexerSubsystem;
import frc.team1699.subsystems.ShooterSubsystem;
import frc.utils.BeamBreak;

public class ShootCommand extends Command {
    private ShooterSubsystem shoot;
    private IndexerSubsystem indexer;
    private Timer time;
    public ShootCommand(ShooterSubsystem shoot, IndexerSubsystem indexer) {
        this.shoot = shoot;
        this.indexer = indexer;
        this.time=new Timer();
        addRequirements(shoot,indexer);
    }

    @Override
    public void initialize() {
        shoot.outake();
        time.start();
    }

    @Override
    public void execute() {
        if(time.get()> .10) {
            indexer.outake();
            time.stop();
            time.reset();
        }
    }

    @Override
    public boolean isFinished() {
        // return !BeamBreak.loaded().getAsBoolean();
        return false;
    }

    @Override
    public void end(boolean isInterupted) {
        shoot.stop();
        indexer.stop();
        time.reset();
    }
}