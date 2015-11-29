package sql;

import java.util.ArrayList;
import java.util.List;

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

	public String getName() {
		return name;
	}

	public List<Column> getColumns() {
		return columns;
	}
}
