package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.model.DiffLine;
import hexlet.code.model.JsonAddedNode;
import hexlet.code.model.JsonDiffNode;
import hexlet.code.model.JsonRemovedNode;

import java.util.List;
import java.util.TreeMap;

public class JsonFormatter {
    public static String format(List<DiffLine> diff) throws JsonProcessingException {
        var om = new ObjectMapper();
        var statusToChanges = new TreeMap<String, Object>();
        for (var diffLine : diff) {
            var jsonDiff = switch (diffLine.status()) {
                case ADDED -> new JsonAddedNode(diffLine.newValue());
                case UPDATED, UNTOUCHED -> new JsonDiffNode(diffLine.oldValue(), diffLine.newValue());
                case REMOVED -> new JsonRemovedNode(diffLine.oldValue());
                default -> throw new IllegalStateException("Unexpected value: " + diffLine.status());
            };
            statusToChanges.put(diffLine.name(), jsonDiff);
        }
        return om.writeValueAsString(statusToChanges);
    }
}
