package ru.pudgy.isu.brain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


/**
 * Времменой отрезок памяти с "интересной" точкой
 */
@Getter
@AllArgsConstructor
public class MemorySlice {
    private final List<UniversumPoint> slice;
    private final Extremum extremum;

    public static MemorySlice create(List<UniversumPoint> slice, Extremum extremum) {
        return new MemorySlice(slice, extremum);
    }
}
