package org.aa.byvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class VertGenerator {
    private final Pattern pattern = Pattern.compile("\\b");

    public void generate(BufferedReader reader, BufferedWriter writer, Grammar grammar) throws UncheckedIOException {
        try {
            generateInternally(reader, writer, grammar);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void generateInternally(BufferedReader reader, BufferedWriter writer, Grammar grammar) throws IOException {
        writeOrThrow("<doc>\n", writer);

        String line;
        while ((line = reader.readLine()) != null) {
            if(!line.isBlank()) {
                generateP(writer, grammar, line);
            }
        }

        writeOrThrow("</doc>\n", writer);
        writer.flush();
    }

    private void generateP(BufferedWriter writer, Grammar grammar, String line) {
        writeOrThrow("<p>\n", writer);

        record VertLine (String token, String lemma){}

        splitLine(line)
            .map(token ->
                new VertLine(
                    token,
                    getLemma(token, grammar)
                )
            ).forEach(vertLine ->
                writeVertLine(writer, vertLine.token, vertLine.lemma)
            );

        writeOrThrow("</p>\n", writer);
    }

    Stream<String> splitLine(String line) {
        return pattern.splitAsStream(line)
            .map(String::trim)
            .filter(Predicate.not(String::isEmpty));
    }

    private void writeVertLine(BufferedWriter writer, String firstString, String... otherStrings) {
        try {
            writer.write(firstString);

            for(String s : otherStrings) {
                if(s != null){
                    writer.write('\t');
                    writer.write(s);
                }
            }
            writer.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String getLemma(String token, Grammar grammar) {
        return grammar.findLemma(token)
            .map(Grammar::normalize)
            .orElse(null);
    }

    private void writeOrThrow(String token, BufferedWriter writer) throws UncheckedIOException {
        try {
            writer.write(token);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

