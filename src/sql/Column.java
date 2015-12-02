package sql;
/*test*/
public class Column {
	private String name;
	private Table origin;
	
	public Column(String name, Table t) {
		this.name = name;
		origin = t;
	}

	public String getName() {
		return name;
	}

	public Table getOrigin() {
		return origin;
	}
}
