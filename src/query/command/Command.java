package query.command;

/**
 * Created by helomari on 01/12/15.
 */
public interface Command<T> {
    T run(String o);
}
