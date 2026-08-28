package controller;

import Controller.LoginController;
import DAO.UserDAO;
import Model.User;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginRoleTest {

    private LoginController createController(final String userType) {
        return new LoginController(new UserDAO() {
            public User authenticate(String username, String password) throws SQLException {
                if (!"kasun.perera".equals(username) || !"12345".equals(password)) {
                    return null;
                }
                User user = new User();
                user.setUsername(username);
                user.setName("Kasun Perera");
                user.setUserType(userType);
                return user;
            }
        });
    }

    @Test
    public void testStaffLogin() {
        LoginController controller = createController("STAFF");
        assertEquals("LOGIN_SUCCESS", controller.login("kasun.perera", "12345", "Staff"));
        assertEquals("STAFF", controller.getAuthenticatedUser().getUserType());
    }

    @Test
    public void testDentistLogin() {
        LoginController controller = createController("DENTIST");
        assertEquals("LOGIN_SUCCESS", controller.login("kasun.perera", "12345", "Dentist"));
        assertEquals("DENTIST", controller.getAuthenticatedUser().getUserType());
    }

    @Test
    public void testStaffSelectingDentist() {
        LoginController controller = createController("STAFF");
        assertEquals("ROLE_MISMATCH", controller.login("kasun.perera", "12345", "Dentist"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testDentistSelectingStaff() {
        LoginController controller = createController("DENTIST");
        assertEquals("ROLE_MISMATCH", controller.login("kasun.perera", "12345", "Staff"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testIncorrectPassword() {
        LoginController controller = createController("STAFF");
        assertEquals("INVALID_CREDENTIALS", controller.login("kasun.perera", "wrong123", "Dentist"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testUnsupportedStoredRole() {
        LoginController controller = createController("MANAGER");
        assertEquals("ROLE_MISMATCH", controller.login("kasun.perera", "12345", "Staff"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testMissingStoredRole() {
        LoginController controller = createController(null);
        assertEquals("ROLE_MISMATCH", controller.login("kasun.perera", "12345", "Staff"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testFailedLoginClearsUser() {
        LoginController controller = createController("STAFF");
        controller.login("kasun.perera", "12345", "Staff");
        assertEquals("INVALID_CREDENTIALS", controller.login("kasun.perera", "wrong123", "Staff"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testMissingRoleClearsUser() {
        LoginController controller = createController("STAFF");
        controller.login("kasun.perera", "12345", "Staff");
        assertEquals("Please select a role", controller.login("kasun.perera", "12345", "Select role"));
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testDatabaseError() {
        LoginController controller = new LoginController(new UserDAO() {
            public User authenticate(String username, String password) throws SQLException {
                throw new SQLException("Test connection failure");
            }
        });
        assertEquals("DATABASE_ERROR", controller.login("kasun.perera", "12345", "Staff"));
        assertNull(controller.getAuthenticatedUser());
    }
}
