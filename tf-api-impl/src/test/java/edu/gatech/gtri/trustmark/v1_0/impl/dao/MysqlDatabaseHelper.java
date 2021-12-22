package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * TODO: Write a description here
 *
 * @user brad
 * @date 9/16/16
 */
public class MysqlDatabaseHelper implements DatabaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(MysqlDatabaseHelper.class);
    private static Properties MYSQL_PROPS;


    private static void checkMysqlDatabase(Properties props) {
        String url = "jdbc:mysql://" + props.getProperty("hostname", "localhost");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Assert the driver is in memory...
            Connection con = DriverManager.getConnection(url, props.getProperty("username"), props.getProperty("password"));
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS "+props.getProperty("database", "temp"));
            stmt.close();
            con.close();
        }catch(Throwable t){
            logger.error("Error checking/creating mysql database", t);
            throw new UnsupportedOperationException("Error checking/creating mysql database", t);
        }
    }

    private static Properties getMysqlProps() throws FileNotFoundException, IOException {
        synchronized (logger) {
            if( MYSQL_PROPS == null ) {
                MYSQL_PROPS = new Properties();
                File mysqlCredsFile = new File(new File(System.getProperty("user.home")), "mysql.creds");
                if( !mysqlCredsFile.exists() )
                    throw new FileNotFoundException("Missing required file: ~/mysql.creds");
                MYSQL_PROPS.load(new FileReader(mysqlCredsFile));
            }
            return MYSQL_PROPS;
        }
    }


    @Override
    public void resetDatabase() {
        try {
            Properties props = getMysqlProps();
            Connection con = AbstractDaoTest.getDataSource().getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS "+props.getProperty("database", "temp"));
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS "+props.getProperty("database", "temp"));
            stmt.close();
            con.close();
        }catch(Throwable t){
            logger.error("Error resetting database!", t);
        }
    }

    @Override
    public DataSource createDataSource() {
        DataSource mysqlDs = null;
        try{
            Properties props = getMysqlProps();
            checkMysqlDatabase(props);

            String url = "jdbc:mysql://" + props.getProperty("hostname", "localhost") + "/" + props.getProperty("database", "temp");
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl(url);
            mysqlDataSource.setUser(props.getProperty("username"));
            mysqlDataSource.setPassword(props.getProperty("password"));

            mysqlDs = mysqlDataSource;

        }catch(Throwable t){
            logger.error("Unable to create mysql data source!", t);
            throw new UnsupportedOperationException("Unable to create mysql data source!", t);
        }
        return mysqlDs;
    }

    @Override
    public void loadSql(String fileName) {
        String fullPath = "./src/test/resources/sql/" + fileName + ".mysql.sql";
        File f = new File(fullPath);
        if( !f.exists() )
            throw new RuntimeException("Cannot find file: "+f);
        resetDatabase(); // Removes everything from the database, period.
        Connection connection = null;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            connection = AbstractDaoTest.getDataSource().getConnection();
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (line.length() == 0 || line.startsWith("--")) {
                    continue;
                } else {
                    sb.append(line);
                }

                if (line.trim().endsWith(";")) {
                    statement.execute(sb.toString());
                    sb = new StringBuilder();
                }

            }
            br.close();
        }catch (Throwable t){
            logger.error("Error inserting sql: "+fileName, t);
        }finally {
            if( connection != null ){
                try{ connection.close(); } catch( Throwable t2 ) {}
            }
        }

    }//end loadSql()

    @Override
    public void saveToFile() {
        logger.warn("NOT SAVING MYSQL FILE - Use the command line mysqldump operation for this instead!");
    }



}/* end MysqlDatabaseHelper */
