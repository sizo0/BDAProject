package mongodb;

/**
 * Created by helomari on 04/12/15.
 */
public class Field {
    private String name;
    private Collection origin;

    public Field() { }

    public Field(String name) {
        this.name = name;
    }

    public Field(String name, Collection origin) {
        this.name = name;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection getOrigin() {
        return origin;
    }

    public void setOrigin(Collection origin) {
        this.origin = origin;
    }

    public boolean isForeignKey() {
        return false;
    }

    @Override
    public String toString() {
        return "Field{" +
                "name='" + name + '\'' +
                ", origin=" + origin.getName() +
                '}';
    }
}
