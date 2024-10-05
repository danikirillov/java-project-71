package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public final class Parser {

    public static Map<String, Object> parse(String path) throws IOException {
        var objectMapper = getObjectMapper(path);
        return objectMapper.readValue(
            toFile(path),
            new TypeReference<>() {
            }
        );
    }

    private static ObjectMapper getObjectMapper(String path) {
        if (path.endsWith(".json")) {
            return new ObjectMapper();
        }
        if (path.endsWith(".yaml") || path.endsWith(".yml")) {
            return new YAMLMapper();
        }
        throw new IllegalArgumentException("Unsupported file format: " + path);
    }

    public static File toFile(String uri) {
        return Paths
            .get(uri)
            .toAbsolutePath()
            .normalize()
            .toFile();
    }
}
