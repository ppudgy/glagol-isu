package ru.pudgy.isu;

public interface ValueFilter {
    ValueFilter correct(ValueFilter filter);
    double apply(double value);
}
