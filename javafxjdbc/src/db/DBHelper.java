/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author user
 */
public class DBHelper {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DATABASE = "Prakpbo";
    private static final String MYCONNECTION = "jdbc:mysql://localhost/" + DATABASE;
    
    public static Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(MYCONNECTION, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
}
