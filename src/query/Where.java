package query;

import org.jooq.Condition;

import static org.jooq.impl.DSL.field;

/**
 * sizo0
 */
public class Where {
    private Column column;
    private String operator;
    private Value value;

    public Where() { }

    public Where(Column column, String operator, Value value) {
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

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Condition toSQL() throws Exception {
        switch (operator) {
            case "=":
                return field(column.toString()).eq(value.getValue());
            case "!=":
                return field(column.toString()).ne(value.getValue());
            case ">=":
                return field(column.toString()).ge(value.getValue());
            case "<=":
                return field(column.toString()).le(value.getValue());
            case "<":
                return field(column.toString()).lt(value.getValue());
            case ">":
                return field(column.toString()).gt(value.getValue());
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