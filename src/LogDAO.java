package src;

import java.sql.PreparedStatement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author willian
 */
public class LogDAO 
{
    public static void insertLog( LogReader.Log log )
    {
        try 
        {
//            System.out.println( log.toString() );
            PreparedStatement ps = Database.getInstance().getConnection().prepareStatement( "insert into log_moodle values( ?, ?, ?, ?, ?, ?, ?, ?, ? )" );
            
            int i = 1;
            ps.setTimestamp( i++, log.getDate() );
            ps.setString( i++, log.getName() );
            ps.setString( i++, log.getUser() );
            ps.setString( i++, log.getContext() );
            ps.setString( i++, log.getComponent() );
            ps.setString( i++, log.getEventName() );
            ps.setString( i++, log.getDescription() );
            ps.setString( i++, log.getOrigin() );
            ps.setString( i++, log.getIP() );
            
            ps.execute();
        } 
        
        catch ( Exception e ) 
        {
            e.printStackTrace();
        }
    }
}
