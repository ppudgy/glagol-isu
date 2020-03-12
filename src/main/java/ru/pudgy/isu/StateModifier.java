package ru.pudgy.isu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class StateModifier {
    private Map<UUID, ValueFilter> map = new HashMap<>();

    public static StateModifier empty() {
        return new StateModifier();
    }

    public void modify(UUID aid, ValueFilter filter) {
        ValueFilter f = map.get(aid);
        if(f == null) {
            map.put(aid, filter);
        } else {
            map.put(aid, f.correct(filter));
        }
    }

    public void plus(StateModifier state) {
        for(Map.Entry<UUID, ValueFilter> entry : state.map.entrySet()){
            modify(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        map.clear();
    }

    public Stream<Map.Entry<UUID, ValueFilter>> stream(){
        return map.entrySet().stream();
    }

    public Map<UUID, ValueFilter> getMap() {
        return map;
    }
}
