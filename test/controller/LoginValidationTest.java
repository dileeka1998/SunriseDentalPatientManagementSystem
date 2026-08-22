package controller;

import Controller.LoginController;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginValidationTest {

    LoginController controller = new LoginController();

    @Test
    public void testStaffRole() {
        String result = controller.validateInput("admin", "12345", "Staff");
        assertEquals("VALID", result);
    }

    @Test
    public void testDentistRole() {
        String result = controller.validateInput("dentist", "12345", "Dentist");
        assertEquals("VALID", result);
    }

    @Test
    public void testMissingRole() {
        String result = controller.validateInput("admin", "12345", "Select role");
        assertEquals("Please select a role", result);
    }

    @Test
    public void testInvalidRole() {
        String result = controller.validateInput("admin", "12345", "Manager");
        assertEquals("Please select a role", result);
    }

    @Test
    public void testNullRole() {
        String result = controller.validateInput("admin", "12345", null);
        assertEquals("Please select a role", result);
    }

    @Test
    public void testEmptyUsername() {
        String result = controller.validateInput(" ", "12345", "Staff");
        assertEquals("Username is required", result);
    }

    @Test
    public void testShortPassword() {
        String result = controller.validateInput("admin", "123", "Staff");
        assertEquals("Password must contain at least 5 characters", result);
    }
}
