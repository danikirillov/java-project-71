package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.DiffLine;

import java.util.List;

public final class Formatter {
    public static String format(List<DiffLine> diff, String format) throws JsonProcessingException {
        return switch (format) {
            case "stylish" -> StylishFormatter.format(diff);
            case "plain" -> PlainFormatter.format(diff);
            case "json" -> JsonFormatter.format(diff);
            default -> throw new IllegalStateException("Unknown format: " + format);
        };
    }
}
