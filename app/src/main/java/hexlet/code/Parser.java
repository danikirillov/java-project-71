package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import hexlet.code.model.FileType;

import java.io.IOException;
import java.util.Map;

public final class Parser {

    public static Map<String, Object> parse(String fileContent, FileType fileType) throws IOException {
        var objectMapper = getObjectMapper(fileType);
        return objectMapper.readValue(
            fileContent,
            new TypeReference<>() {
            }
        );
    }

    private static ObjectMapper getObjectMapper(FileType fileType) {
        return switch (fileType) {
            case JSON -> new ObjectMapper();
            case YAML -> new YAMLMapper();
            default -> throw new IllegalArgumentException("Unsupported file format: " + fileType);
        };
    }
}
