package hexlet.code;

public record DiffLine(Sign sign, String name, String value) {
    @Override
    public String toString() {
        return sign.getSymbol() + " " + name + ": " + value;
    }
}
