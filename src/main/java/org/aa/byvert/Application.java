package org.aa.byvert;

import java.util.Optional;
import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());

    private final Grammar grammar;

    public Application() throws Exception {
        log.info("Starting Application");

        grammar = new Grammar();
    }

    public Optional<String> findLemma(String word) {
        return grammar.findLemma(word);
    }
}
