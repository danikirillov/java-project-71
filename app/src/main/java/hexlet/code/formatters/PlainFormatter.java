package hexlet.code.formatters;

import hexlet.code.DiffLine;
import hexlet.code.Status;

import java.util.List;

public final class PlainFormatter {
    private static final String LINE_DELIMITER = "\n";

    public static String format(List<DiffLine> diff) {
        var sb = new StringBuilder();
        for (var diffLine : diff) {
            if (diffLine.status() == Status.UNTOUCHED) {
                continue;
            }
            var wrappedName = parseValue(diffLine.name());
            sb.append("Property ").append(wrappedName);
            switch (diffLine.status()) {
                case ADDED -> sb.append(" was added with value: ").append(parseValue(diffLine.newValue()));
                case REMOVED -> sb.append(" was removed");
                case UPDATED -> sb.append(" was updated. From ").append(parseValue(diffLine.oldValue()))
                                  .append(" to ").append(parseValue(diffLine.newValue()));
                default -> throw new IllegalStateException("Unexpected value: " + diffLine.status());
            }
            sb.append(LINE_DELIMITER);
        }
        var lastDelimIndex = sb.length() - LINE_DELIMITER.length();
        if (lastDelimIndex >= 0) {
            sb.delete(lastDelimIndex, sb.length());
        }
        return sb.toString();
    }

    private static String parseValue(Object value) {
        return switch (value) {
            case null -> "null";
            case String sField -> String.format("'%s'", sField);
            case Boolean bField -> bField.toString();
            case Number nField -> nField.toString();
            default -> "[complex value]";
        };

    }
}
