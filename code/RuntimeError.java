package code;

public class RuntimeError extends RuntimeException {
    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    final Token token;
}
