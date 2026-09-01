package Controller;

import Factory.BillFactory;
import Model.Appointment;
import Model.Bill;
import java.math.BigDecimal;

public class BillingController {

    public String validateBill(Appointment appointment, String consultationFee) {
        if (appointment == null) {
            return "Find a completed appointment first";
        }
        if (!"COMPLETED".equals(appointment.getStatus())) {
            return "Only completed appointments can be billed";
        }
        try {
            BigDecimal fee = new BigDecimal(consultationFee == null ? "" : consultationFee.trim());
            if (fee.compareTo(BigDecimal.ZERO) < 0) {
                return "Consultation fee cannot be negative";
            }
        } catch (Exception e) {
            return "Enter a valid consultation fee";
        }
        return "VALID";
    }

    public Bill calculateBill(Appointment appointment, String consultationFee) {
        return new BillFactory().createBill(appointment, new BigDecimal(consultationFee.trim()));
    }
}
