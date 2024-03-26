package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class F7 {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jf7 [script]");
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }

    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String sourceCode = new String(bytes, Charset.defaultCharset());
        run(sourceCode);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null)
                break;

            run(line);
        }
    }

    private static void run(String source) {
        System.out.println("Source: " + source);
    }

}
