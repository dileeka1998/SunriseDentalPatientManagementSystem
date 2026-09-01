package Factory;

import Model.Appointment;
import Model.Bill;
import java.math.BigDecimal;

public class BillFactory {

    public Bill createBill(Appointment appointment, BigDecimal consultationFee) {
        Bill bill = new Bill();
        BigDecimal treatmentCost = appointment.getTreatment().getTreatmentCost();
        bill.setAppointment(appointment);
        bill.setConsultationFee(consultationFee);
        bill.setTreatmentCost(treatmentCost);
        bill.setTotalAmount(treatmentCost.add(consultationFee));
        return bill;
    }
}
