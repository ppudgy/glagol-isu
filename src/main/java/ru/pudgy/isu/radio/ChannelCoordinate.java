package ru.pudgy.isu.radio;

import ru.pudgy.isu.Coordinate;
import ru.pudgy.isu.MeasuredValue;

import java.util.UUID;

public class ChannelCoordinate implements Coordinate {
    private UUID id;
    private NetRadioDevice device;

    public ChannelCoordinate(UUID id, NetRadioDevice device) {
        this.id = id;
        this.device = device;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void control(double value) {
        int ch = (int) value;
        device.setChannel(ch);
    }

    @Override
    public MeasuredValue measure() {
        return new MeasuredValue(id, device.getChannel());
    }
}
