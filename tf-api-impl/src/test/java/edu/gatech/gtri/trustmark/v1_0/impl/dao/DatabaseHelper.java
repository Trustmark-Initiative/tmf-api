package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import javax.sql.DataSource;

/**
 * Provides functionality helpful for working with databases (abstracts out database specifics into general functions).
 *
 * @user brad
 * @date 9/16/16
 */
public interface DatabaseHelper {

    /**
     * After this method is called, the database schema is completely empty (as though nothing had ever happened).
     */
    public void resetDatabase();

    /**
     * Provides a data source object to the caller.
     */
    public DataSource createDataSource();

    /**
     * First clears the database using resetDatabase() above.  Next, loads the given file (as though it was sql) into
     * the database.
     */
    public void loadSql(String filename);

    /**
     * Saves the current database schema to a file.
     */
    public void saveToFile();



}/* end DatabaseHelper */