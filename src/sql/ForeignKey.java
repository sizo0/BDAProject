package sql;

public class ForeignKey extends Column {
	private Table ref;

	public ForeignKey(String name, Table t, Table r) {
		super(name, t);
		ref = r;
	}
}
