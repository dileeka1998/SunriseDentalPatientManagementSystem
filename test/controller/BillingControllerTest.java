package controller;

import Controller.BillingController;
import Model.Appointment;
import Model.Bill;
import Model.Treatment;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

public class BillingControllerTest {

    BillingController controller = new BillingController();

    private Appointment appointment(String status) {
        Appointment appointment = new Appointment();
        appointment.setStatus(status);
        appointment.setTreatment(new Treatment(1, "Dental check-up",
                new BigDecimal("2500.00")));
        return appointment;
    }

    @Test
    public void testCompletedAppointmentCanBeBilled() {
        assertEquals("VALID", controller.validateBill(appointment("COMPLETED"), "1000.00"));
    }

    @Test
    public void testAppointmentRequired() {
        assertEquals("Find a completed appointment first", controller.validateBill(null, "1000.00"));
    }

    @Test
    public void testScheduledAppointmentCannotBeBilled() {
        assertEquals("Only completed appointments can be billed",
                controller.validateBill(appointment("SCHEDULED"), "1000.00"));
    }

    @Test
    public void testConfirmedAppointmentCannotBeBilled() {
        assertEquals("Only completed appointments can be billed",
                controller.validateBill(appointment("CONFIRMED"), "1000.00"));
    }

    @Test
    public void testNegativeConsultationFee() {
        assertEquals("Consultation fee cannot be negative",
                controller.validateBill(appointment("COMPLETED"), "-1"));
    }

    @Test
    public void testInvalidConsultationFee() {
        assertEquals("Enter a valid consultation fee",
                controller.validateBill(appointment("COMPLETED"), "one thousand"));
    }

    @Test
    public void testEmptyConsultationFee() {
        assertEquals("Enter a valid consultation fee",
                controller.validateBill(appointment("COMPLETED"), ""));
    }

    @Test
    public void testFactoryCalculatesTotal() {
        Bill bill = controller.calculateBill(appointment("COMPLETED"), "1000.00");
        assertEquals(new BigDecimal("3500.00"), bill.getTotalAmount());
    }
}
