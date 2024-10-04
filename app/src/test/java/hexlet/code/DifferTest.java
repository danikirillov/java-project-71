package hexlet.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    static Path getFixturePath(String fileName) {
        return Paths
            .get("src", "test", "resources", "fixtures", fileName)
            .toAbsolutePath()
            .normalize();
    }

    static String readFixture(String fileName) throws IOException {
        var fixturePath = getFixturePath(fileName);
        return Files.readString(fixturePath).trim();
    }

    @Test
    @DisplayName("generate - for 2 good files - a valid differences string")
    void testGenerateForJsons() throws IOException {
        var expected = readFixture("wellFormedPlainJsonDiff.txt");
        var path1 = getFixturePath("wellFormedPlain1.json").toString();
        var path2 = getFixturePath("wellFormedPlain2.json").toString();

        var actual = Differ.generate(path1, path2);

        assertEquals(expected, actual);
    }

}
