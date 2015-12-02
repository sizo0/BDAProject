package query;

import sql.Column;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private String name;
	private List<sql.Column> columns;

	public Table(String name) {
		this.name = name;
		columns = new ArrayList<>();
	}

	public Table add(sql.Column c) {
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
