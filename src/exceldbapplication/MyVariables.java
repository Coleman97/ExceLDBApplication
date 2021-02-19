/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceldbapplication;

import java.sql.*;


/**
 *
 * @author Nrg_ColdColean
 */
public class MyVariables {
    String url = "jdbc:derby://localhost:1527/StateInfoDatabase";
        String username  = "Coleman";
        String password = "messi10barca";
        
        String excelFileLocation = "C:\\Users\\Nrg_ColdColean\\Downloads\\state_lga.xlsx";
        int filesize = 58;
        
        Connection myConnection = null;
        Statement myStatement = null; 
        ResultSet myResult = null;
        ResultSetMetaData myMetadata = null;
}
    