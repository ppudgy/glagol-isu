package ru.pudgy.isu.tool;

import lombok.extern.slf4j.Slf4j;
import ru.pudgy.isu.radio.NetRadioDevice;

@Slf4j
public class TestRadioDeviceImple implements NetRadioDevice{
    private boolean onOff;
    private int volume;
    private int channel;

    public TestRadioDeviceImple() {
        this.onOff = false;
        this.volume = 0;
        this.channel = 0;
    }
    @Override
    public void setOnOff(boolean b) {
        log.debug(String.format("-----------------------------------------------OnOff(%b -> %b)", onOff, b));
        onOff = b;
    }
    @Override
    public boolean getOnOff() {
//        log.debug(String.format("getOnOff: %b", onOff));
        return onOff;
    }
    @Override
    public void setVolume(int value) {
        log.debug(String.format("-----------------------------------------------setVolum(%d -> %d)", volume, value));
        volume = value;
    }
    @Override
    public int getVolume() {
//        log.debug(String.format("getVolum: %d", volume));
        return volume;
    }
    @Override
    public void setChannel(int ch) {
        log.debug(String.format("-----------------------------------------------setChannel(%d -> %d)", channel, ch));
        channel = ch;
    }
    @Override
    public int getChannel() {
//        log.debug(String.format("getChannel: %d)", channel));
        return channel;
    }
}
