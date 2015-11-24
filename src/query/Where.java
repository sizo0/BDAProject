package query;

/**
 * sizo0
 */
public class Where {
    private Column column;
    private String operator;
    private String value; // TODO faire une classe où on a la valeur et le type en tant que class

    public Where(Column column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }
}