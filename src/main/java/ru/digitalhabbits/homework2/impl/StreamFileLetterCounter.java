package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.LetterCounter;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class StreamFileLetterCounter implements FileLetterCounter {

    LetterCounter counter = new LetterCounterImpl();
    LetterCountMerger merger = new LetterCountMergerImpl();

    @Override
    public Map<Character, Long> count(File input) {

        Stream<String> lines = (new FileReaderImpl()).readLines(input);

        BinaryOperator<Map<Character, Long>> operationMerge = new BinaryOperator() {
            @Override
            public Object apply(Object o, Object o2) {
                return merger.merge((Map<Character, Long>)o, (Map<Character, Long>)o2);
            }
        };

        return lines
                .parallel()
                .map(counter::count)
                .reduce(operationMerge)
//                .reduce(merger::merge)
                .orElse(Collections.emptyMap());
    }

    public static void main(String[] args) {
        File file = new File("D:\\Solutions\\Digital.Habits\\JavaMiddleDev-homework_2-multithreading-\\src\\test\\resources\\test.txt");

        FileLetterCounter streamFileLetterCounter = new StreamFileLetterCounter();
        var map = streamFileLetterCounter.count(file);
        System.out.println(map.toString());
    }

}
