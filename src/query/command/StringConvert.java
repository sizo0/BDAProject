package query.command;

/**
 * Created by helomari on 01/12/15.
 */
public class StringConvert implements Command<String> {

    @Override
    public String run(String o) {
        return o;
    }
}
