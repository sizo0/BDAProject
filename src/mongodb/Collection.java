package mongodb;

import sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by helomari on 04/12/15.
 */
public class Collection {
    String name;
    List<Field> fields;

    public Collection() { }

    public Collection(String name) {
        this.name = name;
        fields = new ArrayList<>();
    }

    public Collection add(Field field) {
        fields.add(field);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public boolean hasField(String field) {
        return getField(field) != null;
    }

    public Field getField(String field) {
        Optional<Field> o = fields.stream()
                .filter(field1 -> field.toLowerCase().equals(field1.getName().toLowerCase()))
                .findFirst();
        return o.isPresent() ? o.get() : null;
    }

    public boolean equals(Object o) {
        return o instanceof Collection && ((Collection) o).getName().toLowerCase().equals(getName().toLowerCase());
    }

    public boolean isLinkedTo(Collection collection) {
        System.out.println("isLinkedTo");
        return getForeignKey(collection) != null;
    }

    public Field getForeignKey(Collection collection) {
        System.out.println(collection.getName());
        Optional<Field> o = fields.stream()
                .filter(field -> {
                    System.out.println(field.getName());
                    return field.isForeignKey() && ((ForeignKey) field).getRef().equals(collection);
                })
                .findFirst();
        return o.isPresent() ? o.get() : null;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
