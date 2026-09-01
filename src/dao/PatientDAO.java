package DAO;

import Model.Patient;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public List<Patient> search(String text) throws SQLException {
        List<Patient> patients = new ArrayList<Patient>();
        String sql = "SELECT patient_id, name, address, contact_number FROM patient "
                + "WHERE CAST(patient_id AS CHAR) LIKE ? OR name LIKE ? OR contact_number LIKE ? ORDER BY name";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            String value = "%" + (text == null ? "" : text.trim()) + "%";
            pst.setString(1, value);
            pst.setString(2, value);
            pst.setString(3, value);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    patients.add(readPatient(rs));
                }
            }
        }
        return patients;
    }

    public int insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patient(name, address, contact_number) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, patient.getName());
            pst.setString(2, patient.getAddress());
            pst.setString(3, patient.getContactNumber());
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE patient SET name = ?, address = ?, contact_number = ? WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, patient.getName());
            pst.setString(2, patient.getAddress());
            pst.setString(3, patient.getContactNumber());
            pst.setInt(4, patient.getPatientId());
            return pst.executeUpdate() == 1;
        }
    }

    private Patient readPatient(ResultSet rs) throws SQLException {
        return new Patient(rs.getInt("patient_id"), rs.getString("name"),
                rs.getString("address"), rs.getString("contact_number"));
    }
}
