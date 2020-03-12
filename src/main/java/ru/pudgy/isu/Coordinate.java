package ru.pudgy.isu;

import java.util.UUID;

public interface Coordinate {
    UUID getId();
    MeasuredValue measure();
    void control( double value);
}
