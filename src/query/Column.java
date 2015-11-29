package query;

/**
 * sizo0
 */
public class Column {
    private String prefix;
    private String name;

    public Column(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    @Override
    public String toString() {
        return prefix + "." + name;
    }
}