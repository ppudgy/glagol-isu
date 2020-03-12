package ru.pudgy.isu.radio;

import ru.pudgy.isu.Coordinate;
import ru.pudgy.isu.MeasuredValue;

import java.util.UUID;

public class SoundCoordinate implements Coordinate {
    private UUID id;
    private NetRadioDevice device;

    public SoundCoordinate(UUID id, NetRadioDevice device) {
        this.id = id;
        this.device = device;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void control(double value) {
        device.setVolume((int)value);
    }

    @Override
    public MeasuredValue measure() {
        return new MeasuredValue(id, device.getVolume());
    }
}
