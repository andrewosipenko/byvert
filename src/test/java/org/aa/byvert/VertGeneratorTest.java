package org.aa.byvert;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class VertGeneratorTest {
    static VertGenerator vertGenerator;

    @BeforeAll
    static void setUp() {
        vertGenerator = new VertGenerator();
    }

    @Test
    void splitLineNoPunctuation() {
        assertLinesMatch(List.of("a"), vertGenerator.splitLine("a").toList());
        assertLinesMatch(List.of("a", "b"), vertGenerator.splitLine("a b").toList());
        assertLinesMatch(List.of("a", "b"), vertGenerator.splitLine("a  b").toList());
        assertLinesMatch(List.of("aa", "bb", "ccc"), vertGenerator.splitLine("aa bb ccc").toList());
    }

    @Test
    void splitLinePunctuation() {
        assertLinesMatch(List.of("a", "."), vertGenerator.splitLine("a.").toList());
        assertLinesMatch(List.of("a", ",", "b"), vertGenerator.splitLine("a,b").toList());
        assertLinesMatch(List.of("a", ",", "b"), vertGenerator.splitLine("a, b").toList());
        assertLinesMatch(List.of("aa", "bb", "...", "ccc"), vertGenerator.splitLine("aa bb... ccc").toList());
    }
}