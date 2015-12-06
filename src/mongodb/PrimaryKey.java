package mongodb;

import sql.Column;
import sql.Table;

/**
 * Created by helomari on 02/12/15.
 */
public class PrimaryKey extends ObjectID {

    public PrimaryKey(String name, Collection t) {
        super(name, t);
    }
}

