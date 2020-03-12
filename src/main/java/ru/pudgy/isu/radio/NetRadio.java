package ru.pudgy.isu.radio;

import ru.pudgy.text.TextProcessor;
import ru.pudgy.isu.*;

import java.util.*;

/**
 * Модуль управления радио (или теле) устройством
 */
public class NetRadio implements IUnit {
    private NetRadioDevice device;

    private UUID cId;
    private UUID sId;
    private UUID ooId;

    private ChannelCoordinate cc;
    private SoundCoordinate sc;
    private OnOffCoordinate oc;

    private TextProcessor processor;
    private NetRadioThinker thinker;

    private NetRadio(NetRadioDevice adevice, TextProcessor processor) {
        this.device = adevice;
        this.processor = processor;
    }

    @Override
    public String getName() {
        return "Net radio";
    }

    @Override
    public List<Coordinate> getIndependentCoordinate() {
        return Arrays.asList(cc, sc, oc);
    }

    @Override
    public List<Thinker> getThinkers() {
        List<Thinker> result = new ArrayList<>();
        result.add(thinker);
        return result;
    }

    public static NetRadio create(NetRadioDevice device, TextProcessor processor){
        NetRadio result = new NetRadio(device, processor);

        result.cId = UUID.randomUUID();
        result.sId = UUID.randomUUID();
        result.ooId = UUID.randomUUID();

        result.cc = new ChannelCoordinate(result.cId, device);
        result.sc = new SoundCoordinate(result.sId, device);
        result.oc = new OnOffCoordinate(result.ooId, device);

        result.thinker = NetRadioThinker.create(result.ooId, result.sId, result.cId, device, processor);
        return result;
    }
}
