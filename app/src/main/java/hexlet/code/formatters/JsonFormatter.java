package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.DiffLine;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class JsonFormatter {
    public static String format(List<DiffLine> diff) throws JsonProcessingException {
        var om = new ObjectMapper();
        var statusToChanges = diff
            .stream()
            .collect(
                Collectors.groupingBy(
                    DiffLine::status,
                    TreeMap::new,
                    Collectors.toList()
                )
            );
        return om.writeValueAsString(statusToChanges);
    }
}
