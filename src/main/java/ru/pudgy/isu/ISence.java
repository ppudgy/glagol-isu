package ru.pudgy.isu;


import java.util.List;

/**
 * Орган чуств
 *
 * TODO - реализовать обработчики информации органов чуств которые сами рождают информацию.
 * TODO - например на основе принятого звука или видео распознается текстовые команды.
 */
public interface ISence {
    List<IInformation> input();
}
