/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SunriseDentalPatientManagementSystem;

import db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import view.LoginView;

/**
 *
 * @author hnd
 */
public class TestAutomation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LoginView login = new LoginView();
            login.setVisible(true);
        } catch (Exception e) {
            System.err.println("Failed to open the application window. Please restart the application.");
            e.printStackTrace();
        }
    }
}
