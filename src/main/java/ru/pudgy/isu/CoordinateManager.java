package ru.pudgy.isu;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CoordinateManager {
    Map<UUID, Coordinate> coordinates = new HashMap<>();

    public void register(Coordinate coordinate){
        if(!coordinates.containsKey(coordinate.getId())){
            coordinates.put(coordinate.getId(), coordinate);
            log.debug(String.format("register coordinate: %s", coordinate.toString()));
        }
    }

    public State measureNow(){
        List<MeasuredValue> res = coordinates.entrySet().stream().map(entry -> entry.getValue().measure()).collect(Collectors.toList());
        return State.of(res);
    }

    public boolean empty() {
        return coordinates.isEmpty();
    }

    public void apply(final StateModifier correct) {
        correct.stream().forEach(entry -> {
            coordinates.computeIfPresent(entry.getKey(), (id, coordinate) -> {
                    coordinate.control( entry.getValue().apply(coordinate.measure().getValue())  );
                    return coordinate;
            });
        });
    }
}
