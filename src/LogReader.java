package src;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;
/**
 *
 * @author wav
 */
public class LogReader
{
    public LogReader( String path )
    {
        readXls( path );
    }
    
    /*
        read excel
    https://www.callicoder.com/java-read-excel-file-apache-poi/
    */
    
    /**
     * readLogFromFolder
     * 
     * Recursively method to read all files under a single folder
     * 
     * @param folder File
     */
    public void readLogFromFolder( File folder )
    {
        if ( folder.isDirectory() )
        {
            for( File f : folder.listFiles() )
            {
                readLogFromFolder( f );
            }
        }
        
        else
        {
            readLogFile( folder );
        }
    }
    
    /**
     * readLogFile
     * 
     * read a single log file and insert in the log_moodle table
     * 
     * @param f File
     */
    public void readLogFile( File f )
    {
        try
        {
            List<String> lines = new ArrayList();
            
            Files.lines( f.toPath() ).forEach( line -> 
            {
                lines.add( line );
            } );
            
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
    
    /**
     * processLines
     * 
     * process the lines loaded from the file and generates the Objects to insert in database
     * 
     * @param lines List&lt;String&gt;
     */
    private void processLines( List<String> lines )
    {
        for ( String line : lines )
        {
            String[] l = line.split( ";" );
        }
    }
    
    /**
     * 
     * @param path 
     */
    private void readXls( String path )
    {
        try
        {
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = null;
            
            if ( getClass().getResource( path ) != null )
            {
                workbook = WorkbookFactory.create( new File( getClass().getResource( path ).getPath() ) );
            }
            
            else
            {
                workbook = WorkbookFactory.create( new File( "C:\\Users\\wav\\Desktop\\tcc_wav\\work\\log.xlsx" ) );
            }

            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

            /*
               =============================================================
               Iterating over all the sheets in the workbook (Multiple ways)
               =============================================================
            */

            // use a Java 8 forEach with lambda
            System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
            workbook.forEach(sheet -> {
                System.out.println("=> " + sheet.getSheetName());
            });

            /*
               ==================================================================
               Iterating over all the rows and columns in a Sheet (Multiple ways)
               ==================================================================
            */

            // Getting the Sheet at index zero
            Sheet sheet = workbook.getSheetAt(0);

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();
            final SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy hh:mm" );
            
            sheet.forEach( row -> {
                List<Cell>  cells = IteratorUtils.toList( row.cellIterator());

                if ( cells.get( 0 ).toString().contains( "/" ) ) //workaround to skip the first line
                {
                    try 
                    {
                        Timestamp time = new Timestamp( dateFormat.parse( cells.get(0).toString() ).getTime() );
                        String name = dataFormatter.formatCellValue( cells.get(1) );
                        String user = dataFormatter.formatCellValue( cells.get(2) );
                        String context =dataFormatter.formatCellValue( cells.get(3) );
                        String component = dataFormatter.formatCellValue( cells.get(4) );
                        String eventName = dataFormatter.formatCellValue( cells.get(5) );
                        String description = dataFormatter.formatCellValue( cells.get(6) );
                        String origin = dataFormatter.formatCellValue( cells.get(7) );
                        String IP = dataFormatter.formatCellValue( cells.get(8) );

                        LogDAO.insertLog( new Log( time, name, user, context, component, eventName, description, origin, IP ) );
                    } 
                    
                    catch ( Exception e ) 
                    {
                        e.printStackTrace();
                    }
                    
                }
            });

            // Closing the workbook
            workbook.close();
        }
        
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
    /*
        Importação dos logs do Moodle:
             Criar uma interface na qual o usuário pode escolher a turma (tabela turma), e o
            usuário (turma_usuario), além do arquivo de log a ser importado. Quando o
            usuário seleciona a turma, o sistema deve mostrar os alunos que estão na turma
            em questão para um deles seja selecionado.
             Após escolher os parâmetros o sistema executa a rotina de importação composta
            pelos seguintes passos:
            o Apagar todos os registros de log para o usuário na tabela log_moodle.
            o Ler os registros do arquivo log e gravar os mesmos na tabela
            log_moodle.
    
        create table log_moodle(    date_event datetime not null, name varchar(50) not null, user varchar(50) null, 
                                    context varchar(200) not null, component varchar(50) not null, event_name varchar(100) not null, 
                                    description varchar(400) not null, origin varchar(200) null, ip varchar(50) ); 
    */
    
    public static void main( String[] args )
    {
        new LogReader( "log.xlsx" );
//        readXls( "C:\\Users\\wav\\Desktop\\tcc_wav\\work\\log.xlsx" );
//        readLogFile( new File( "C:\\Users\\wav\\Desktop\\logs_20171011-1701_jeferson.csv" ) );
    }

    
    static class Log
    {
        private Timestamp date;
        private String name;
        private String user;//pode ser null
        private String context;
        private String component;
        private String eventName;
        private String description;
        private String origin;//pode ser null
        private String IP;

        public Log(Timestamp date, String name, String user, String context, String component, String eventName, String description, String origin, String IP) 
        {
            this.date = date;
            this.name = name;
            this.user = user;
            this.context = context;
            this.component = component;
            this.eventName = eventName;
            this.description = description;
            this.origin = origin;
            this.IP = IP;
        }

        
        
        /**
         * @return the date
         */
        public Timestamp getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(Timestamp date) {
            this.date = date;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the user
         */
        public String getUser() {
            return user;
        }

        /**
         * @param user the user to set
         */
        public void setUser(String user) {
            this.user = user;
        }

        /**
         * @return the context
         */
        public String getContext() {
            return context;
        }

        /**
         * @param context the context to set
         */
        public void setContext(String context) {
            this.context = context;
        }

        /**
         * @return the component
         */
        public String getComponent() {
            return component;
        }

        /**
         * @param component the component to set
         */
        public void setComponent(String component) {
            this.component = component;
        }

        /**
         * @return the eventName
         */
        public String getEventName() {
            return eventName;
        }

        /**
         * @param eventName the eventName to set
         */
        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description the description to set
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return the origin
         */
        public String getOrigin() {
            return origin;
        }

        /**
         * @param origin the origin to set
         */
        public void setOrigin(String origin) {
            this.origin = origin;
        }

        /**
         * @return the IP
         */
        public String getIP() {
            return IP;
        }

        /**
         * @param IP the IP to set
         */
        public void setIP(String IP) {
            this.IP = IP;
        }

        @Override
        public String toString()
        {
            String s = "date= " + date + "; Name= " + name + "; user= " + user + "; context= " + context 
                    + "; component= " + component + "; eventName= " + eventName + "; description= " + description 
                    + "; origin= " + origin + "; IP=" + IP ;
            
            return s;
        }
    }
}
