package hexlet.code.model;

public record DiffLine(Status status, String name, Object oldValue, Object newValue) {
}
