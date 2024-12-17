/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outeniquaserver;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin1
 */
public class DataBase implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    private String sampleProperty;
    private PropertyChangeSupport propertySupport;
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    Connection conn;
    
    public DataBase() 
    {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    //Method to clear all the fields    
    public void clearSearch() 
    {
    //Method to clear all the fields
    search = "";  
    }
    
    //Connects to the database and loads the driver
    public void getDatabase()
    {    //laai die drywer
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

            System.out.println("Connection to Fauna&FloraDB is successful");

        } catch (Exception e) {
            System.out.println("Could not successfully connect to database");
        }
    
    }
    
    //Searches animal in database
    public String searchAnimal()
    {   
         try 
        {
            boolean match = Pattern.matches("[^0-9]", getSearch());
            
            if (!match || !"".equalsIgnoreCase(search))
            {
                getDatabase();
                java.sql.Statement stmt = conn.createStatement();
               
                String sql1 = "SELECT * FROM Animal WHERE animal_name = '"+ search +"';";
                String sql2 = "SELECT * FROM Species WHERE species_name = '" + search + "';";
                String result = "";
                
                ResultSet rs = stmt.executeQuery (sql1);
                String sqlSearch = rs.getString ("Name");
                
                while (rs.next())
                {
                    sqlSearch = rs.toString();
                    
                    result = "Name  : " + rs.getString("Name") + "\nLatin Name: " + rs.getString("LatinName") +"\nInfo   : " + rs.getString("Info");
                }
                
                //If there's nothing in the Animal table, check the Species table
                rs = stmt.executeQuery(sql2);
                sqlSearch = rs.getString("Name");
                while (rs.next())
                {
                    sqlSearch = rs.toString();           
                }
                
                if (sqlSearch.equalsIgnoreCase (search))
                { 
                    return sqlSearch;
                }
            }
                else 
                 clearSearch();
                 JOptionPane.showMessageDialog (null, "Error.\nPlease retype your search");
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return "";
    }
    
    public String getSampleProperty() 
    {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) 
    {
        
        
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) 
    {
        
        
    }
}
