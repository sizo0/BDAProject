package sql;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import query.Where;

import java.util.ArrayList;
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

    public SelectSelectStep<Record> queryFromTablesAndColumns(SelectSelectStep<Record> query, String document, List<String> tablesOrColumns) {
        // TODO
        tables.forEach(table -> {
            if (tablesOrColumns.get(tablesOrColumns.size() - 1).equals(table.getName())) {

            }
        });
        return query;
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
}
