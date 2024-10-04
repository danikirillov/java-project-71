package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
    name = "gendiff",
    mixinStandardHelpOptions = true,
    version = "1.0.0-SNAPSHOT",
    description = "Compares two configuration files and shows a difference."
)
public class App implements Callable<Integer> {
    @Parameters(paramLabel = "filepath1", description = "path to the first file")
    private String filePath1;
    @Parameters(paramLabel = "filepath2", description = "path to the second file")
    private String filePath2;
    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    private String format = "stylish";

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        var result = Differ.generate(filePath1, filePath2);
        System.out.println(result);
        return 0;
    }
}
