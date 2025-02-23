package org.aa.byvert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        Application application = new Application();

        Path sourceDir = Paths.get("data", "corpus");
        log.info("Loading Corpuses from dir " + sourceDir.toAbsolutePath());
        try(Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(Files::isRegularFile)
                .forEach(path -> convertCorpusToVert(path, application));
        }
    }

    private static void convertCorpusToVert(Path corpus, Application application) {
        try {
            Path targetDir = mayBeCreateTargetDir();
            Path target = targetDir.resolve(corpus.getFileName() + ".vert");
            forceCreateFile(target);

            application.generateVert(corpus, target);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void forceCreateFile(Path target) throws IOException {
        if (Files.exists(target)) {
            Files.delete(target);
        }
        Files.createFile(target);
    }

    private static Path mayBeCreateTargetDir() throws IOException {
        Path targetDir = Paths.get("target", "data");
        if (!Files.exists(targetDir)) {
            log.info("Creating target dir " + targetDir.toAbsolutePath());
            Files.createDirectory(targetDir);
        }
        return targetDir;
    }
}