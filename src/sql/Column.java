package sql;

public class Column {
	private String name;
	private Table origin;
	
	public Column(String name, Table t) {
		this.name = name;
		origin = t;
	}
}
