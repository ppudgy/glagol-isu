package ru.pudgy.isu.brain;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.pudgy.isu.IInformation;
import ru.pudgy.isu.ValueFilter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 *  DTO
 * Елемент памяти, элементарное событие
 *   имеет зафиксированную точку времени , млсек от EPOX
 *   значение обобщенных координат системы
 *   значения комманд для управляющих устройств, выработанные системой
 *   состояние "души"
 *   список информации с органов чуств
 */
@Data
@AllArgsConstructor
public class UniversumPoint {
    private final long time;                          // Время
    private final Map<UUID, Double> coords;           // конфигурация системы
    private final Map<UUID, ValueFilter> modifiers;   // комманды системе
    private final Map<UUID, Double> souls;            // параметры системы (душа)
    private final List<IInformation> infs;            // информация с органнов чуств

    public static UniversumPoint create(long time, Map<UUID, Double> coords, Map<UUID, ValueFilter> modifiers, Map<UUID, Double> values, List<IInformation> infs){
        return new UniversumPoint(time, coords, modifiers, values, infs);
    }
}
