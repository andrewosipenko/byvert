package org.aa.byvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());

    private final Grammar grammar;

    public Application() throws Exception {
        log.info("Starting Application");

        grammar = new Grammar();
    }

    public void generateVert(Path source, Path target) throws UncheckedIOException {
        log.info("Generating Vert from corpus " + source +  " to " + target);
        VertGenerator vertGenerator = new VertGenerator();
        try(
            BufferedReader reader = Files.newBufferedReader(source);
            BufferedWriter writer = Files.newBufferedWriter(target)
        ) {
            vertGenerator.generate(reader, writer, grammar);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
