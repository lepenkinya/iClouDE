package storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import storage.executablefile.ExecutableFileHandler;
import storage.sourcefile.SourceFileHandler;
import storage.user.UserHandler;
import storage.StoringType;


public class Database {
	
	private static final Map<StoringType, Handler> handlers = 
			new HashMap<StoringType, Handler>();
	
	static {
		handlers.put(StoringType.SOURCE_FILE, new SourceFileHandler());
		handlers.put(StoringType.EXECUTABLE_FILE, new ExecutableFileHandler());
		handlers.put(StoringType.USER, new UserHandler());
	}


	public boolean contains(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(TestData.class);
		q.setFilter("name == nameParam");
	    q.declareParameters("String nameParam");

	    @SuppressWarnings("unchecked")
		List<TestData> results = (List<TestData>) q.execute(name);
//	    pm.close();
    	return !results.isEmpty();
	}
	
	public void add(Object td) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(td);
		pm.close();
	}
	
	public static Object get(StoringType type, Object... params) throws DatabaseException {
		return handlers.get(type).get(params);
	}
	
	public static void save(StoringType type, Object toSave) {
		handlers.get(type).save(toSave);
	}
}
