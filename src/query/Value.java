package query;

import query.command.Command;

/**
 * Created by helomari on 01/12/15.
 */
public class Value<T> {
    private T value;

    public Value(String value, Command<T> command) {
        this.value = command.run(value);
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                '}';
    }
}
