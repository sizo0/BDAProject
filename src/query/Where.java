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
    private boolean fieldTransform;

    public Where() {
        fieldTransform = false;
    }

    public Where(Column column, String operator, Value value) {
        this();
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
                return field(column.toString()).eq(fieldTransform ? field(value.getValue().toString()) : value.getValue());
            case "!=":
                return field(column.toString()).ne(fieldTransform ? field(value.getValue().toString()) : value.getValue());
            case ">=":
                return field(column.toString()).ge(fieldTransform ? field(value.getValue().toString()) : value.getValue());
            case "<=":
                return field(column.toString()).le(fieldTransform ? field(value.getValue().toString()) : value.getValue());
            case "<":
                return field(column.toString()).lt(fieldTransform ? field(value.getValue().toString()) : value.getValue());
            case ">":
                return field(column.toString()).gt(fieldTransform ? field(value.getValue().toString()) : value.getValue());
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

    public boolean isFieldTransform() {
        return fieldTransform;
    }

    public void setFieldTransform(boolean fieldTransform) {
        this.fieldTransform = fieldTransform;
    }
}