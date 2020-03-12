package ru.pudgy.isu;

public class ValueFilterValue implements ValueFilter {
    private final double value;

    public ValueFilterValue(double v) {
        super();
        value = v;
    }

    @Override
    public ValueFilter correct(ValueFilter filter) {
        return new ValueFilterValue( filter.apply(value) );
    }

    @Override
    public double apply(double avalue) {
        return value;
    }
}
