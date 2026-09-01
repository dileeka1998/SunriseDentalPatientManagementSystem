package DAO;

import Model.*;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public List<Appointment> search(String text, Integer dentistId, boolean todayOnly) throws SQLException {
        List<Appointment> appointments = new ArrayList<Appointment>();
        String sql = "SELECT a.appointment_no, a.appointment_date, a.appointment_time, a.status, "
                + "p.patient_id, p.name patient_name, p.address, p.contact_number, "
                + "u.id dentist_id, u.username, u.name dentist_name, u.user_type, "
                + "t.treatment_id, t.treatment_name, t.treatment_cost "
                + "FROM appointment a JOIN patient p ON a.patient_id = p.patient_id "
                + "JOIN user u ON a.dentist_id = u.id "
                + "JOIN treatment t ON a.treatment_id = t.treatment_id "
                + "WHERE (CAST(a.appointment_no AS CHAR) LIKE ? OR p.name LIKE ?) "
                + (dentistId == null ? "" : "AND a.dentist_id = ? ")
                + (todayOnly ? "AND a.appointment_date = CURDATE() " : "")
                + "ORDER BY a.appointment_date, a.appointment_time";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            String value = "%" + (text == null ? "" : text.trim()) + "%";
            pst.setString(1, value);
            pst.setString(2, value);
            if (dentistId != null) {
                pst.setInt(3, dentistId);
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    appointments.add(readAppointment(rs));
                }
            }
        }
        return appointments;
    }

    public Appointment findByNumber(int appointmentNo) throws SQLException {
        List<Appointment> appointments = search(String.valueOf(appointmentNo), null, false);
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentNo() == appointmentNo) {
                return appointment;
            }
        }
        return null;
    }

    public boolean isSlotAvailable(int dentistId, int patientId, Date date,
            Time time, int excludedAppointmentNo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM appointment WHERE appointment_date = ? "
                + "AND appointment_time = ? AND status IN ('SCHEDULED','CONFIRMED') "
                + "AND (dentist_id = ? OR patient_id = ?) AND appointment_no <> ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDate(1, date);
            pst.setTime(2, time);
            pst.setInt(3, dentistId);
            pst.setInt(4, patientId);
            pst.setInt(5, excludedAppointmentNo);
            try (ResultSet rs = pst.executeQuery()) {
                rs.next();
                return rs.getInt(1) == 0;
            }
        }
    }

    public int insert(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment(patient_id, dentist_id, treatment_id, "
                + "appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setValues(pst, appointment);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public boolean update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointment SET patient_id = ?, dentist_id = ?, treatment_id = ?, "
                + "appointment_date = ?, appointment_time = ?, status = ? WHERE appointment_no = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            setValues(pst, appointment);
            pst.setInt(7, appointment.getAppointmentNo());
            return pst.executeUpdate() == 1;
        }
    }

    public boolean updateStatus(int appointmentNo, String status) throws SQLException {
        String sql = "UPDATE appointment SET status = ? WHERE appointment_no = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setInt(2, appointmentNo);
            return pst.executeUpdate() == 1;
        }
    }

    private void setValues(PreparedStatement pst, Appointment appointment) throws SQLException {
        pst.setInt(1, appointment.getPatient().getPatientId());
        pst.setInt(2, appointment.getDentist().getId());
        pst.setInt(3, appointment.getTreatment().getTreatmentId());
        pst.setDate(4, appointment.getAppointmentDate());
        pst.setTime(5, appointment.getAppointmentTime());
        pst.setString(6, appointment.getStatus());
    }

    private Appointment readAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentNo(rs.getInt("appointment_no"));
        appointment.setAppointmentDate(rs.getDate("appointment_date"));
        appointment.setAppointmentTime(rs.getTime("appointment_time"));
        appointment.setStatus(rs.getString("status"));
        appointment.setPatient(new Patient(rs.getInt("patient_id"), rs.getString("patient_name"),
                rs.getString("address"), rs.getString("contact_number")));
        User dentist = new User();
        dentist.setId(rs.getInt("dentist_id"));
        dentist.setUsername(rs.getString("username"));
        dentist.setName(rs.getString("dentist_name"));
        dentist.setUserType(rs.getString("user_type"));
        appointment.setDentist(dentist);
        appointment.setTreatment(new Treatment(rs.getInt("treatment_id"),
                rs.getString("treatment_name"), rs.getBigDecimal("treatment_cost")));
        return appointment;
    }
}
