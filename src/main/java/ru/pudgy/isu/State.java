package ru.pudgy.isu;

import java.util.*;

public class State {
    private List<MeasuredValue> state;

    private State(List<MeasuredValue> state) {
        this.state = state;
    }

    private Optional<MeasuredValue> find(UUID id) {
        return state.stream().filter(s -> id == s.getId()).findFirst();
    }

    public StateModifier additionTo(final State target_state){
        StateModifier result = StateModifier.empty();
        for(MeasuredValue mv: state){
            Optional<MeasuredValue> o_target_mv = target_state.find(mv.getId());
            if(o_target_mv.isPresent() && mv.getValue() != o_target_mv.get().getValue() ){
                MeasuredValue target_mv = o_target_mv.get();
                result.modify(target_mv.getId(), new ValueFilterValue(target_mv.getValue()));
            }
        }
        return result;
    }
    public StateModifier additionFrom(State original_state){
        return original_state.additionTo(this);
    }
    public boolean isEmpty(){
        return state.isEmpty();
    }
    /**
     * добавляет только отсутствующие измерения
     * @param other
     * @return
     */
    public State union(State other){
        for(MeasuredValue mv : other.state){
            if(state.contains(mv)) continue;
            state.add(mv);
        }
        return this;
    }
    /**
     * добавляет отсутствующие измерения
     * Производит "сравнение" измерений иp другог
     * @param other
     * @return
     */
    public State plus(State other){
        for(MeasuredValue ns: other.state){   // TODO: very ugly - rewrite it
            Optional<MeasuredValue> f = find(ns.getId());
            f.ifPresentOrElse( value -> {
                        if(value.getValue() != ns.getValue())
                            value.setValue(ns.getValue());
                    } ,
                    () -> state.add(ns));
        }
        return this;
    }

    public void initFrom(State state) {
        this.state = state.state;
    }

    public static State empty() {
        return new State(new ArrayList<>());
    }

    public static State of(List<MeasuredValue> astate) {
        return new State(astate);
    }

    public Map<UUID, Double> getMap() {
        Map<UUID, Double> result = new HashMap<>();
        state.forEach(mv -> result.put(mv.getId(), mv.getValue()) );
        return result;
    }

    public void apply(StateModifier correct) {
        correct.stream().forEach(entry -> {
            var omv = state.stream().filter(mv -> mv.getId().equals(entry.getKey())).findFirst();

            omv.flatMap(mv -> {
                mv.setValue(entry.getValue().apply(mv.getValue()));
                return  Optional.of(mv);
            }).or(() -> {
                MeasuredValue newMV = new MeasuredValue(entry.getKey(), entry.getValue().apply(0));
                state.add( newMV);
                return Optional.of(newMV);
            });
        });
    }
}
