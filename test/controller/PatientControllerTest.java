package controller;

import Controller.PatientController;
import org.junit.Test;
import static org.junit.Assert.*;

public class PatientControllerTest {

    PatientController controller = new PatientController();

    @Test
    public void testValidPatient() {
        String result = controller.validateInput("Nimal Perera", "12 Main Road", "0771234567");
        assertEquals("VALID", result);
    }

    @Test
    public void testEmptyName() {
        String result = controller.validateInput(" ", "12 Main Road", "0771234567");
        assertEquals("Patient name is required", result);
    }

    @Test
    public void testEmptyAddress() {
        String result = controller.validateInput("Nimal Perera", "", "0771234567");
        assertEquals("Address is required", result);
    }

    @Test
    public void testEmptyContactNumber() {
        String result = controller.validateInput("Nimal Perera", "12 Main Road", null);
        assertEquals("Contact number is required", result);
    }

    @Test
    public void testShortContactNumber() {
        String result = controller.validateInput("Nimal Perera", "12 Main Road", "077123");
        assertEquals("Enter a 10 digit contact number starting with 0", result);
    }

    @Test
    public void testContactNumberWithLetters() {
        String result = controller.validateInput("Nimal Perera", "12 Main Road", "077123456a");
        assertEquals("Enter a 10 digit contact number starting with 0", result);
    }

    @Test
    public void testContactNumberWithoutZero() {
        String result = controller.validateInput("Nimal Perera", "12 Main Road", "1771234567");
        assertEquals("Enter a 10 digit contact number starting with 0", result);
    }

    @Test
    public void testNameWithApostrophe() {
        String result = controller.validateInput("Anne O'Neil", "12 Main Road", "0112345678");
        assertEquals("VALID", result);
    }
}
