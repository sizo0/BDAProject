package query;

import java.util.List;

/**
 * sizo0
 */
public class Select extends Query {
    private List<Column> columns;
    private List<String> tables;
    private List<Where> wheres;

    public Select(List<Column> columns, List<String> tables, List<Where> wheres) {
        this.columns = columns;
        this.tables = tables;
        this.wheres = wheres;
    }
}