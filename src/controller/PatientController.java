package Controller;

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
}
