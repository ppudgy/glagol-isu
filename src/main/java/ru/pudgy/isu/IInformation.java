package ru.pudgy.isu;



import ru.pudgy.oko.data.VideoData;
import ru.pudgy.text.Instruction;


import java.util.Optional;

/**
 * единица информации полученная из органа чуств
 */
public interface IInformation {
    default Optional<Instruction> getTextCommand() {
        return Optional.empty();
    }
    default Optional<VideoData> getVideoData() {
        return Optional.empty();
    }
}
