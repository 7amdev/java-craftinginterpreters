package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class generate_ast {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
                "Assign        : Token name, Expr value",
                "Binary        : Expr left, Token operator, Expr right",
                "Call          : Expr callee, Token paren, List<Expr> arguments",
                "Get           : Expr object, Token name",
                "Conditional   : Expr condition, Expr thenBranch, Expr elseBranch",
                "Grouping      : Expr expression",
                "Literal       : Object value",
                "Logical       : Expr left, Token operator, Expr right",
                "Set           : Expr object, Token name, Expr value",
                "This          : Token keyword",
                "Unary         : Token operator, Expr right",
                "Variable      : Token name"));

        defineAst(outputDir, "Stmt", Arrays.asList(
                "Block             : List<Stmt> statements",
                "Break             : ",
                "Class             : Token name, List<Stmt.Function> methods",
                "Expression        : Expr expression",
                "Function          : Token name, List<Token> params, List<Stmt> body",
                "If                : Expr condition, Stmt thenBranch, Stmt elseBranch",
                "Print             : Expr expression",
                "Return            : Token keyword, Expr value",
                "Var               : Token name, Expr initializer",
                "While             : Expr condition, Stmt body"));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package code;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        // Define Visitor Interface
        writer.println("  interface Visitor<R> {");

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("  }"); // closing Interface definition.

        // Define the base accept method.
        writer.println();
        writer.println("  abstract <R> R accept(Visitor<R> visitor);");
        writer.println();

        // Define Types Subclasses
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();

            writer.println("  static class " + className + " extends " + baseName + " {");

            // Constructor
            writer.println("    " + className + "(" + fields + ") {");
            String[] variables;
            if (fields.isEmpty()) {
                variables = new String[0];
            } else {
                variables = fields.split(", ");
            }

            for (String variable : variables) {
                String variableName = variable.split(" ")[1];
                writer.println("      this." + variableName + " = " + variableName + ";");
            }

            writer.println("    }");

            // Visitor Pattern
            writer.println();
            writer.println("    @Override");
            writer.println("    <R> R accept(Visitor<R> visitor) {");

            writer.println("      return visitor.visit" + className + baseName + "(this);");

            writer.println("    }");

            // fields
            writer.println();
            for (String variable : variables) {
                writer.println("    final " + variable + ";");
            }

            writer.println("  }");
            writer.println();
        }

        writer.println("}"); // Closing abstract base class.
        writer.close();
    }

}
