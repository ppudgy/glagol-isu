package ru.pudgy.isu.radio;

import ru.pudgy.isu.MeasuredValue;

public interface NetRadioDevice {
    void setOnOff(boolean b);
    boolean getOnOff();

    void setVolume(int value);
    int getVolume();

    void setChannel(int ch);
    int getChannel();

}
