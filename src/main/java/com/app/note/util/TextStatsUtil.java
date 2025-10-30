package com.app.note.util;

import java.util.*;
import java.util.stream.Collectors;

public class TextStatsUtil {
    public static Map<String, Integer> wordStats(String text) {
        if (text == null || text.isBlank())
            return Collections.emptyMap();

        String[] tokens = text.toLowerCase().split("[^\\p{L}0-9]+");
        Map<String, Integer> counts = new HashMap<>();
        for (String t : tokens)
            if (!t.isBlank())
                counts.merge(t, 1, Integer::sum);

        return counts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
