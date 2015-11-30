package query;

/**
 * sizo0
 */
public class Column {
    private String prefix;
    private String name;

    public Column(String name) {
        this.name = name;
    }

    public Column(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    @Override
    public String toString() {
        if (prefix == null) return name;
        return prefix + "." + name;
    }
}