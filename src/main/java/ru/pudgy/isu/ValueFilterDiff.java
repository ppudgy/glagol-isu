package ru.pudgy.isu;

public class ValueFilterDiff implements ValueFilter {
    private final double diff;

    public ValueFilterDiff(double v) {
        super();
        diff = v;
    }

    @Override
    public ValueFilter correct(ValueFilter filter) {
        return filter;
    }

    @Override
    public double apply(double value) {
        return value + diff;
    }
}
