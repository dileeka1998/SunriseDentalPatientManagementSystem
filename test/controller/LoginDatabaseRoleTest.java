package controller;

import Controller.LoginController;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginDatabaseRoleTest {

    LoginController controller = new LoginController();

    @Test
    public void testStoredStaffRole() {
        String result = controller.login("admin", "12345", "Staff");
        assertEquals("LOGIN_SUCCESS", result);
        assertEquals("STAFF", controller.getAuthenticatedUser().getUserType());
        assertEquals("admin", controller.getAuthenticatedUser().getUsername());
        assertNotNull(controller.getAuthenticatedUser().getName());
    }

    @Test
    public void testStoredDentistRole() {
        String result = controller.login("dentist1", "dentist123", "Dentist");
        assertEquals("LOGIN_SUCCESS", result);
        assertEquals("DENTIST", controller.getAuthenticatedUser().getUserType());
    }

    @Test
    public void testStaffWithWrongRole() {
        String result = controller.login("admin", "12345", "Dentist");
        assertEquals("ROLE_MISMATCH", result);
        assertNull(controller.getAuthenticatedUser());
    }

    @Test
    public void testDentistWithWrongRole() {
        String result = controller.login("dentist1", "dentist123", "Staff");
        assertEquals("ROLE_MISMATCH", result);
        assertNull(controller.getAuthenticatedUser());
    }
}
