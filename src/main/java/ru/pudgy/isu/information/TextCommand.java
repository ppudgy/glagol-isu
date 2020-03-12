package ru.pudgy.isu.information;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ru.pudgy.text.Instruction;
import ru.pudgy.isu.IInformation;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class TextCommand implements IInformation {
    private final String text;
    private final Instruction instruction;

    @Override
    public Optional<Instruction> getTextCommand() {
        return Optional.of(instruction);
    }
    public static TextCommand create(@NonNull Instruction inst) {
        return new TextCommand(inst.toString(), inst);
    }
    @Override
    public String toString() {
        return String.format("TextCommand{%s}", text);
    }
}
