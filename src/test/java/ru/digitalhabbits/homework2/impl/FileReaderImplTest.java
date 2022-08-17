package ru.digitalhabbits.homework2.impl;

import org.junit.jupiter.api.Test;

import ru.digitalhabbits.homework2.FileReader;

import java.io.File;

import static com.google.common.io.Resources.getResource;
import static org.assertj.core.api.Assertions.*;

class FileReaderImplTest {

    FileReader fileReader = new FileReaderImpl();

    @Test
    void readLines_file_null() {

        var lines = fileReader.readLines(null);

        assertThat(lines).isEmpty();

    }
}