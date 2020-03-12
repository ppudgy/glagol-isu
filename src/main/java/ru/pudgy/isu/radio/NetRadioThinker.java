package ru.pudgy.isu.radio;

import lombok.extern.slf4j.Slf4j;
import ru.pudgy.text.TextProcessor;
import ru.pudgy.isu.*;

import java.util.UUID;

/**
 *       Элементарное управление радио
 *
 */
@Slf4j
public class NetRadioThinker implements Thinker {
    private CommandDatabase commandDatabase;
    private StateModifier acum = StateModifier.empty();

    private NetRadioThinker(){}

    @Override
    public void think(IInformation info,  long now) {
        log.debug(info.toString());
        StateModifier
            result = info.getTextCommand()
                    .map( cmd -> commandDatabase.find(cmd)
                                                .map(blk -> blk.toStateModifier())
                                                .orElse(StateModifier.empty()))
                    .orElse(StateModifier.empty());
        acum.plus(result);
    }

    @Override
    public StateModifier update(final State state,  long now) {
        StateModifier result = StateModifier.empty();
        result.plus(acum);
        acum.clear();
        return result;
    }




    public static NetRadioThinker create(UUID switchID, UUID volumeID, UUID channelID, NetRadioDevice device, TextProcessor processor){
        NetRadioThinker result = new NetRadioThinker();
        CommandDatabase db = CommandDatabase.create();

        db.add(processor.processText("Включить радио").get(0), switchID, new ValueFilterValue(1));
//        db.add(processor.processText("Включить музыку").get(0), switchID, new ValueFilterValue(1));
//        db.add(processor.processText("Включить эхо москвы").get(0), volumeID, new ValueFilterDiff(1));
        db.add(processor.processText("Выключить радио").get(0), switchID, new ValueFilterValue(0));
        db.add(processor.processText("Громче").get(0), volumeID, new ValueFilterDiff(1));
        db.add(processor.processText("Тише").get(0), volumeID, new ValueFilterDiff(-1));
        db.add(processor.processText("Первый канал").get(0), channelID, new ValueFilterValue(1));
        db.add(processor.processText("Второй канал").get(0), channelID, new ValueFilterValue(2));
        db.add(processor.processText("Третий канал").get(0), channelID, new ValueFilterValue(3));
        db.add(processor.processText("Четвертый канал").get(0), channelID, new ValueFilterValue(4));
        db.add(processor.processText("Пятый канал").get(0), channelID, new ValueFilterValue(5));
        db.add(processor.processText("Шестой канал").get(0), channelID, new ValueFilterValue(6));
        db.add(processor.processText("Седьмой канал").get(0), channelID, new ValueFilterValue(7));
        db.add(processor.processText("Восьмой канал").get(0), channelID, new ValueFilterValue(8));
        db.add(processor.processText("Девятый канал").get(0), channelID, new ValueFilterValue(9));
        db.add(processor.processText("Десятый канал").get(0), channelID, new ValueFilterValue(10));
        db.add(processor.processText("Двадцать пятый канал").get(0), channelID, new ValueFilterValue(25));

        db.add(processor.processText("Следующий канал").get(0), channelID, new ValueFilterDiff(1));
        db.add(processor.processText("Предыдущий канал").get(0), channelID, new ValueFilterDiff(-1));

        result.commandDatabase = db;
        return result;
    }


}








