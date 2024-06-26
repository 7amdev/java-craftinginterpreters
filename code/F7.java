package code;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class f7 {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;

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

        if (hadError)
            System.exit(65);

        if (hadRuntimeError)
            System.exit(70);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            hadError = false;

            System.out.print("> ");
            String line = reader.readLine();
            if (line == null)
                break;

            Scanner scanner = new Scanner(line);
            List<Token> tokens = scanner.scanTokens();

            Parser parser = new Parser(tokens);
            Object syntax = parser.parseRepl();

            if (hadError)
                continue;

            if (syntax instanceof List) {
                interpreter.interpret((List<Stmt>) syntax);
            } else if (syntax instanceof Expr) {
                String result = interpreter.interpret((Expr) syntax);
                if (result != null) {
                    System.out.println("= " + result);
                }
            }
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        if (hadError) {
            return;
        }

        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        if (hadError) {
            return;
        }

        interpreter.interpret(statements);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(RuntimeError error) {
        System.out.println(error.getMessage() + "\n [line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        System.out.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

}
