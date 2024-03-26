package code;

import java.io.IOException;

public class F7 {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
        } else if (args.length == 1) {
            runFile("");
        } else {
            runPrompt();
        }

    }

    private static void runFile(String path) throws IOException {
        System.out.println("Load file");
    }

    private static void runPrompt() {
        System.out.println(">");
    }

}
