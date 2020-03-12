package ru.pudgy.isu.brain;



import ru.pudgy.text.TextProcessor;
import ru.pudgy.isu.*;
import ru.pudgy.isu.radio.CommandDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


public class SoulThinker implements Thinker {
    private static Logger logger = LoggerFactory.getLogger(SoulThinker.class);
    private CommandDatabase commandDatabase;
    private StateModifier acum = StateModifier.empty();

    private SoulThinker() {
    }

    @Override
    public void think(IInformation info, long now) {
        logger.debug(info.toString());
        // Если info - текстовая комманда.
        StateModifier result = info.getTextCommand()
                    .flatMap(cmd -> commandDatabase.find(cmd))
                    .map(blk -> blk.toStateModifier())
                    .orElse(StateModifier.empty());
        acum.plus(result);
    }

    @Override
    public StateModifier update(State state, long now) {
        StateModifier result = StateModifier.empty();
        result.plus(acum);
        acum.clear();
        return result;
    }

    public static SoulThinker create(UUID joyid,  TextProcessor processor){
        SoulThinker result = new SoulThinker();
        CommandDatabase db = CommandDatabase.create();

        // положительные эмоции
        db.add(processor.processText("Молодец").get(0), joyid, new ValueFilterValue(1));
        db.add(processor.processText("Правильно").get(0), joyid, new ValueFilterValue(0));
        db.add(processor.processText("Да").get(0), joyid, new ValueFilterDiff(1));

        // отрицательные эмоции
        db.add(processor.processText("Тупица").get(0), joyid, new ValueFilterDiff(-1));
        db.add(processor.processText("Нет").get(0), joyid, new ValueFilterDiff(-1));

        result.commandDatabase = db;

        return result;
    }

}
