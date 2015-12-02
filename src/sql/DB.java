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
    String currentVarTable;

    private DB() {
        hashedTables = new HashMap<>();


        tables = new ArrayList<>();
        Table personnes = new Table("Personnes");
        Table formations = new Table("Formations");
        Table ecoles = new Table("Ecoles");


        Column id = new PrimaryKey("id", personnes);
        Column nom = new Column("nom", personnes);
        Column prenom = new Column("prenom", personnes);
        Column idFormation = new ForeignKey("idFormation", personnes, formations);

        Column idF = new PrimaryKey("id", formations);
        Column nomF = new Column("nom", formations);
        Column idEcole = new ForeignKey("idEcole", formations, ecoles);

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
        throw new Error(currentVarTable + " was not declared.");
    }

    public void setCurrentVarTable(String var) {
        currentVarTable = var;
    }

    public List<Table> getTableFromTablesAndColumns(String document, List<String> tablesOrColumns) throws Exception {

        if (!document.equals(tablesOrColumns.get(0)))
            tablesOrColumns.add(0, document);
        System.out.println(tablesOrColumns);
        String last = tablesOrColumns.get(tablesOrColumns.size() - 1);
        System.out.println(last + " " + isAttribut(last));
        List<Table> res = new ArrayList<>();
        if (isAttribut(last)) { // Last is an attribut
            Column c = getColumn(last);
            Table t = c.getOrigin();
            System.out.println(c + " " + t);
            if (!t.getName().toLowerCase().equals(tablesOrColumns.get(tablesOrColumns.size() - 2).toLowerCase())) {
                throw new Exception("Last attribute is not in the table : " + tablesOrColumns.get(tablesOrColumns.size() - 2));
            }
            tablesOrColumns.remove(tablesOrColumns.size() - 1);
        } else if (!isTable(last)) { //Last is a tables
            throw new Error(last + " is neither a table or a column!");
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

        if (currentVarTable != null)
            hashedTables.put(currentVarTable, res.get(res.size() - 1));

        return res;
    }

    private SelectJoinStep<Record> joinQueryFromTables(SelectJoinStep<Record> q, List<Table> tables) {
        for (int i = tables.size() - 2; i >= 0; i--) {
            Table table1 = tables.get(i + 1);
            Table table2 = tables.get(i);
            System.out.println(table1.getPrimaryKey().getName());
            System.out.println(table2.getForeignKey(table1).getName());
            q = q.join(table(table2.getName()))
                    .on(field(table1.getPrimaryKey().getName()).equal(field(table2.getForeignKey(table1).getName())));
        }
        return q;
    }

    public SelectJoinStep<Record> queryFromTablesAndColumns(SelectJoinStep<Record> join, String document, List<String> tablesOrColumns) throws Exception {
        List<Table> res = getTableFromTablesAndColumns(document, tablesOrColumns);

        join = join.join(table(res.get(res.size() - 1).getName())).on();

        return joinQueryFromTables(join, res);
    }

    public SelectJoinStep<Record> queryFromTablesAndColumns(String document, List<String> tablesOrColumns) throws Exception {
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
        for (Table t : tables) {
            for (Column c : t.getColumns()) {
                if (c.getName().toLowerCase().equals(last.toLowerCase())) {
                    return c;
                }
            }
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

    private boolean isAttribut(String s) {
        for (Table t : tables) {
            for (Column c : t.getColumns()) {
                if (c.getName().equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Condition makeCondition(Condition cwhere1, String operator, Condition cwhere2) {
        if (operator.equals("and"))
            return cwhere2.and(cwhere1);
        return cwhere2.or(cwhere1);
    }
}
