package ru.pudgy.isu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pudgy.isu.brain.Soul;
import ru.pudgy.isu.radio.NetRadio;
import ru.pudgy.isu.tool.TestRadioDeviceImple;
import ru.pudgy.isu.tool.TextStorageSence;
import ru.pudgy.text.TextProcessor;
import ru.pudgy.text.TextProcessorLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    private void doChat() throws IOException {
        TextProcessor processor = new TextProcessorLocal();
        Controller controller = new Controller();
        // создать модуль управления радио
        TestRadioDeviceImple rdi = new TestRadioDeviceImple();
        NetRadio radio = NetRadio.create(rdi, processor);
        // добавить радио в основную систему
        controller.add(radio);
        // из списка комманд скормить системе каждую комманду
        TextStorageSence speeech = new TextStorageSence(processor);
        controller.add(speeech);
        controller.addSoul(Soul.create(processor));
        try {
            AtomicBoolean run_flag = new AtomicBoolean(true);
            var thread = new Thread(() -> controller.runForever(run_flag));
            thread.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print('>');
            String line = br.readLine();
            while (line != null && !line.equalsIgnoreCase("q")) {

                speeech.emmit(line);
                System.out.print('>');
                line = br.readLine();
            }
            run_flag.set(false);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Application app = new Application();
        try {
            app.doChat();
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }
}
