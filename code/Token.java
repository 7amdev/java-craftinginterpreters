package code;

class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    // Constructer with no Access Modifier
    // gives the Token class a package level access
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

}
