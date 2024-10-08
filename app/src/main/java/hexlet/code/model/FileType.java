package hexlet.code.model;

public enum FileType {
    JSON, YAML;

    public static FileType fromFilePath(String filePath) {
        var pathParts = filePath.split("\\.");
        var extension = pathParts[pathParts.length - 1];
        if ("yml".equals(extension)) {
            extension = "yaml";
        }
        return valueOf(extension.toUpperCase());
    }
}
