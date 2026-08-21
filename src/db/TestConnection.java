/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author hnd
 */
public class TestConnection {
    
     public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {

            if (conn != null) {
                System.out.println("Connected to sunrise_db successfully!");
            }

        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
    
}
