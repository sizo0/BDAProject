package query;

import java.util.ArrayList;
import java.util.List;

/**
 * sizo0
 */
public class Cache<T> {
    private List<T> items;

    public Cache() {
        items = new ArrayList<>();
    }

    public boolean add(T item) {
        return items.add(item);
    }

    public List<T> get() {
        return items;
    }
}