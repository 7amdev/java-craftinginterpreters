package code;

import java.util.List;

interface F7Callable {
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}