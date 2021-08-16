package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/16/16
 */
public class H2DatabaseHelper implements DatabaseHelper {

    private static final Logger logger = LogManager.getLogger(H2DatabaseHelper.class);

    @Override
    public void resetDatabase() {
        try {
            Connection con = AbstractDaoTest.getDataSource().getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DROP ALL OBJECTS");
            stmt.close();
            con.close();
        }catch(Throwable t){
            logger.error("Error resetting database!", t);
        }
    }

    @Override
    public DataSource createDataSource() {
        logger.debug("Creating JdbcConnectionPool...");
        String url = "jdbc:h2:mem:test";
        return JdbcConnectionPool.create(url, "sa", "sa");
    }

    @Override
    public void loadSql(String fileName) {
        String fullPath = "./src/test/resources/sql/" + fileName + ".h2.sql";
        File f = new File(fullPath);
        if( !f.exists() )
            throw new RuntimeException("Cannot find file: "+f);
        resetDatabase(); // Removes everthing from the database, period.
        try {
            Connection con = AbstractDaoTest.getDataSource().getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("RUNSCRIPT FROM '" + f.getCanonicalPath() + "'");
            stmt.close();
            con.close();
            logger.debug("Successfully loaded SQL file: "+f);
        }catch(Throwable t){
            logger.error("Error loading file: "+f, t);
            throw new UnsupportedOperationException("Cannot load file: "+f, t);
        }
    }

    @Override
    public void saveToFile() {
        try {
            Connection con = AbstractDaoTest.getDataSource().getConnection();
            Statement stmt = con.createStatement();
            stmt.execute("SCRIPT TO './target/temp.sql'");
            stmt.close();
            con.close();
            logger.info("Successfully stored file ./target/temp.sql");
        }catch(Throwable t){
            logger.error("ERROR SAVING DATABASE!", t);
        }
    }



}/* end H2DatabaseHelper */