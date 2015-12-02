package sql;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import query.Where;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DB {
    public static final DB INSTANCE = new DB();
    List<Table> tables;

    private DB() {
        tables = new ArrayList<>();
        Table personnes = new Table("Personnes");
        Table formations = new Table("Formations");
        Table ecoles = new Table("Ecoles");


        Column id = new Column("id", personnes);
        Column nom = new Column("nom", personnes);
        Column prenom = new Column("prenom", personnes);
        Column idFormation = new ForeignKey("idFormation", personnes, formations);

        Column idF = new Column("id", formations);
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

    public SelectSelectStep<Record> queryFromTablesAndColumns(SelectSelectStep<Record> query, String document, List<String> tablesOrColumns) throws Exception {
        String last = tablesOrColumns.get(tablesOrColumns.size() - 1 );
        List res = new LinkedList<>();
        if(isAttribut(last)){ // Last is an attribut
            Table t = getColumn(last).getOrigin();
            Column c = getColumn(last);
            if(!t.getName().equals(tablesOrColumns.get(tablesOrColumns.size() - 2))){
                throw new Exception("Last attribute is not in the table : "+ tablesOrColumns.get(tablesOrColumns.size() - 2));
            }
            for(int i =0; i < tablesOrColumns.size() - 2 ; i++){ // On cherche a voir si toute les tables sont liées
                Table current = getTable(tablesOrColumns.get(i));
                Table next = getTable(tablesOrColumns.get(i + 1));
                if(isLinkTable(current,next)){ // si lié on rajoute la table current
                    res.add(current);
                }else{
                    throw new Exception("The tables :" + current.getName() +" and "+ next.getName() + " are not linked");
                    /*test*/
                }
            }
            res.add(t);
            res.add(c);
        }else{ //Last is a tables
            for(int i =0; i < tablesOrColumns.size() - 1 ; i++){ // On cherche a voir si toute les tables sont liées
                Table current = getTable(tablesOrColumns.get(i));
                Table next = getTable(tablesOrColumns.get(i + 1));
                if(isLinkTable(current,next)){ // si lié on rajoute la table current
                    res.add(current);
                }else{
                    throw new Exception("The tables :" + current.getName() +" and "+ next.getName() + " are not linked");
                }
            }
        }

        //return res;
        return query;
    }

    private boolean isLinkTable(Table current,Table next) {
        for(Column column : current.getColumns()){
            if(column instanceof ForeignKey){
                if(((ForeignKey) column).getRef().getName().equals(next.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    private Column getColumn(String last) {
        for (Table t : tables){
            for(Column c : t.getColumns()){
                if(c.getName().equals(last)){
                    return c;
                }
            }
        }
        return null;
    }

    private Table getTable(String s) {
        for (Table t : tables){
            if(t.getName().equals(s)){
                return t;
            }
        }
        return null;
    }

    private boolean isAttribut(String s) {
        for (Table t : tables){
            for(Column c : t.getColumns()){
                if(c.getName().equals(s)){
                    return true;
                }
            }
        }
        return false;
    }

    public Condition queryWhere(List<Where> wheres, List<String> operators) {
        List<Condition> cwheres = wheres.stream()
                .map(where -> {
                    try {
                        return where.toSQL();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
        Condition cwhere1 = cwheres.get(0);
        cwheres.remove(0);

        final int[] i = {0};
        return cwheres.stream()
                .reduce(cwhere1,
                        (cwhere, acc) -> {
                            Condition c;
                            if (operators.get(i[0]).equals("and"))
                                c = acc.and(cwhere);
                            else
                                c = acc.or(cwhere);
                            i[0]++;
                            return c;
                        });
    }

    public Condition makeCondition(Condition cwhere1, String operator, Condition cwhere2) {
        if (operator.equals("and"))
            return cwhere2.and(cwhere1);
        return cwhere2.or(cwhere1);
    }
}
