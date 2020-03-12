package ru.pudgy.isu.brain;

import ru.pudgy.isu.Coordinate;
import ru.pudgy.isu.MeasuredValue;

import java.util.UUID;

public class SoulCoordinate implements Coordinate {
    private final UUID id;
    private double value;

    SoulCoordinate(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
    @Override
    public MeasuredValue measure() {
        return new MeasuredValue(id, value);
    }
    @Override
    public void control(double avalue) {
        value = avalue;
    }
}
