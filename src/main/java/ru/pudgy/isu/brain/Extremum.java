package ru.pudgy.isu.brain;

import java.util.UUID;

public class Extremum {
    private UUID id;
    private long x;
    private long y;

    public static Extremum create(UUID id, long point, long value) {
        Extremum result = new Extremum();
        result.id = id;
        result.x = point;
        result.y = value;
        return result;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public long getX() {
        return x;
    }
    public void setX(long x) {
        this.x = x;
    }
    public long getY() {
        return y;
    }
    public void setY(long y) {
        this.y = y;
    }
}
