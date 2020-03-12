package ru.pudgy.isu.radio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.pudgy.text.Instruction;
import ru.pudgy.isu.StateModifier;
import ru.pudgy.isu.ValueFilter;

import java.util.*;

public class CommandDatabase {
    List<CommandBlock> blocks = new ArrayList<>();

    public void add(Instruction inst, UUID id, ValueFilter filter) {
        blocks.add(CommandBlock.create(inst, id, filter));
    }

    public Optional<CommandBlock> find(Instruction inst) {
        return blocks.stream()
                .map(block -> {return new ImmutablePair<Double, CommandBlock>( block.compareCmd(inst), block );})
                .max(Comparator.comparingDouble(ImmutablePair::getLeft))
                .map(ImmutablePair::getRight);
    }

    public static CommandDatabase create() {
        return new CommandDatabase();
    }

    @Getter
    @AllArgsConstructor
    public static class CommandBlock {
        private final Instruction instruction;
        private final UUID id;
        private final ValueFilter filter;

        public static CommandBlock create(Instruction inst, UUID id, ValueFilter filter) {
            return new CommandBlock(inst, id, filter);
        }

        public StateModifier toStateModifier() {
            StateModifier result = StateModifier.empty();
            result.modify(id, filter);
            return result;
        }

        public Double compareCmd(Instruction cmd) {
            return instruction.compare(cmd);
        }
    }
}
