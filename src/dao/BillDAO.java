package DAO;

import Model.*;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public Bill findByAppointment(int appointmentNo) throws SQLException {
        String sql = "SELECT bill_id, consultation_fee, treatment_cost, total_amount, billed_at "
                + "FROM bill WHERE appointment_no = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, appointmentNo);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setConsultationFee(rs.getBigDecimal("consultation_fee"));
                    bill.setTreatmentCost(rs.getBigDecimal("treatment_cost"));
                    bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                    bill.setBilledAt(rs.getTimestamp("billed_at"));
                    return bill;
                }
            }
        }
        return null;
    }

    public int insert(Bill bill) throws SQLException {
        String sql = "INSERT INTO bill(appointment_no, consultation_fee, treatment_cost, total_amount) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, bill.getAppointment().getAppointmentNo());
            pst.setBigDecimal(2, bill.getConsultationFee());
            pst.setBigDecimal(3, bill.getTreatmentCost());
            pst.setBigDecimal(4, bill.getTotalAmount());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<Object[]> report(Date from, Date to) throws SQLException {
        List<Object[]> rows = new ArrayList<Object[]>();
        String sql = "SELECT b.bill_id, a.appointment_no, p.name, b.total_amount, b.billed_at "
                + "FROM bill b JOIN appointment a ON b.appointment_no = a.appointment_no "
                + "JOIN patient p ON a.patient_id = p.patient_id "
                + "WHERE DATE(b.billed_at) BETWEEN ? AND ? ORDER BY b.billed_at";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDate(1, from);
            pst.setDate(2, to);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{rs.getInt("bill_id"), rs.getInt("appointment_no"),
                        rs.getString("name"), rs.getBigDecimal("total_amount"), rs.getTimestamp("billed_at")});
                }
            }
        }
        return rows;
    }
}
