package edu.gatech.gtri.trustmark.v1_0.impl.dao;

import com.j256.ormlite.db.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Insert Comment Here
 * <br/><br/>
 *
 * @author brad
 * @date 9/14/16
 */
public class DatabaseUtils {
    //==================================================================================================================
    //  STATIC VARIABLES
    //==================================================================================================================
    private static final Logger log = LoggerFactory.getLogger(DatabaseUtils.class);

    public static Map<String, DatabaseType> TYPE_MAPPINGS = new HashMap<>();
    //==================================================================================================================
    //  STATIC METHODS
    //==================================================================================================================
    static {
        TYPE_MAPPINGS = new HashMap<>();
        TYPE_MAPPINGS.put("MySQL".toLowerCase(),                new MysqlDatabaseType());
        TYPE_MAPPINGS.put("PostgreSQL".toLowerCase(),           new PostgresDatabaseType());
        TYPE_MAPPINGS.put("HSQL Database Engine".toLowerCase(), new HsqldbDatabaseType());
        TYPE_MAPPINGS.put("H2".toLowerCase(),                   new H2DatabaseType());
        TYPE_MAPPINGS.put("Oracle".toLowerCase(),               new OracleDatabaseType());
    }


    /**
     * Determines the database type as needed by ORM lite, by inspecting the database product name.
     */
    public static DatabaseType getDatabaseType(DataSource dataSource){
        DatabaseType dbType = null;
        Connection con = null;
        String dbName = null;
        try {
            con = dataSource.getConnection();
            dbName = con.getMetaData().getDatabaseProductName();

            if( TYPE_MAPPINGS.containsKey(dbName.toLowerCase()) ){
                dbType = TYPE_MAPPINGS.get(dbName.toLowerCase());
            }else{
                throw new UnsupportedOperationException("Cannot find database type: "+dbName);
            }
        }catch(Throwable t){
            log.error("Unable to determine database type!", t);
            throw new RuntimeException(t);
        }finally{
            if( con != null ) {
                try{con.close();}catch(Throwable t){}
            }
        }
        log.debug("Resolved DatabaseProductName["+dbName+"] to DatabaseType:  "+dbType.getClass().getSimpleName());
        return dbType;
    }//end getDatabaseType()


}/* end DatabaseUtils */
