package Controller;

import DAO.AppointmentDAO;
import Model.AppointmentStatus;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentController {

    public String validateInput(Object patient, Object dentist, Object treatment,
            String date, String time) {
        if (patient == null) {
            return "Select a patient";
        }
        if (dentist == null) {
            return "Select a dentist";
        }
        if (treatment == null) {
            return "Select a treatment";
        }
        try {
            LocalDate.parse(date == null ? "" : date.trim());
        } catch (Exception e) {
            return "Enter the date as YYYY-MM-DD";
        }
        try {
            String value = time == null ? "" : time.trim();
            if (!value.matches("[0-9]{2}:[0-9]{2}")) {
                throw new IllegalArgumentException();
            }
            LocalTime.parse(value);
        } catch (Exception e) {
            return "Enter the time as HH:MM";
        }
        return "VALID";
    }

    public String validateStatusChange(String currentStatus, String newStatus) {
        if (currentStatus == null || newStatus == null) {
            return "Select an appointment status";
        }
        if (currentStatus.equals(AppointmentStatus.COMPLETED.name())
                || currentStatus.equals(AppointmentStatus.CANCELLED.name())) {
            return "Completed or cancelled appointments cannot be changed";
        }
        if (currentStatus.equals(newStatus)) {
            return "Appointment already has this status";
        }
        return "VALID";
    }

    public String checkSlot(int dentistId, int patientId, String date,
            String time, int excludedAppointmentNo) throws Exception {
        boolean available = new AppointmentDAO().isSlotAvailable(dentistId, patientId,
                Date.valueOf(date), Time.valueOf(time + ":00"), excludedAppointmentNo);
        return available ? "AVAILABLE" : "This patient or dentist already has an appointment at that time";
    }
}
