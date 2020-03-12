package ru.pudgy.isu.tool;

import ru.pudgy.text.Instruction;
import ru.pudgy.text.TextProcessor;
import ru.pudgy.isu.IInformation;
import ru.pudgy.isu.ISence;
import ru.pudgy.isu.information.TextCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class TextStorageSence implements ISence {

    Queue<String> commandQueue = new ArrayBlockingQueue<String>(100);
    TextProcessor processor ;


    public TextStorageSence(TextProcessor processor) {
        this.processor = processor;
    }

    @Override
    public List<IInformation> input() {

        List<IInformation> result = new ArrayList<>();


        String cmd = commandQueue.poll();
        if(cmd!= null){
            List<Instruction> il = processor.processText(cmd);
            result.addAll(il.stream().map(TextCommand::create).collect(Collectors.toList()));
        }
        return result;
    }


    public void emmit(String c) {
        commandQueue.offer(c);
    }


    @Override
    public String toString() {
        return "TextStorageSence{}";
    }
}
