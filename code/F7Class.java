package code;

import static code.TokenType.IDENTIFIER;

import java.util.List;
import java.util.Map;

public class F7Class implements F7Callable {
    final String name;
    final F7Class superclass;
    private final Map<String, F7Function> methods;

    F7Class(String name, F7Class superclass, Map<String, F7Function> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    F7Function findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        F7Instance instance = new F7Instance(this);
        F7Function initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        // instance.printFields();

        return instance;
    }

    @Override
    public int arity() {
        F7Function initializer = findMethod("init");
        if (initializer == null) {
            return 0;
        }
        return initializer.arity();
    }

    @Override
    public String toString() {
        return name;
    }
}
