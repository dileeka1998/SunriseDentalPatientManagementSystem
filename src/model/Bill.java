package Model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Bill {

    private int billId;
    private Appointment appointment;
    private BigDecimal consultationFee;
    private BigDecimal treatmentCost;
    private BigDecimal totalAmount;
    private Timestamp billedAt;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }

    public BigDecimal getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(BigDecimal treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getBilledAt() {
        return billedAt;
    }

    public void setBilledAt(Timestamp billedAt) {
        this.billedAt = billedAt;
    }
}
