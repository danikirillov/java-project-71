package hexlet.code.formatters;

import hexlet.code.DiffLine;

import java.util.List;

public final class StylishFormatter {
    private static final String LINE_DELIMITER = "\n  ";
    private static final String START_LINE = "{\n  ";
    private static final String END_LINE = "\n}";

    public static String format(List<DiffLine> diff) {
        var sb = new StringBuilder(START_LINE);
        for (var diffLine : diff) {
            switch (diffLine.status()) {
                case ADDED -> sb.append("+ ").append(diffLine.name()).append(": ").append(diffLine.newValue());
                case REMOVED -> sb.append("- ").append(diffLine.name()).append(": ").append(diffLine.oldValue());
                case UNTOUCHED -> sb.append("  ").append(diffLine.name()).append(": ").append(diffLine.oldValue());
                case UPDATED -> sb.append("- ").append(diffLine.name()).append(": ").append(diffLine.oldValue())
                                  .append(LINE_DELIMITER)
                                  .append("+ ").append(diffLine.name()).append(": ").append(diffLine.newValue());
                default -> throw new IllegalStateException("Unexpected value: " + diffLine.status());
            }
            sb.append(LINE_DELIMITER);
        }
        var lastDelimIndex = sb.length() - LINE_DELIMITER.length();
        if (lastDelimIndex >= 0) {
            sb.delete(lastDelimIndex, sb.length());
        }
        sb.append(END_LINE);
        return sb.toString();
    }
}
