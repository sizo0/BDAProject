package query.command;

/**
 * Created by helomari on 01/12/15.
 */
public class DoubleConvert implements Command<Double> {

    @Override
    public Double run(String o) {
        return Double.valueOf(o);
    }
}
