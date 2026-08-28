package Controller;

import DAO.UserDAO;
import Model.User;
import java.sql.SQLException;

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
    private User authenticatedUser;

    public LoginController() {
        this(new UserDAO());
    }

    public LoginController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
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

    public String validateInput(String username, String password, String role) {

        String result = validateInput(username, password);

        if (!result.equals("VALID")) {
            return result;
        }

        if (!"Staff".equals(role) && !"Dentist".equals(role)) {
            return "Please select a role";
        }

        return "VALID";
    }

    // Login method
    public String login(String username, String password) {

        authenticatedUser = null;
        String validationResult =
                validateInput(username, password);

        if (!validationResult.equals("VALID")) {
            return validationResult;
        }

        return authenticateUser(username, password, null);
    }

    public String login(String username, String password, String role) {

        authenticatedUser = null;
        String result = validateInput(username, password, role);

        if (!result.equals("VALID")) {
            return result;
        }

        return authenticateUser(username, password, role);
    }

    private String authenticateUser(String username, String password, String role) {
        try {
            User user = userDAO.authenticate(username, password);

            if (user == null) {
                return "INVALID_CREDENTIALS";
            }

            String userType = user.getUserType();
            if (!"STAFF".equals(userType) && !"DENTIST".equals(userType)) {
                return "ROLE_MISMATCH";
            }

            if (role != null && !role.equalsIgnoreCase(userType)) {
                return "ROLE_MISMATCH";
            }

            authenticatedUser = user;
            return "LOGIN_SUCCESS";
        } catch (SQLException e) {
            return "DATABASE_ERROR";
        }
    }
}
