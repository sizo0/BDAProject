package mongodb;

import sql.Column;
import sql.Table;

public class ForeignKey extends ObjectID {
	/*test*/
	private Collection ref;

	public ForeignKey(String name, Collection t, Collection r) {
		super(name, t);
		ref = r;
	}

	public Collection getRef() {
		return ref;
	}

	public boolean isForeignKey() {
		return true;
	}
}
