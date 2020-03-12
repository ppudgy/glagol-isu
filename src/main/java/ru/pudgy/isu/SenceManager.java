package ru.pudgy.isu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SenceManager {
    private List<ISence> senceList  = new ArrayList<>();
    public void add(ISence sence) {
        senceList.add(sence);
    }

    public List<IInformation> inputAll() {
        return senceList.stream().flatMap(sence -> sence.input().stream()).collect(Collectors.toList());
    }

    public boolean empty() {
        return senceList.isEmpty();
    }
}
