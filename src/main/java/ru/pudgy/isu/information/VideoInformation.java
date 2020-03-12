package ru.pudgy.isu.information;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.pudgy.isu.IInformation;
import ru.pudgy.oko.data.VideoData;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class VideoInformation implements IInformation {
    private final VideoData data;

    @Override
    public Optional<VideoData> getVideoData() {
        return Optional.of(data);
    }

    @Override
    public String toString() {
        return String.format("VideoInformation{%s}", data.toString());
    }
}
