package mongodb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helomari on 06/12/15.
 */
public class Utils {
    public static Utils INSTANCE = new Utils();
    private int cursor = -1;

    private Utils() { }

    public String getVar() {
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        cursor++;
        return alphabets.substring(cursor, cursor + 1);
    }

    public String removeQuotationMarks(String s) {
        return s.replace("\"", "");
    }

    public void resetCursor() {
        cursor = -1;
    }
}
