package query;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;

import static org.jooq.impl.DSL.field;

/**
 * sizo0
 */
public class Where {
    private Column column;
    private String operator;
    private String value;

    public Where() { }

    public Where(Column column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Condition toSQL() throws Exception {
        boolean isString = value.charAt(0) == '"' && value.charAt(value.length() - 1) == '\"';
        switch (operator) {
            case "=":
                return field(column.toString()).eq(isString ? value.substring(1, value.length() - 1) : Double.valueOf(value));
            case "!=":
                return field(column.toString()).ne(isString ? value.substring(1, value.length() - 1) : Double.valueOf(value));
            case ">=":
                return field(column.toString()).ge(isString ? value.substring(1, value.length() - 1) : Double.valueOf(value));
            case "<=":
                return field(column.toString()).le(isString ? value.substring(1, value.length() - 1) : Double.valueOf(value));
            case "<":
                return field(column.toString()).lt(isString ? value.substring(1, value.length() - 1) : Double.valueOf(value));
            case ">":
                return field(column.toString()).gt(isString ? value.substring(1, value.length() - 1) : Double.valueOf(value));
            default:
                throw new Exception("Operator: " + operator + " not implemented!");
        }
    }

    @Override
    public String toString() {
        return "Where{" +
                "column=" + column +
                ", operator='" + operator + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}