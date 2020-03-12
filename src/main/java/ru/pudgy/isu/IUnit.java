package ru.pudgy.isu;

import java.util.List;

public interface IUnit {
    String getName();
    List<Thinker> getThinkers();
    List<Coordinate> getIndependentCoordinate();
}
