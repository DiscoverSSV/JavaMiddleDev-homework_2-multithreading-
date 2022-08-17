package ru.digitalhabbits.homework2.impl;

import ru.digitalhabbits.homework2.LetterCounter;

import java.util.HashMap;
import java.util.Map;

public class LetterCounterImpl implements LetterCounter {

    @Override
    public Map<Character, Long> count(String input) {

        Map<Character, Long> letters = new HashMap<>();
        for (char c : (input==null?"":input).toCharArray()) {
            letters.put(c, letters.getOrDefault(c, 0L) + 1L);
        }

        return letters;
    }

}
