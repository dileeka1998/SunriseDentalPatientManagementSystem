package Controller;

import DAO.PatientDAO;
import Model.Patient;
import java.sql.SQLException;
import java.util.List;

public class PatientController {

    public String validateInput(String name, String address, String contactNumber) {

        if (name == null || name.trim().isEmpty()) {
            return "Patient name is required";
        }

        if (address == null || address.trim().isEmpty()) {
            return "Address is required";
        }

        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return "Contact number is required";
        }

        if (!contactNumber.trim().matches("0[0-9]{9}")) {
            return "Enter a 10 digit contact number starting with 0";
        }

        return "VALID";
    }

    public int savePatient(String name, String address, String contactNumber) throws SQLException {
        Patient patient = new Patient(0, name.trim(), address.trim(), contactNumber.trim());
        return new PatientDAO().insert(patient);
    }

    public boolean updatePatient(int patientId, String name, String address,
            String contactNumber) throws SQLException {
        Patient patient = new Patient(patientId, name.trim(), address.trim(), contactNumber.trim());
        return new PatientDAO().update(patient);
    }

    public List<Patient> searchPatients(String text) throws SQLException {
        return new PatientDAO().search(text);
    }
}
