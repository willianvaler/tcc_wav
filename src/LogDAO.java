
import java.sql.Statement;

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
            /*
                    private Timestamp date;
                    private String name;
                    private String user;//pode ser null
                    private String context;
                    private String component;
                    private String eventName;
                    private String description;
                    private String origin;//pode ser null
                    private String IP;
            */
            
            Statement st = Database.getInstance().getConnection().createStatement();
            
            String sql = "insert into log_moodle values( " + log.getDate()           + ",  '" + log.getName()      + "', '" + 
                                                             log.getUser()           + "', '" + log.getContext()   + "', '" + 
                                                             log.getComponent()      + "', '" + log.getEventName() + "', '" + 
                                                             log.getDescription()    + "',' " + log.getOrigin()    + "', '" + 
                                                             log.getIP()             + "' "   + " )";
            
            st.executeQuery(sql);
        } 
        
        catch ( Exception e ) 
        {
            e.printStackTrace();
        }
    }
}
