package ru.digitalhabbits.homework2.impl;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import ru.digitalhabbits.homework2.FileLetterCounter;
import ru.digitalhabbits.homework2.LetterCountMerger;
import ru.digitalhabbits.homework2.LetterCounter;

//todo Make your impl
public class AsyncFileLetterCounter implements FileLetterCounter {

    public static int numOfThreads = Runtime.getRuntime().availableProcessors();
    LetterCounter counter = new LetterCounterImpl();
    List<Future<Map<Character, Long>>> counterResults = new ArrayList<>();
    LetterCountMerger merger = new LetterCountMergerImpl();

    @Override
    public Map<Character, Long> count(File input) {

        Stream<String> lines = (new FileReaderImpl()).readLines(input);

        ExecutorService service = Executors.newFixedThreadPool(AsyncFileLetterCounter.numOfThreads);
        lines.forEach( line -> counterResults.add( service.submit(new CallableCounter(counter, line)) ) );
        service.shutdown();

        ForkJoinPool pool = new ForkJoinPool(AsyncFileLetterCounter.numOfThreads);

        return pool.invoke(new RecursiveTaskMerger(merger, counterResults));

    }

    public static void main(String[] args) {
        File file = new File("D:\\Solutions\\Digital.Habits\\JavaMiddleDev-homework_2-multithreading-\\src\\test\\resources\\test.txt");

        FileLetterCounter asyncFileLetterCounter = new AsyncFileLetterCounter();
        var map = asyncFileLetterCounter.count(file);
        System.out.println(map.toString());
    }

}

class CallableCounter implements Callable<Map<Character, Long>> {

    private LetterCounter counter;
    private String line;

    public CallableCounter(LetterCounter counter, String line) {
        this.counter = counter;
        this.line = line;
    }

    @Override
    public Map<Character, Long> call() throws Exception {
        return counter.count(line);
    }
}

class RecursiveTaskMerger extends RecursiveTask<Map<Character, Long>> {

    private LetterCountMerger merger;
    private List<Future<Map<Character, Long>>> list;

   public RecursiveTaskMerger(LetterCountMerger merger, List<Future<Map<Character, Long>>> list) {
        this.merger = merger;
        this.list = list;
    }

    @SneakyThrows
    private Map<Character, Long> futureMap(Future<Map<Character, Long>> future) {
       return future.get();
    }

    @SneakyThrows
    @Override
    protected Map<Character, Long> compute() {
        if (list.size() <= 2) {
            return list
                    .stream()
                    .map(this::futureMap)
                    .reduce(merger::merge)
                    .orElse(Collections.emptyMap());
        }

        RecursiveTaskMerger leftHalf = new RecursiveTaskMerger(merger, list.subList(0, list.size()/2));
        RecursiveTaskMerger rightHalf = new RecursiveTaskMerger(merger, list.subList(list.size()/2, list.size()));
        leftHalf.fork();
        rightHalf.fork();

        return merger.merge(leftHalf.join(), rightHalf.join());
    }
}
