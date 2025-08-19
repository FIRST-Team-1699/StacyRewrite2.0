package frc.utils;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.utils.BeamBreak;

public class BeamBreak {
    private static DigitalInput input = new DigitalInput(2);

    public static BooleanSupplier loaded() {
        return () -> !input.get();
    }
}