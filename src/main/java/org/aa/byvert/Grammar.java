package org.aa.byvert;

import org.alex73.grammardb.GrammarDB2;
import org.alex73.grammardb.GrammarFinder;
import org.alex73.grammardb.StressUtils;
import org.alex73.grammardb.structures.Form;
import org.alex73.grammardb.structures.Paradigm;
import org.alex73.grammardb.structures.Variant;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class Grammar {
    private final GrammarFinder grammarFinder;

    public Grammar() throws Exception {
        GrammarDB2 grammarDB2 = GrammarDB2.initializeFromDir("data/GrammarDB");
        grammarFinder = new GrammarFinder(grammarDB2);
    }

    public Optional<String> findLemma(String word) {
        String normalizedWord = normalize(word);
        Paradigm[] paradigms = grammarFinder.getParadigms(word);
        return Arrays.stream(paradigms)
            .parallel()
            .map(Paradigm::getVariant)
            .flatMap(Collection::stream)
            .filter(variant -> isWordVariant(normalizedWord, variant))
            .map(Variant::getLemma)
            .findFirst();
    }

    private static boolean isWordVariant(String word, Variant variant) {
        return variant.getForm().stream()
            .map(Form::getValue)
            .map(Grammar::normalize)
            .anyMatch(word::equals);
    }

    private static String normalize(String word) {
        return StressUtils.unstress(word);
    }
}
