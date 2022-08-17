package ru.digitalhabbits.homework2.impl;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.LetterCounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LetterCounterImplTest {

    LetterCounter counter = new LetterCounterImpl();

    @Test
    void count() {

        var line = "acaebfafdfcffcdf";

        var countedLetter = counter.count(line);

        assertThat(countedLetter).containsOnly(
                entry('a', 3L),
                entry('b', 1L),
                entry('c', 3L),
                entry('d', 2L),
                entry('e', 1L),
                entry('f', 6L)
        );

    }

    @Test
    void count_input_null() {

        var countedLetter = counter.count(null);

        assertThat(countedLetter).isEmpty();

    }

    @Test
    void count_input_empty() {

        var countedLetter = counter.count("");

        assertThat(countedLetter).isEmpty();

    }

}