package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LetterCountMergerImpl implements LetterCountMerger {

    @Override
    public Map<Character, Long> merge(Map<Character, Long> first, Map<Character, Long> second) {

        return merge_remapping(first, second); //Вариант 1.
//        return merge_merging_w_func_interface_1(first, second); //Вариант 2.
//        return merge_merging_w_func_interface_2(first, second); //Вариант 3.
//        return merge_merging_w_lambda(first, second); //Вариант 4.
//        return merge_collect_to_map(first, second); //Вариант 5.

    }

    //Вариант 1
    public Map<Character, Long> merge_remapping(Map<Character, Long> first, Map<Character, Long> second) {
        Map<Character, Long> merged = new HashMap<>((first == null ? Collections.emptyMap() : first));

        if (second != null) {
            second.forEach(
                    (key, value) ->
                        merged.put(key, merged.getOrDefault(key, 0L) + value)
            );
        }

        return merged;
    }

    //Вариант 2
    public Map<Character, Long> merge_merging_w_func_interface_1(Map<Character, Long> first, Map<Character, Long> second) {
        Map<Character, Long> merged = new HashMap<>((first==null?Collections.emptyMap():first));

        if (second != null) {
            BiFunction aggregate = new BiFunction<Long, Long, Long>() {
                @Override
                public Long apply(Long valueFromFirst, Long valueFromSecond) {
                    return valueFromFirst + valueFromSecond;
                }
            };

            second.forEach(
                    (key, value) ->
                        merged.merge(key, value, aggregate)
            );
        }

        return merged;
    }

    //Вариант 3.
    public Map<Character, Long> merge_merging_w_func_interface_2(Map<Character, Long> first, Map<Character, Long> second) {
        Map<Character, Long> merged = new HashMap<>((first==null?Collections.emptyMap():first));

        if (second != null) {
            second.forEach(
                    (key, value) ->
                        merged.merge(key, value, Long::sum)
            );
        }

        return merged;
    }

    //Вариант 4.
    public Map<Character, Long> merge_merging_w_lambda(Map<Character, Long> first, Map<Character, Long> second) {
        Map<Character, Long> merged = new HashMap<>((first==null?Collections.emptyMap():first));

        if (second != null) {
            second.forEach(
                    (key, value) ->
                        merged.merge(key, value, (valueFirst, valueSecond) -> valueFirst + valueSecond)
            );
        }

        return merged;
    }

    //Вариант 5.
    public Map<Character, Long> merge_collect_to_map(Map<Character, Long> first, Map<Character, Long> second) {
        Map<Character, Long> merged = Stream
                .of(
                    (first==null?new HashMap<Character, Long>():first),
                    (second==null?new HashMap<Character, Long>():second)
                )
                .flatMap(map -> map.entrySet().stream())
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Long::sum
                    )
                );

        return merged;
    }

}

