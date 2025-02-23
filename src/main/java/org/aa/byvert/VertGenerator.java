package org.aa.byvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.regex.Pattern;

public class VertGenerator {
    public void generate(BufferedReader reader, BufferedWriter writer, Grammar grammar) throws UncheckedIOException {
        try {
            generateInternally(reader, writer, grammar);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void generateInternally(BufferedReader reader, BufferedWriter writer, Grammar grammar) throws IOException {
        Pattern pattern = Pattern.compile("[\b\s]");

        writeOrThrow("<doc>\n", writer);

        String line;
        while ((line = reader.readLine()) != null) {
            if(!line.isBlank()) {
                generateP(writer, grammar, pattern, line);
            }
        }

        writeOrThrow("</doc>\n", writer);
        writer.flush();
    }

    private void generateP(BufferedWriter writer, Grammar grammar, Pattern pattern, String line) {
        writeOrThrow("<p>\n", writer);

        pattern.splitAsStream(line).forEach(token ->
            generateVertLine(token, writer, grammar)
        );

        writeOrThrow("</p>\n", writer);
    }

    private void generateVertLine(String token, BufferedWriter writer, Grammar grammar) {
        try {
            writer.write(token);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        grammar.findLemma(token).ifPresent(lemma ->
            writeNextToken(lemma, writer)
        );

        writeOrThrow("\n", writer);
    }

    private void writeNextToken(String token, BufferedWriter writer) {
        writeOrThrow("\t", writer);
        writeOrThrow(token, writer);
    }

    private void writeOrThrow(String token, BufferedWriter writer) throws UncheckedIOException {
        try {
            writer.write(token);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
