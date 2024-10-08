package hexlet.code;

import hexlet.code.model.DiffLine;
import hexlet.code.model.Status;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DifferenceFinder {

    public static List<DiffLine> createChangeList(
        Map<String, Object> nameToValueForFile1,
        Map<String, Object> nameToValueForFile2
    ) {
        var diff = new ArrayList<DiffLine>();
        var fieldNames = new HashSet<>(nameToValueForFile1.keySet());
        fieldNames.addAll(nameToValueForFile2.keySet());
        for (var fieldName : fieldNames) {
            var fieldValue = nameToValueForFile1.get(fieldName);
            var updatedFieldValue = nameToValueForFile2.get(fieldName);

            var status = Status.ADDED;
            if (nameToValueForFile1.containsKey(fieldName)) {
                status = Status.REMOVED;
                if (nameToValueForFile2.containsKey(fieldName)) {
                    status = Objects.equals(fieldValue, updatedFieldValue) ? Status.UNTOUCHED : Status.UPDATED;
                }
            }

            diff.add(new DiffLine(status, fieldName, fieldValue, updatedFieldValue));
        }

        diff.sort(Comparator.comparing(DiffLine::name));
        return diff;
    }

}
