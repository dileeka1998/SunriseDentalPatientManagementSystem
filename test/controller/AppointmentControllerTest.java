package controller;

import Controller.AppointmentController;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppointmentControllerTest {

    AppointmentController controller = new AppointmentController();
    Object patient = new Object();
    Object dentist = new Object();
    Object treatment = new Object();

    @Test
    public void testValidAppointment() {
        assertEquals("VALID", controller.validateInput(patient, dentist, treatment,
                "2026-09-02", "09:30"));
    }

    @Test
    public void testPatientRequired() {
        assertEquals("Select a patient", controller.validateInput(null, dentist, treatment,
                "2026-09-02", "09:30"));
    }

    @Test
    public void testDentistRequired() {
        assertEquals("Select a dentist", controller.validateInput(patient, null, treatment,
                "2026-09-02", "09:30"));
    }

    @Test
    public void testTreatmentRequired() {
        assertEquals("Select a treatment", controller.validateInput(patient, dentist, null,
                "2026-09-02", "09:30"));
    }

    @Test
    public void testInvalidDateFormat() {
        assertEquals("Enter the date as YYYY-MM-DD", controller.validateInput(patient,
                dentist, treatment, "02/09/2026", "09:30"));
    }

    @Test
    public void testInvalidCalendarDate() {
        assertEquals("Enter the date as YYYY-MM-DD", controller.validateInput(patient,
                dentist, treatment, "2026-02-30", "09:30"));
    }

    @Test
    public void testInvalidTimeFormat() {
        assertEquals("Enter the time as HH:MM", controller.validateInput(patient,
                dentist, treatment, "2026-09-02", "9.30"));
    }

    @Test
    public void testInvalidTimeRange() {
        assertEquals("Enter the time as HH:MM", controller.validateInput(patient,
                dentist, treatment, "2026-09-02", "25:00"));
    }

    @Test
    public void testConfirmScheduledAppointment() {
        assertEquals("VALID", controller.validateStatusChange("SCHEDULED", "CONFIRMED"));
    }

    @Test
    public void testCompleteConfirmedAppointment() {
        assertEquals("VALID", controller.validateStatusChange("CONFIRMED", "COMPLETED"));
    }

    @Test
    public void testCompletedAppointmentIsFinal() {
        assertEquals("Completed or cancelled appointments cannot be changed",
                controller.validateStatusChange("COMPLETED", "CONFIRMED"));
    }

    @Test
    public void testSameStatusRejected() {
        assertEquals("Appointment already has this status",
                controller.validateStatusChange("SCHEDULED", "SCHEDULED"));
    }
}
