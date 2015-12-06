package mongodb;

/**
 * Created by helomari on 04/12/15.
 */
public class ObjectID extends Field {

    public ObjectID() { }


    public ObjectID(String name) {
        super(name);
    }

    public ObjectID(String name, Collection origin) {
        super(name, origin);
    }

}
