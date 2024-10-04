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
            Arguments.of("wellFormedPlain1.json", "wellFormedPlain2.json", "wellFormedPlainJsonDiff.txt"),
            Arguments.of("empty.json", "wellFormedPlain2.json", "emptyAndWellFormedPlainJsonDiff.txt"),
            Arguments.of("wellFormedPlain1.json", "empty.json", "wellFormedPlainJsonAndEmptyDiff.txt"),
            Arguments.of("empty.json", "empty.json", "empty.txt"),
            Arguments.of("sortTestFile1.json", "sortTestFile2.json", "sortTestJsonDiff.txt")
        );
    }

    @ParameterizedTest(name = "Generate method test {index} - for {0} and {1}, expected {2}")
    @MethodSource("provideFilesForGenerateTest")
    void generateForJsonsTest(String fileName1, String fileName2, String diffFileName) throws IOException {
        var expected = readFixture(diffFileName);
        var path1 = getFixturePath(fileName1).toString();
        var path2 = getFixturePath(fileName2).toString();

        var actual = Differ.generate(path1, path2);

        assertEquals(expected, actual);
    }

}
