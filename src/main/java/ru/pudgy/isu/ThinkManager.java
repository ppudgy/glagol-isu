package ru.pudgy.isu;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThinkManager {
    List<Thinker> thinkerList = new ArrayList<>();

    public void think(final IInformation info, long now) {
        thinkerList.forEach(thinker -> thinker.think(info, now));
    }

    public StateModifier update(final State state, long now) {
        StateModifier resultState = StateModifier.empty();
        for(Thinker thinker : thinkerList){
            StateModifier sm = thinker.update(state, now);
            resultState.plus(sm);
        }
        return resultState;
    }

    public void register(Thinker thinker) {
        thinkerList.add(thinker);
        log.debug(String.format("register thinker: %s", thinker.toString()));
    }

    public boolean empty() {
        return thinkerList.isEmpty();
    }
}
