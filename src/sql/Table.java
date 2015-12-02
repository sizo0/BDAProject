package sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*test*/
public class Table {
	private String name;
	private List<Column> columns;
	
	public Table(String name) {
		this.name = name;
		columns = new ArrayList<>();
	}
	
	public Table add(Column c) {
		columns.add(c);
		return this;
	}

	public boolean hasColumn(String name){
		for (Column c : columns) {
			if(c.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		return "Table{" +
				"name='" + name + '\'' +
				", columns=" + columns +
				'}';
	}

	public Optional<Column> getForeignKey(Predicate<ForeignKey> p) {
		return columns.stream()
				.filter(column -> column instanceof ForeignKey && p.test((ForeignKey) column))
				.findFirst();
	}

	public Column getForeignKey(Table t) {
		return columns.stream()
				.filter(column -> column instanceof ForeignKey && ((ForeignKey) column).getRef().getName().toLowerCase().equals(t.getName().toLowerCase()))
				.findFirst()
				.get();
	}

	public Column getPrimaryKey() {
		return columns.stream()
				.filter(column -> column instanceof PrimaryKey)
				.findFirst()
				.get();
	}
}
