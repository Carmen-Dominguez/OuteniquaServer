/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outeniquaserver;

/**
 *
 * @author Carmen
 */
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class OuteniquaServer 
{

    /**
     * @param args the command line arguments
     */
    
    private String search;
    Connection conn;
    
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    //Connect To Database
    public void ConnectDB() 
    {
        //laai die drywer
        try 
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("Driver succesfully downloaded");
            
        } catch (ClassNotFoundException c) 
        {
            System.out.println("Could not successfully download driver");
        }

        //koppel met die databasis
        try {
            
            String filename = "jdbc:odbc:OuteniquaDB";

            conn = DriverManager.getConnection(filename);

            System.out.println("Connection to OuteniquaDB is successful");

        } catch (Exception e) {
            System.out.println("Could not successfully connect to database");
        }
    }
    
     
    //Search method
    public void Search(String str){
    try 
            {
                setSearch(str);
                if (!"".equalsIgnoreCase(str))
                {
                    ConnectDB();
                    Statement stmt = conn.createStatement();
                    String sql = "SELECT * FROM Animal WHERE animal_name = '"+ getSearch() +"';";
                    ResultSet rs = stmt.executeQuery (sql);
                    String sqlSearch = rs.getString ("Name");

                    String id, name, desc, spe;

                    while (rs.next())
                    {
                        id = rs.toString();
                        System.out.println(id);
                    }

                    if (sqlSearch.equalsIgnoreCase (search))
                    { 
                        //this.dispose();
                        //new ResultFrame().setVisible(true);
                    }
                }
                    else 
                     JOptionPane.showMessageDialog (null, "Error.\nPlease retype your search");
                
            }
            catch (SQLException e)
            {

            }
    }
    
    public static void main(String[] args) throws IOException 
    {
        // TODO code application logic here
        ServerSocket server = null;
        String line;
        DataInputStream in;
        PrintStream os = null;
        Socket clientSocket = null;
        
        try
        {
            //Client socket created
            clientSocket = server.accept();
            in = new DataInputStream(clientSocket.getInputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(in));
            os = new PrintStream(clientSocket.getOutputStream());
            //read from input stream
            line = is.readLine();
            is.close();
        
        }catch(IOException e)
        {
            System.out.println("Main:   " + e);
        }
        catch(NullPointerException e)
        {
            System.out.println("Main:   " + e);
        }
        finally
        {
            os.close();
            clientSocket.close();
            server.close();
        }
    }
}
