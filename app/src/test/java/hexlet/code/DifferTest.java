package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {
    private static Path getFixturePath(String fileName) {
        return Paths
            .get("src", "test", "resources", "fixtures", fileName)
            .toAbsolutePath()
            .normalize();
    }

    private static String readFixture(String fileName) throws IOException {
        var fixturePath = getFixturePath(fileName);
        return Files.readString(fixturePath).trim();
    }

    private static Stream<Arguments> provideFilesForGenerateTest() {
        return Stream.of(
            Arguments.of("wellFormed1.json", "wellFormed2.json", "wellFormedDiff.txt", "stylish"),
            Arguments.of("wellFormed1.json", "wellFormed2.json", "plainFormatWellFormedDiff.txt", "plain"),
            Arguments.of("wellFormed1.json", "wellFormed2.json", "jsonFormatWellFormedDiff.txt", "json"),
            Arguments.of("empty.json", "wellFormed2.json", "emptyAndWellFormedDiff.txt", "stylish"),
            Arguments.of("empty.json", "wellFormed2.json", "plainFormatEmptyAndWellFormedDiff.txt", "plain"),
            Arguments.of("empty.json", "wellFormed2.json", "jsonFormatEmptyAndWellFormedDiff.txt", "json"),
            Arguments.of("wellFormed1.json", "empty.json", "wellFormedAndEmptyDiff.txt", "stylish"),
            Arguments.of("wellFormed1.json", "empty.json", "plainFormatWellFormedAndEmptyDiff.txt", "plain"),
            Arguments.of("empty.json", "empty.json", "emptyDiff.txt", "stylish"),
            Arguments.of("empty.json", "empty.json", "plainFormatEmptyDiff.txt", "plain"),

            Arguments.of("wellFormed1.yaml", "wellFormed2.yml", "wellFormedDiff.txt", "stylish"),
            Arguments.of("wellFormed1.yaml", "wellFormed2.yml", "plainFormatWellFormedDiff.txt", "plain")
        );
    }

    @ParameterizedTest(name = "Generate method test {index} -f {3} - for {0} and {1}, expected {2}")
    @MethodSource("provideFilesForGenerateTest")
    void generateForJsonsTest(String fileName1, String fileName2, String diffFileName, String format)
        throws IOException {
        var expected = readFixture(diffFileName);
        var path1 = getFixturePath(fileName1).toString();
        var path2 = getFixturePath(fileName2).toString();

        var actual = Differ.generate(path1, path2, format);

        assertEquals(expected, actual);
    }

}
