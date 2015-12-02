package query;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * sizo0
 */
public class Select {
    private List<Column> columns;
    private List<String> tables;
    private List<Where> wheres;
    private List<Select> joins;

    public Select() {
        columns = new ArrayList<>();
        tables = new ArrayList<>();
        wheres = new ArrayList<>();
        joins = new ArrayList<>();
    }

    public String toString() {
        String stringColumns = columns.size() == 0 ? "*" : StringUtils.join(columns, ",");
//        return "Select " + stringColumns +
        return "";
    }
}