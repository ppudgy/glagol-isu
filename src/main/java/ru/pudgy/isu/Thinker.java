package ru.pudgy.isu;

public interface Thinker {
    void think(final IInformation info, long now);
    StateModifier update(final State state, long now);
}
