package ru.digitalhabbits.homework2.impl;

import org.junit.jupiter.api.Test;
import ru.digitalhabbits.homework2.LetterCountMerger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LetterCountMergerImplTest {

    LetterCountMerger merger = new LetterCountMergerImpl();

    Map<Character, Long> firstMap = Map.of(
            'a', 4L,
            'b', 2L,
            'c', 4L,
            'd', 3L,
            'e', 2L,
            'f', 1L
    );

    Map<Character, Long> secondMap = Map.of(
            'b', 3L,
            'c', 2L,
            'd', 4L,
            'e', 4L,
            'f', 3L
            );

    @Test
    void merge() {
        var mergedMap = merger.merge(firstMap, secondMap);

        assertThat(mergedMap).containsOnly(
                entry('a', 4L),
                entry('b', 5L),
                entry('c', 6L),
                entry('d', 7L),
                entry('e', 6L),
                entry('f', 4L)
        );
    }

    @Test
    void merge_first_null() {
        var mergedMap = merger.merge(null, secondMap);

        assertThat(mergedMap).containsOnly(
                entry('b', 3L),
                entry('c', 2L),
                entry('d', 4L),
                entry('e', 4L),
                entry('f', 3L)
        );
    }

    @Test
    void merge_first_empty() {
        var mergedMap = merger.merge(Collections.emptyMap(), secondMap);

        assertThat(mergedMap).containsOnly(
                entry('b', 3L),
                entry('c', 2L),
                entry('d', 4L),
                entry('e', 4L),
                entry('f', 3L)
        );
    }

    @Test
    void merge_second_null() {
        var mergedMap = merger.merge(firstMap, null);

        assertThat(mergedMap).containsOnly(
                entry('a', 4L),
                entry('b', 2L),
                entry('c', 4L),
                entry('d', 3L),
                entry('e', 2L),
                entry('f', 1L)
        );
    }

    @Test
    void merge_second_empty() {
        var mergedMap = merger.merge(firstMap, Collections.emptyMap());

        assertThat(mergedMap).containsOnly(
                entry('a', 4L),
                entry('b', 2L),
                entry('c', 4L),
                entry('d', 3L),
                entry('e', 2L),
                entry('f', 1L)
        );
    }

    @Test
    void merge_first_second_null() {
        var mergedMap = merger.merge(null, null);

        assertThat(mergedMap).isEmpty();
    }

    @Test
    void merge_first_second_empty() {
        var mergedMap = merger.merge(Collections.emptyMap(), Collections.emptyMap());

        assertThat(mergedMap).isEmpty();
    }

}