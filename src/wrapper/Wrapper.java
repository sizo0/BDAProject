package wrapper;

import mongodb.Query;
import org.jooq.Record;
import org.jooq.SelectJoinStep;

/**
 * Created by helomari on 04/12/15.
 */
public class Wrapper {
    private SelectJoinStep<Record> sql;
    private Query mongodb;

    public Wrapper(SelectJoinStep<Record> sql, Query mongodb) {
        this.sql = sql;
        this.mongodb = mongodb;
    }

    public SelectJoinStep<Record> getSql() {
        return sql;
    }

    public Query getMongodb() {
        return mongodb;
    }
}
