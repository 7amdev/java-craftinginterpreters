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
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping      : Expr expression",
                "Literal       : Object value",
                "Unary         : Token operator, Expr right"));
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
            String[] variables = fields.split(", ");
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
