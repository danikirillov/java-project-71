package hexlet.code;

public enum Sign {
    NONE(" "),
    MINUS("-"),
    PLUS("+");

    private final String symbol;

    Sign(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
