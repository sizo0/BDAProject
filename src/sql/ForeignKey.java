package sql;

public class ForeignKey extends Column {
	/*test*/
	private Table ref;

	public ForeignKey(String name, Table t, Table r) {
		super(name, t);
		ref = r;
	}

	public Table getRef() {
		return ref;
	}
}
