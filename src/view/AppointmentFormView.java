package view;

import Controller.AppointmentController;
import DAO.*;
import Model.*;
import java.awt.Color;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class AppointmentFormView extends JDialog {

    private Appointment appointment;
    private Runnable afterSave;

    public AppointmentFormView(JFrame parent, Appointment appointment, Runnable afterSave) {
        super(parent, true);
        this.appointment = appointment;
        this.afterSave = afterSave;
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        loadChoices();
        if (appointment != null) {
            setTitle("Edit appointment");
            lblTitle.setText("Edit appointment #" + appointment.getAppointmentNo());
            selectItem(cmbPatient, appointment.getPatient().getPatientId());
            selectItem(cmbDentist, appointment.getDentist().getId());
            selectItem(cmbTreatment, appointment.getTreatment().getTreatmentId());
            txtDate.setText(appointment.getAppointmentDate().toString());
            txtTime.setText(appointment.getAppointmentTime().toString().substring(0, 5));
        }
        setLocationRelativeTo(parent);
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        lblTitle = new JLabel();
        pnlFields = new JPanel();
        lblPatient = new JLabel();
        cmbPatient = new JComboBox<Patient>();
        lblDentist = new JLabel();
        cmbDentist = new JComboBox<User>();
        lblTreatment = new JLabel();
        cmbTreatment = new JComboBox<Treatment>();
        lblDate = new JLabel();
        txtDate = new JTextField();
        lblTime = new JLabel();
        txtTime = new JTextField();
        pnlBottom = new JPanel();
        lblMessage = new JLabel();
        pnlButtons = new JPanel();
        btnCancel = new JButton();
        btnSave = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New appointment");
        setResizable(false);
        setLayout(new java.awt.BorderLayout(12, 16));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        lblTitle.setText("New appointment");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        add(lblTitle, java.awt.BorderLayout.NORTH);
        pnlFields.setOpaque(false);
        pnlFields.setLayout(new java.awt.GridLayout(5, 2, 12, 12));
        lblPatient.setText("Patient");
        pnlFields.add(lblPatient);
        pnlFields.add(cmbPatient);
        lblDentist.setText("Dentist");
        pnlFields.add(lblDentist);
        pnlFields.add(cmbDentist);
        lblTreatment.setText("Treatment");
        pnlFields.add(lblTreatment);
        pnlFields.add(cmbTreatment);
        lblDate.setText("Date (YYYY-MM-DD)");
        pnlFields.add(lblDate);
        txtDate.setColumns(18);
        txtDate.setText(LocalDate.now().plusDays(1).toString());
        pnlFields.add(txtDate);
        lblTime.setText("Time (HH:MM)");
        pnlFields.add(lblTime);
        txtTime.setText("09:00");
        pnlFields.add(txtTime);
        add(pnlFields, java.awt.BorderLayout.CENTER);
        pnlBottom.setOpaque(false);
        pnlBottom.setLayout(new java.awt.BorderLayout(8, 8));
        lblMessage.setText("Choose a 30 minute appointment time.");
        pnlBottom.add(lblMessage, java.awt.BorderLayout.NORTH);
        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(e -> dispose());
        pnlButtons.add(btnCancel);
        btnSave.setText("Save appointment");
        btnSave.setBackground(new Color(36, 115, 66));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveAppointment());
        pnlButtons.add(btnSave);
        pnlBottom.add(pnlButtons, java.awt.BorderLayout.SOUTH);
        add(pnlBottom, java.awt.BorderLayout.SOUTH);
        pack();
    }//GEN-END:initComponents

    private void loadChoices() {
        try {
            List<Patient> patients = new PatientDAO().search("");
            for (Patient patientItem : patients) {
                cmbPatient.addItem(patientItem);
            }
            for (User dentist : new UserDAO().findDentists()) {
                cmbDentist.addItem(dentist);
            }
            for (Treatment treatment : new TreatmentDAO().findAll()) {
                cmbTreatment.addItem(treatment);
            }
        } catch (Exception e) {
            showMessage("Unable to load appointment choices.", false);
            btnSave.setEnabled(false);
        }
    }

    private void saveAppointment() {
        Patient patientItem = (Patient) cmbPatient.getSelectedItem();
        User dentist = (User) cmbDentist.getSelectedItem();
        Treatment treatment = (Treatment) cmbTreatment.getSelectedItem();
        AppointmentController controller = new AppointmentController();
        String result = controller.validateInput(patientItem, dentist, treatment,
                txtDate.getText(), txtTime.getText());
        if (!"VALID".equals(result)) {
            showMessage(result, false);
            return;
        }
        try {
            int appointmentNo = appointment == null ? 0 : appointment.getAppointmentNo();
            result = controller.checkSlot(dentist.getId(), patientItem.getPatientId(),
                    txtDate.getText().trim(), txtTime.getText().trim(), appointmentNo);
            if (!"AVAILABLE".equals(result)) {
                showMessage(result, false);
                return;
            }
            Appointment value = appointment == null ? new Appointment() : appointment;
            value.setPatient(patientItem);
            value.setDentist(dentist);
            value.setTreatment(treatment);
            value.setAppointmentDate(Date.valueOf(txtDate.getText().trim()));
            value.setAppointmentTime(Time.valueOf(txtTime.getText().trim() + ":00"));
            if (appointment == null) {
                value.setStatus(AppointmentStatus.SCHEDULED.name());
                value.setAppointmentNo(new AppointmentDAO().insert(value));
            } else {
                new AppointmentDAO().update(value);
            }
            JOptionPane.showMessageDialog(this,
                    "Appointment #" + value.getAppointmentNo() + " saved successfully.",
                    "Appointment saved", JOptionPane.INFORMATION_MESSAGE);
            if (afterSave != null) {
                afterSave.run();
            }
            dispose();
        } catch (Exception e) {
            showMessage("Unable to save the appointment.", false);
        }
    }

    private void selectItem(JComboBox comboBox, int id) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            int itemId = item instanceof Patient ? ((Patient) item).getPatientId()
                    : item instanceof User ? ((User) item).getId()
                    : ((Treatment) item).getTreatmentId();
            if (itemId == id) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private void showMessage(String message, boolean success) {
        lblMessage.setForeground(success ? new Color(36, 115, 66) : new Color(160, 45, 45));
        lblMessage.setText(message);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCancel;
    private JButton btnSave;
    private JComboBox<User> cmbDentist;
    private JComboBox<Patient> cmbPatient;
    private JComboBox<Treatment> cmbTreatment;
    private JLabel lblDate;
    private JLabel lblDentist;
    private JLabel lblMessage;
    private JLabel lblPatient;
    private JLabel lblTime;
    private JLabel lblTitle;
    private JLabel lblTreatment;
    private JPanel pnlBottom;
    private JPanel pnlButtons;
    private JPanel pnlFields;
    private JTextField txtDate;
    private JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
