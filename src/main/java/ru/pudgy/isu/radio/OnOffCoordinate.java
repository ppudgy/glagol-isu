package ru.pudgy.isu.radio;

import ru.pudgy.isu.Coordinate;
import ru.pudgy.isu.MeasuredValue;

import java.util.UUID;

public class OnOffCoordinate implements Coordinate {
    private UUID id;
    private NetRadioDevice device;

    public OnOffCoordinate(UUID id, NetRadioDevice device) {
        this.id = id;
        this.device = device;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void control(double value) {
        device.setOnOff( Math.abs(value) > 0.5 );
    }

    @Override
    public MeasuredValue measure() {
        return new MeasuredValue(id, device.getOnOff() ? 1.0 : 0.0);
    }
}
