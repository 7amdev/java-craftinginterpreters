package code;

import java.util.HashMap;
import java.util.Map;

public class F7Instance {

    private F7Class klass;
    private final Map<String, Object> fields = new HashMap<>();

    F7Instance(F7Class klass) {
        this.klass = klass;
    }

    Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }

        F7Function method = klass.findMethod(name.lexeme);
        if (method != null)
            return method.bind(this);

        throw new RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
    }

    void printFields() {
        for (Map.Entry<String, Object> set : this.fields.entrySet()) {
            System.out.println(set.getKey() + ": " + set.getValue().toString());
        }
    }

    void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }

}
