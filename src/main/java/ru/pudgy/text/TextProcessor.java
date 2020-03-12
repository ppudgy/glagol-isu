package ru.pudgy.text;

import ru.pudgy.text.Instruction;

import java.util.List;

public interface TextProcessor {
    List<Instruction> processText(String str);
}
