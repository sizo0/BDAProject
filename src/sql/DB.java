package sql;

import org.jooq.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.*;

public class DB {
    public static final DB INSTANCE = new DB();
    List<Table> tables;

    Map<String, Table> hashedTables; // TODO make query class and place all methods and this attr in it
    Map<String, Column> hashedAttributes;

    String currentVarTable;

    private DB() {
        hashedTables = new HashMap<>();
        hashedAttributes = new HashMap<>();


        tables = new ArrayList<>();
        Table personnes = new Table("Personne");
        Table formations = new Table("Formation");
        Table ecoles = new Table("Ecole");


        Column id = new PrimaryKey("id", personnes);
        Column nom = new Column("nom", personnes);
        Column prenom = new Column("prenom", personnes);
        Column idFormation = new ForeignKey("IDFormation", personnes, formations);

        Column idF = new PrimaryKey("IDFormation", formations);
        Column nomF = new Column("nom", formations);
        Column idEcole = new ForeignKey("IDEcole", formations, ecoles);

        personnes.add(id)
                .add(nom)
                .add(prenom)
                .add(idFormation);

        formations.add(idF)
                .add(nomF)
                .add(idEcole);

        tables.add(personnes);
        tables.add(formations);
    }

    public String getTableFromVarName() {
        if (hashedTables.containsKey(currentVarTable))
            return hashedTables.get(currentVarTable).getName();
        throw new Error(currentVarTable + " was not declared as a table.");
    }

    public String getAttributeFromVarName() {
        if (hashedAttributes.containsKey(currentVarTable))
            return hashedAttributes.get(currentVarTable).getBareName();
        return null;
    }

    public void setCurrentVarTable(String var) {
        currentVarTable = var;
    }

    public List<Table> getTableFromTablesAndColumns(String document, List<String> tablesOrColumns) throws Error {

        if (!document.equals(tablesOrColumns.get(0)))
            tablesOrColumns.add(0, document);
        System.out.println(tablesOrColumns);
        String last = tablesOrColumns.get(tablesOrColumns.size() - 1);
        System.out.println(last + " " + isAttribute(last));
        List<Table> res = new ArrayList<>();
        Column c = null;
        if (isAttribute(last)) { // Last is an attribute
            System.out.println(tablesOrColumns.get(tablesOrColumns.size() - 2));
            Table t = getTable(tablesOrColumns.get(tablesOrColumns.size() - 2));
            if (t == null)
                throw new Error(tablesOrColumns.get(tablesOrColumns.size() - 2) + " is not a table.");
            if (!t.hasColumn(last)) {
                throw new Error("Last attribute is not in the table : " + tablesOrColumns.get(tablesOrColumns.size() - 2));
            }
            c = t.getColumn(last);
            tablesOrColumns.remove(tablesOrColumns.size() - 1);
        } else if (!isTable(last)) { //Last is a tables
            throw new Error(last + " is neither a table nor a column!");
        }

        res.add(getTable(tablesOrColumns.get(0)));
        for (int i = 1; i < tablesOrColumns.size(); i++) { // On cherche a voir si toute les tables sont liées
            Table current = getTable(tablesOrColumns.get(i));
            Table previous = getTable(tablesOrColumns.get(i - 1));
            if (isLinkTable(current, previous)) { // si lié on rajoute la table current
                res.add(current);
            } else {
                throw new Error("The tables: " + current.getName() + " and " + previous.getName() + " are not linked");
            }
        }

        System.out.println("RES " + res);

        System.out.println(document + " " + currentVarTable);

        if (currentVarTable != null) {
            hashedTables.put(currentVarTable, res.get(res.size() - 1));
            if (isAttribute(last))
                hashedAttributes.put(currentVarTable, c);
        }

        System.out.println(hashedTables);
        System.out.println(hashedAttributes);

        return res;
    }

    private SelectJoinStep<Record> joinQueryFromTables(SelectJoinStep<Record> q, List<Table> tables) {
        for (int i = tables.size() - 2; i >= 0; i--) {
            Table table1 = tables.get(i + 1);
            Table table2 = tables.get(i);
//            System.out.println(table1.getPrimaryKey().getName());
//            System.out.println(table2.getForeignKey(table1).getName());
            q = q.join(table(table2.getName()))
                    .on(field(table1.getPrimaryKey().getName()).equal(field(table2.getForeignKey(table1).getName())));
        }
        return q;
    }

    public SelectJoinStep<Record> queryFromTablesAndColumns(SelectJoinStep<Record> join, String document, List<String> tablesOrColumns) throws Error {
        List<Table> res = getTableFromTablesAndColumns(document, tablesOrColumns);

        join = join.join(table(res.get(res.size() - 1).getName())).on();

        return joinQueryFromTables(join, res);
    }

    public SelectJoinStep<Record> queryFromTablesAndColumns(String document, List<String> tablesOrColumns) throws Error {
        List<Table> res = getTableFromTablesAndColumns(document, tablesOrColumns);

        SelectJoinStep<Record> q = select().select(field("*")).from(table(res.get(res.size() - 1).getName()));

        return joinQueryFromTables(q, res);
    }

    private boolean isTable(String last) {
        return tables.stream()
                .anyMatch(table -> last.toLowerCase().equals(table.getName().toLowerCase()));
    }

    private boolean isLinkTable(Table current, Table previous) {
        return previous.getForeignKey(foreignKey -> foreignKey.getRef().getName().toLowerCase().equals(current.getName().toLowerCase())).isPresent();
    }

    private Column getColumn(String last) {
        Column c;
        for (Table t : tables) {
            if ((c = t.getColumn(last)) != null)
                return c;
        }
        return null;
    }

    private Table getTable(String s) {
        for (Table t : tables) {
            if (t.getName().toLowerCase().equals(s.toLowerCase())) {
                return t;
            }
        }
        return null;
    }

    private boolean isAttribute(String s) {
        return tables.stream()
                .anyMatch(table -> table.hasColumn(s));
    }

    public Condition makeCondition(Condition cwhere1, String operator, Condition cwhere2) {
        if (operator.equals("and"))
            return cwhere2.and(cwhere1);
        return cwhere2.or(cwhere1);
    }
}
