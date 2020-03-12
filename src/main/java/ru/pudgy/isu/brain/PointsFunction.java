package ru.pudgy.isu.brain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 *   Одномерная дискретная функция по обобщенной координате
 *    представленна в виде массива элементарных значений для снижения влияния разброса в памяти объектных значений
 *    основное назначение анализ на экстремумы
 */


public class PointsFunction {
    private final UUID id;          // идентификатор координаты
    private final long[] points;    // список точек функции x - четные, у - нечетные
    private final boolean valid;
    private final int length;
    private PointsFunction(final UUID id, final long[] points) {
        this.id = id;
        this.points = points;
        this.length = points.length/2;
        this.valid = checkIsPointFunction();
    }

    public UUID getId() {
        return id;
    }
    public static PointsFunction create(final UUID id, final long[] data) {
        return new PointsFunction(id, data);
    }
    public List<Extremum> findExtremum(){
        List<Extremum> result = new ArrayList<>();
        if (valid) {
            for (int i = 2; i < length - 1; i++) {
                if (checkIsPointExtremum(i)){
                    result.add(Extremum.create(id, getArgument(i), getValue(i)));
                }
            }
        }
        return result;
    }
    /**
     * проверка строгого возрастания аргумента
     * @return
     */
    private boolean checkIsPointFunction() {
        if(points == null) return false;
        if(points.length == 0 /*&& points.length % 2 != 0 */) return false;
        // check argument is upriseing
        long prev = getArgument(0);
        for(int i = 1; i < length; i++){
            var cur = getArgument(i);
            if( cur <= prev) return false;  // аргумент должен быть строго возрастающей функцией
            prev = cur;
        }
        return true;
    }
    private boolean checkIsPointExtremum(int i) {
        return getValue(i-2) < getValue(i-1) && getValue(i-1) < getValue(i) && getValue(i) >= getValue(i+1);
    }
    private long getValue(int i) {
        return points[2*i+1];
    }
    private long getArgument(int i) {
        return points[2*i];
    }
}
