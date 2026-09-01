package DAO;

import Model.Treatment;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO {

    public List<Treatment> findAll() throws SQLException {
        List<Treatment> treatments = new ArrayList<Treatment>();
        String sql = "SELECT treatment_id, treatment_name, treatment_cost FROM treatment ORDER BY treatment_name";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                treatments.add(new Treatment(rs.getInt("treatment_id"),
                        rs.getString("treatment_name"), rs.getBigDecimal("treatment_cost")));
            }
        }
        return treatments;
    }
}
