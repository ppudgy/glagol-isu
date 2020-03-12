package ru.pudgy.isu.brain;

import ru.pudgy.text.TextProcessor;
import ru.pudgy.isu.Coordinate;
import ru.pudgy.isu.IUnit;
import ru.pudgy.isu.Thinker;

import java.util.*;

public class Soul implements IUnit {
    private SoulCoordinate joyness;  // радость
    //private SoulCoordinate tireness;  // усталость
    private final SoulThinker soulThinker;

    private  Soul(SoulCoordinate joyness, SoulThinker soulThinker) {
        this.joyness = joyness;
        this.soulThinker = soulThinker;
    }

    public static Soul create( TextProcessor processor) {
        UUID jid = UUID.randomUUID();
        SoulCoordinate joyness = new SoulCoordinate(jid);
        SoulThinker thinker = SoulThinker.create(jid, processor);
        return new Soul(joyness, thinker);
    }

    @Override
    public String getName() {
        return "Soul";
    }

    @Override
    public List<Thinker> getThinkers() {
        return Collections.singletonList(soulThinker);
    }

    @Override
    public List<Coordinate> getIndependentCoordinate() {
        return Collections.singletonList(joyness);
    }
}
