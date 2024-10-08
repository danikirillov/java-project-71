package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.model.FileType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hexlet.code.DifferenceFinder.createChangeList;

public final class Differ {
    public static String generate(String path1, String path2) throws IOException {
        return generate(path1, path2, "stylish");
    }

    public static String generate(String path1, String path2, String format) throws IOException {
        var file1 = readFile(path1);
        var file2 = readFile(path2);

        var nameToValueForFile1 = Parser.parse(file1, FileType.fromFilePath(path1));
        var nameToValueForFile2 = Parser.parse(file2, FileType.fromFilePath(path2));

        var diff = createChangeList(nameToValueForFile1, nameToValueForFile2);
        return Formatter.format(diff, format);
    }

    private static String readFile(String uri) throws IOException {
        var filePath = Paths
            .get(uri)
            .toAbsolutePath()
            .normalize();
        return Files.readString(filePath).trim();
    }
}
