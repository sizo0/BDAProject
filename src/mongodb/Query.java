package mongodb;

import com.mongodb.QueryBuilder;
import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helomari on 04/12/15.
 */
public class Query {
    Collection collection;
    List<Query> joins;
    QueryBuilder condition;
    String joinVariable;

    public Query() {
        joins = new ArrayList<>();
        condition = new QueryBuilder();
        joinVariable = Utils.INSTANCE.getVar();
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Query addJoin(Query query) {
        joins.add(query);
        return this;
    }

    public void createConditions(List<Collection> collections) {
        System.out.println("createConditions");
        System.out.println(collections);
        if (collections.size() <= 1) return;
        QueryBuilder c = new QueryBuilder();
        Collection fstCollection = collections.get(0);

        c = c.put("_id").in("db." + fstCollection.getName() + ".distinct('" + fstCollection.getForeignKey(collections.get(1)).getName() + "')");

        for (int i = 1; i < collections.size() - 1; i++) {
            Collection current = collections.get(i);
            Collection next = collections.get(i + 1);
            String s = c.get().toString();
            s = s.replace("\"", "");
            c = c.put("_id").in("db." + current.getName() + ".distinct('" + current.getForeignKey(next).getName() + "', " + s + ")");
        }

        condition.and(c.get());
        System.out.println(condition.get().toString().replace("\"", ""));
    }

    public String get() {
        String result;
        List<String> variables = new ArrayList<>();

        result = getHeader();
        variables.add(joinVariable);

        for (Query join : joins) {
            result += "return " + join.getHeader();
            variables.add(join.joinVariable);
        }

        result += "return extend(" + StringUtils.join(variables, ",") + ");";

        result += getFooter();

        return result;
    }

    private String getHeader() {
        return "db." + collection.getName() + ".find(" + Utils.INSTANCE.removeQuotationMarks(condition.get().toString()) + ").map(function (" + joinVariable + ") {";
    }

    private String getFooter() {
        String footer = "});";

        for (Query ignored : joins) {
            footer += "});";
        }

        footer = footer.substring(0, footer.length() - 1);
        footer += ".reduce(function (acc, value) {return acc.concat(value);}, [])";

        return footer;
    }
}
