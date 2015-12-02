package query.command;

/**
 * Created by helomari on 01/12/15.
 */
public class IntConvert implements Command<Integer> {

    @Override
    public Integer run(String o) {
        return Double.valueOf(o).intValue();
    }
}
