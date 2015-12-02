package sql;

import java.util.ArrayList;
import java.util.List;
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
}
