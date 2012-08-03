package storage.sourcefile;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;

import storage.Database;
import storage.DatabaseException;
import storage.PMF;
import storage.StoringType;
import storage.project.ProjectItemHandler;

/**
 * This class provides implementations of all database operations
 * with SourceFile objects (create, get, update, delete).
 * @author Sergey
 *
 */
public class SourceFileHandler extends ProjectItemHandler {

	/**
	 * Creates new SourceFile object.
	 * <br/><br/>
	 * There should be 3 parameters:<br/>
	 * String name - name of the file to create<br/>
	 * String projectKey - database key of the project where this file should be created<br/>
	 * String parentKey - database key of the project item in which this file should be created
	 */
	@Override
	public String create(Object... params) throws DatabaseException {
		if (params.length != 3) {
			throw new DatabaseException("Incorrent number of parameters" +
					" for creating new file. There should be 3 parameters, but" +
					params.length + " parameters are given");
		}
		
		if (!(params[0] instanceof String)) {
			throw new DatabaseException("First parameter should be the name of" +
					" the file. Its type must be String");
		}
		
		if (!(params[1] instanceof String)) {
			throw new DatabaseException("Second parameter should be the key of" +
					" the project. Its type must be String");
		}
		
		if (!(params[2] instanceof String)) {
			throw new DatabaseException("Third parameter should be the key of" +
					" the parent ProjectItem. Its type must be String");
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String name = (String)params[0];
		String projectKey = (String)params[1];
		String parentKey = projectKey;
		
		SourceFile sourceFile = new SourceFile(name, projectKey, parentKey,
				new Date());
		pm.makePersistent(sourceFile);
		SourceFile tmp = pm.detachCopy(sourceFile);
		pm.close();
		
		SourceFileWriter writer = tmp.openForWriting();
		try {
			writer.close();
		} catch (IOException e) {
			throw new DatabaseException(e.getMessage());
		}
		Database.save(StoringType.SOURCE_FILE, tmp);
		
		return sourceFile.getKey();
	}
	
	/**
	 * Finds source file with given key.
	 * @param key - database key of the object to get
	 * @return source file found
	 * @throws DatabaseException if some error occurs in database or
	 * file wasn't found
	 */
	@Override
	public Object get(String key) throws DatabaseException {
		return get(key, SourceFile.class);
	}

	/**
	 * Deletes source file from database.
	 * @param key - database key of the object to delete
	 * @throws DatabaseException if some error occurs in database or
	 * if file wasn't found
	 */
	@Override
	public void delete(String key) throws DatabaseException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(SourceFile.class, key));
		pm.close();
	}
	
	/**
	 * Saves changes in file to database.
	 * @param toSave - SourceFile to be saved
	 * @throws DatabaseException if some error occurs in database
	 */
	@Override
	public void save(Object toSave) throws DatabaseException {
		super.save(toSave);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		SourceFile file = pm.getObjectById(SourceFile.class, ((SourceFile)toSave).getKey());
		file.setModificationTime(new Date());
		pm.close();
	}

}
