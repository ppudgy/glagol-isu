package ru.pudgy.isu.brain;

import lombok.AllArgsConstructor;
import ru.pudgy.isu.IInformation;
import ru.pudgy.isu.ValueFilter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Память.
 *  состоит из множества элементарных событий
 */
public class Memory {
    private List<UniversumPoint> universum;

    public Memory() {
        this.universum = new ArrayList<>();
    }

    public void addPoint(UniversumPoint universum_point){
        universum.add(universum_point);
    }
    public void addPoint(long time, Map<UUID, Double> coords, Map<UUID, ValueFilter> modifiers, Map<UUID, Double> values) {
        addPoint(time, coords, modifiers, values, Collections.EMPTY_LIST);
    }
    public void addPoint(long time, Map<UUID, Double> coords, Map<UUID, ValueFilter> modifiers, Map<UUID, Double> values, List<IInformation> infs) {
        addPoint(UniversumPoint.create(time, coords, modifiers, values, infs));
    }

    // сформировать из universum, по указанным координатам, PointsFunction
    public PointsFunction makeSlice(UUID param) {
        List<Point> points = universum.stream()
                .map( un -> {
                    long  time = un.getTime();
                    double value = un.getSouls().getOrDefault(param, Double.MIN_VALUE);
                    return new Point(time, Math.round(value * 1000));
                })
                .sorted(Comparator.comparingLong(a -> a.time))
                .collect(Collectors.toList());
        return Point.toPointFunction(param, points);
    }

    /**
     * Создание копии участка "памяти" для указанного экстремума параметров
     *
     * @param extremum
     * @return
     */
    public Optional<MemorySlice> createSlice(Extremum extremum) {
        // ищем от указанной точки в "прошлое" первый непрерывный участок с непустыми входнами данными
        Optional<MemorySlice> result = Optional.empty();
        return universum.stream()
                .filter(un -> un.getTime() < extremum.getX())
                .filter(un -> un.getInfs().size() > 0)
                .max(Comparator.comparingLong(UniversumPoint::getTime))
                .map( point -> {
                        // копируем от естремума до начала участка все данные в резултат
                        var slice = universum.stream()
                                .filter(un -> un.getTime() <= extremum.getX())
                                .filter(un -> un.getTime() >= point.getTime())
                                .collect(Collectors.toList());
                        return  MemorySlice.create(slice, extremum);
                });
    }

    // helper class
    @AllArgsConstructor
    private static class Point {
        private final long time;
        private final long value;

        static PointsFunction toPointFunction(UUID id, List<Point> points) {
            int p_size = points.size();
            long[] res = new long[p_size * 2];
            int i = 0;
            for (Point p : points) {
                res[2*i] = p.time;
                res[2*i+1] = p.value;
                i++;
            }
            return PointsFunction.create(id, res);
        }
    }
}
