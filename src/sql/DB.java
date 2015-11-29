package sql;

import org.jooq.Record;
import org.jooq.SelectSelectStep;

import java.util.ArrayList;
import java.util.List;
import static org.jooq.impl.DSL.*;

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

	public SelectSelectStep<Record> queryFromTablesAndColumns(SelectSelectStep<Record> query, List<String> tablesOrColumns) {
		// TODO
		tables.forEach(table -> {
			if (tablesOrColumns.get(tablesOrColumns.size() - 1).equals(table.getName())) {

			}
		});
		return query;
	}
}
