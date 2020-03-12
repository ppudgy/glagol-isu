package ru.pudgy.isu;

import lombok.extern.slf4j.Slf4j;
import ru.pudgy.isu.brain.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class Controller {
    private State lastState;
    private State lastSoulState;
    private CoordinateManager coordinateManager = new CoordinateManager();
    private SenceManager senceManager = new SenceManager();
    private ThinkManager thinkManager = new ThinkManager();
    private CoordinateManager selfCoordinateManager = new CoordinateManager();
    private ThinkManager selfManager = new ThinkManager();
    private List<IUnit> units = new ArrayList<>();
    private Memory memory = new Memory();


    public void add(IUnit unit) {
        if(unit == null) {
            log.error("try to add null IUnit");
        }
        log.debug("add unit: %s", unit.getName());
        unit.getIndependentCoordinate().forEach(coordinateManager::register);
        unit.getThinkers().forEach(thinkManager::register);
        units.add(unit);
    }
    public void add(ISence sence){
        log.debug(String.format("add sence: %s", sence.toString()));
        senceManager.add(sence);
    }
    public void addSoul(IUnit unit) {
        log.debug("add soul units");
        unit.getIndependentCoordinate().forEach(selfCoordinateManager::register);
        unit.getThinkers().forEach(selfManager::register);
        units.add(unit);
    }
    /**
     * Бесконечный цикл работы
     */
    public void runForever(AtomicBoolean flag){
        if(thinkManager.empty()){
            log.error("Do not run forever - thinkManaget is empty");
            return;
        }
        if(senceManager.empty()){
            log.error("Do not run forever - senceManager is empty");
            return;
        }
        if(coordinateManager.empty()){
            log.error("Do not run forever - coordinateManager is empty");
            return;
        }
        if(selfCoordinateManager.empty()){
            log.error("Do not run forever - Soul is empty");
            return;
        }

        log.debug("run forever");
        lastState = State.empty();
        lastSoulState = State.empty();
        while (flag.get()){
            long now = System.currentTimeMillis();
            List<IInformation> infs = senceManager.inputAll();
            long input_t = System.currentTimeMillis();
             // Осмысливаем информацию с органов чуств
            for(IInformation info : infs){
                thinkManager.think(info, now);
                selfManager.think(info, now);
            }
            long think_t = System.currentTimeMillis();
            /* Вычисляем команды исполнительным механизмам
            *   в lastState находятся наши расчетные значения координат
            *   в state  находятся актуальные значения координат (они могут измениться под действием окружающей среды)
            *   в sm находится изменения в координатах которые мы думаем необходимо произвести
            *   в correct разнища между расчетными значениями координат и актуальными.
            */
            State state = coordinateManager.measureNow();
            StateModifier sm = thinkManager.update(state, now);
            StateModifier correct = state.additionTo(lastState);
            correct.plus(sm);
            coordinateManager.apply(correct);
            lastState.apply(correct);


            {
                State soul_state = selfCoordinateManager.measureNow();   // TODO сделать работу с "душой" изящной
                StateModifier soul_sm = selfManager.update(soul_state, now);
                StateModifier soul_correct = soul_state.additionTo(lastSoulState);
                soul_correct.plus(soul_sm);
                selfCoordinateManager.apply(soul_correct);
                lastSoulState.apply(soul_correct);
            }
            long apply_t = System.currentTimeMillis();
            /* Формирование памяти */
            memory.addPoint(now, lastState.getMap(), correct.getMap(), lastSoulState.getMap(), infs);
            // TODO добавить анализ памяти, извлечение из него действий, добавление действий в вычислитель  -> вынести в thinker
            analyzeMemory();
            /*
             *  Формирование темпа существования
             */


            long memory_t= System.currentTimeMillis();
            if (memory_t - now > 0)
                log.debug("times: input {}, think {}, apply {}, memory {} all {}", input_t - now, think_t - input_t, apply_t - think_t, memory_t - apply_t, memory_t - now);

            long time_to_sleep = 250 - Math.abs(memory_t - now);  // наш пульс 4 герца ))))))
            if(time_to_sleep > 10) {
                try {
                    Thread.sleep(time_to_sleep);
                } catch (InterruptedException e) {
                    log.error("", e);//e.printStackTrace();
                }
            }
        }
    }


    /**
     * найти в памяти шаблоны повелдения приводящие к улучшению "показателей системы"
     * и использовать их для создания условных рефлексов.
     */
    private void analyzeMemory() {
        var slices = selfCoordinateManager.coordinates.keySet().stream()
                .map(key-> memory.makeSlice(key))
                .flatMap(func ->  func.findExtremum().stream())
                .map(extremum -> memory.createSlice(extremum))
                .filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());

        if(slices.size() > 0){
            int i = 0;
        }

    }
}
