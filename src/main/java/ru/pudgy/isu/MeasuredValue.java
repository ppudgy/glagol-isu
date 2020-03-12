package ru.pudgy.isu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

/**
 *  измеренное значение датчика в системе
 */
@Data
@AllArgsConstructor
public class MeasuredValue {
    private final UUID id;
    private double value; // TODO : mast be final

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasuredValue that = (MeasuredValue) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
