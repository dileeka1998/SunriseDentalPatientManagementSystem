package view;

import Controller.BillingController;
import DAO.AppointmentDAO;
import DAO.BillDAO;
import Model.Appointment;
import Model.Bill;
import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.*;

public class BillingView extends JPanel {

    private Appointment appointment;
    private Bill bill;

    public BillingView() {
        initComponents();
        scrollReceipt.getViewport().setBackground(Color.WHITE);
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        lblTitle = new JLabel();
        pnlSearch = new JPanel();
        lblAppointmentNo = new JLabel();
        txtAppointmentNo = new JTextField();
        btnFind = new JButton();
        lblConsultationFee = new JLabel();
        txtConsultationFee = new JTextField();
        btnGenerate = new JButton();
        scrollReceipt = new JScrollPane();
        txtReceipt = new JTextArea();
        pnlBottom = new JPanel();
        lblMessage = new JLabel();
        btnPrint = new JButton();
        setBackground(new Color(244, 248, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setLayout(new java.awt.BorderLayout(10, 18));
        lblTitle.setText("Billing and receipt");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        pnlSearch.setOpaque(false);
        pnlSearch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        pnlSearch.add(lblTitle);
        lblAppointmentNo.setText("Appointment number");
        pnlSearch.add(lblAppointmentNo);
        txtAppointmentNo.setColumns(7);
        pnlSearch.add(txtAppointmentNo);
        btnFind.setText("Find");
        btnFind.addActionListener(e -> findAppointment());
        pnlSearch.add(btnFind);
        lblConsultationFee.setText("Consultation fee (Rs.)");
        pnlSearch.add(lblConsultationFee);
        txtConsultationFee.setColumns(9);
        txtConsultationFee.setText("1000.00");
        pnlSearch.add(txtConsultationFee);
        btnGenerate.setText("Generate bill");
        btnGenerate.setBackground(new Color(36, 115, 66));
        btnGenerate.setForeground(Color.WHITE);
        btnGenerate.addActionListener(e -> generateBill());
        pnlSearch.add(btnGenerate);
        add(pnlSearch, java.awt.BorderLayout.NORTH);
        txtReceipt.setEditable(false);
        txtReceipt.setFont(new java.awt.Font("Monospaced", 0, 14));
        txtReceipt.setText("Find a completed appointment to prepare its bill.");
        scrollReceipt.setViewportView(txtReceipt);
        add(scrollReceipt, java.awt.BorderLayout.CENTER);
        pnlBottom.setOpaque(false);
        pnlBottom.setLayout(new java.awt.BorderLayout());
        lblMessage.setText(" ");
        pnlBottom.add(lblMessage, java.awt.BorderLayout.WEST);
        btnPrint.setText("Print receipt");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(e -> printReceipt());
        pnlBottom.add(btnPrint, java.awt.BorderLayout.EAST);
        add(pnlBottom, java.awt.BorderLayout.SOUTH);
    }//GEN-END:initComponents

    private void findAppointment() {
        try {
            int number = Integer.parseInt(txtAppointmentNo.getText().trim());
            appointment = new AppointmentDAO().findByNumber(number);
            if (appointment == null) {
                showMessage("Appointment number was not found.", false);
                txtReceipt.setText("");
                return;
            }
            bill = new BillDAO().findByAppointment(number);
            if (bill != null) {
                bill.setAppointment(appointment);
                showReceipt(bill);
                showMessage("This bill already exists and can be reprinted.", true);
                btnGenerate.setEnabled(false);
                btnPrint.setEnabled(true);
            } else {
                txtReceipt.setText(appointmentDetails());
                showMessage("Appointment found. Enter the consultation fee.", true);
                btnGenerate.setEnabled(true);
                btnPrint.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            showMessage("Enter a valid appointment number.", false);
        } catch (Exception e) {
            showMessage("Unable to find the appointment.", false);
        }
    }

    private void generateBill() {
        BillingController controller = new BillingController();
        String result = controller.validateBill(appointment, txtConsultationFee.getText());
        if (!"VALID".equals(result)) {
            showMessage(result, false);
            return;
        }
        try {
            bill = controller.calculateBill(appointment, txtConsultationFee.getText());
            bill.setBillId(new BillDAO().insert(bill));
            showReceipt(bill);
            btnGenerate.setEnabled(false);
            btnPrint.setEnabled(true);
            showMessage("Bill generated successfully.", true);
        } catch (Exception e) {
            showMessage("Unable to generate the bill.", false);
        }
    }

    private String appointmentDetails() {
        return "SUNRISE DENTAL\n\nAppointment: #" + appointment.getAppointmentNo()
                + "\nPatient: " + appointment.getPatient().getName()
                + "\nDentist: " + appointment.getDentist().getName()
                + "\nTreatment: " + appointment.getTreatment().getTreatmentName()
                + "\nStatus: " + appointment.getStatus();
    }

    private void showReceipt(Bill value) {
        DecimalFormat money = new DecimalFormat("#,##0.00");
        txtReceipt.setText(appointmentDetails()
                + "\n\nTreatment cost: Rs. " + money.format(value.getTreatmentCost())
                + "\nConsultation fee: Rs. " + money.format(value.getConsultationFee())
                + "\n--------------------------------"
                + "\nTotal: Rs. " + money.format(value.getTotalAmount())
                + "\n\nThank you.");
    }

    private void printReceipt() {
        try {
            txtReceipt.print();
        } catch (Exception e) {
            showMessage("The receipt could not be printed.", false);
        }
    }

    private void showMessage(String message, boolean success) {
        lblMessage.setForeground(success ? new Color(36, 115, 66) : new Color(160, 45, 45));
        lblMessage.setText(message);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnFind, btnGenerate, btnPrint;
    private JLabel lblAppointmentNo, lblConsultationFee, lblMessage, lblTitle;
    private JPanel pnlBottom, pnlSearch;
    private JScrollPane scrollReceipt;
    private JTextField txtAppointmentNo, txtConsultationFee;
    private JTextArea txtReceipt;
    // End of variables declaration//GEN-END:variables
}
