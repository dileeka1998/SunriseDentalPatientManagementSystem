package Controller;

import DAO.UserDAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class LoginController {


    private UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    // Validation method
    public String validateInput(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password is required";
        }

        if (username.length() < 3) {
            return "Username must contain at least 3 characters";
        }

        if (password.length() < 5) {
            return "Password must contain at least 5 characters";
        }

        return "VALID";
    }

    // Login method
    public String login(String username, String password) {

        String validationResult =
                validateInput(username, password);

        if (!validationResult.equals("VALID")) {
            return validationResult;
        }

        boolean authenticated =
                userDAO.authenticate(username, password);

        if (authenticated) {
            return "LOGIN_SUCCESS";
        }

        return "INVALID_CREDENTIALS";
    }
}