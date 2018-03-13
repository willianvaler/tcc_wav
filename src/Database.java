/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author willian
 */
public class Database 
{
    private static Database instance;
    private Connection connection;
    
    private Database() 
    {
        try 
        {
            Properties prop = new Properties();

            prop.load(new FileInputStream( "DB.properties" ) );

            String dbUrl = prop.getProperty( "db.url" );
            String dbUser = prop.getProperty( "db.user" );
            String dbPassword = prop.getProperty( "db.password" );

            connection = DriverManager.getConnection( dbUrl, dbUser, dbPassword );
        } 
        
        catch ( Exception e ) 
        {
            System.err.println( e );
        }
        
    }
    
    /**
     * 
     * @return 
     */
    public static Database getInstance()
    {
        if ( instance == null ) 
        {
            instance = new Database();
        }
        
        return instance;
    }
    
    /**
     * 
     * @return 
     */
    public Connection getConnection() 
    {
        if ( connection == null ) 
        {
            throw new RuntimeException( "connection cannot be null" );
        }

        return connection;
    }
    
}
