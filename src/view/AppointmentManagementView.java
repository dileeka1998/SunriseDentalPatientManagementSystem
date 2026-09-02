package view;

import Controller.AppointmentController;
import DAO.AppointmentDAO;
import Model.Appointment;
import Model.User;
import java.awt.Color;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentManagementView extends JPanel {

    private User user;
    private boolean todayOnly;
    private List<Appointment> appointments;

    public AppointmentManagementView() {
        this(null, false);
    }

    public AppointmentManagementView(User user, boolean todayOnly) {
        this.user = user;
        this.todayOnly = todayOnly;
        initComponents();
        scrollAppointments.getViewport().setBackground(Color.WHITE);
        tblAppointments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (todayOnly) {
            lblTitle.setText(user != null && "DENTIST".equals(user.getUserType())
                    ? "My appointments" : "Today's appointments");
        }
        if (user != null && "DENTIST".equals(user.getUserType())) {
            btnRegister.setVisible(false);
            btnEdit.setVisible(false);
            btnConfirm.setVisible(false);
        }
        loadAppointments();
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        pnlTop = new JPanel();
        lblTitle = new JLabel();
        pnlSearch = new JPanel();
        txtSearch = new JTextField();
        btnSearch = new JButton();
        scrollAppointments = new JScrollPane();
        tblAppointments = new JTable();
        pnlBottom = new JPanel();
        lblMessage = new JLabel();
        pnlButtons = new JPanel();
        btnRegister = new JButton();
        btnEdit = new JButton();
        btnView = new JButton();
        btnConfirm = new JButton();
        btnComplete = new JButton();
        btnCancel = new JButton();

        setBackground(new Color(244, 248, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setLayout(new java.awt.BorderLayout(10, 18));
        pnlTop.setOpaque(false);
        pnlTop.setLayout(new java.awt.BorderLayout(10, 10));
        lblTitle.setText("Appointments");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        pnlTop.add(lblTitle, java.awt.BorderLayout.NORTH);
        pnlSearch.setOpaque(false);
        pnlSearch.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        txtSearch.setColumns(20);
        txtSearch.setToolTipText("Appointment number or patient name");
        pnlSearch.add(txtSearch);
        btnSearch.setText("Search");
        btnSearch.addActionListener(e -> loadAppointments());
        pnlSearch.add(btnSearch);
        pnlTop.add(pnlSearch, java.awt.BorderLayout.CENTER);
        add(pnlTop, java.awt.BorderLayout.NORTH);

        tblAppointments.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"Number", "Date", "Time", "Patient", "Dentist", "Treatment", "Status"}) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblAppointments.setRowHeight(28);
        scrollAppointments.setViewportView(tblAppointments);
        add(scrollAppointments, java.awt.BorderLayout.CENTER);

        pnlBottom.setOpaque(false);
        pnlBottom.setLayout(new java.awt.BorderLayout(10, 8));
        lblMessage.setText(" ");
        pnlBottom.add(lblMessage, java.awt.BorderLayout.NORTH);
        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 0));
        btnRegister.setText("New appointment");
        btnRegister.setBackground(new Color(36, 115, 66));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.addActionListener(e -> openForm(null));
        pnlButtons.add(btnRegister);
        btnView.setText("View details");
        btnView.addActionListener(e -> viewDetails());
        pnlButtons.add(btnView);
        btnEdit.setText("Edit");
        btnEdit.addActionListener(e -> editSelected());
        pnlButtons.add(btnEdit);
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(e -> changeStatus("CONFIRMED"));
        pnlButtons.add(btnConfirm);
        btnComplete.setText("Complete");
        btnComplete.addActionListener(e -> changeStatus("COMPLETED"));
        pnlButtons.add(btnComplete);
        btnCancel.setText("Cancel appointment");
        btnCancel.addActionListener(e -> changeStatus("CANCELLED"));
        pnlButtons.add(btnCancel);
        pnlBottom.add(pnlButtons, java.awt.BorderLayout.SOUTH);
        add(pnlBottom, java.awt.BorderLayout.SOUTH);
    }//GEN-END:initComponents

    public void loadAppointments() {
        DefaultTableModel model = (DefaultTableModel) tblAppointments.getModel();
        model.setRowCount(0);
        try {
            Integer dentistId = user != null && "DENTIST".equals(user.getUserType())
                    ? user.getId() : null;
            appointments = new AppointmentDAO().search(txtSearch.getText(), dentistId, todayOnly);
            for (Appointment appointment : appointments) {
                model.addRow(new Object[]{appointment.getAppointmentNo(), appointment.getAppointmentDate(),
                    appointment.getAppointmentTime().toString().substring(0, 5),
                    appointment.getPatient().getName(), appointment.getDentist().getName(),
                    appointment.getTreatment().getTreatmentName(), appointment.getStatus()});
            }
            showMessage(appointments.size() + " appointment(s) found.", true);
        } catch (Exception e) {
            showMessage("Unable to load appointments. Please check the database.", false);
        }
    }

    private Appointment getSelectedAppointment() {
        int row = tblAppointments.getSelectedRow();
        if (row < 0 || appointments == null) {
            JOptionPane.showMessageDialog(this, "Select an appointment first.",
                    "Appointment required", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return appointments.get(tblAppointments.convertRowIndexToModel(row));
    }

    private void openForm(Appointment appointment) {
        if (appointment != null && ("COMPLETED".equals(appointment.getStatus())
                || "CANCELLED".equals(appointment.getStatus()))) {
            showMessage("Completed or cancelled appointments cannot be edited.", false);
            return;
        }
        JFrame owner = SwingUtilities.getWindowAncestor(this) instanceof JFrame
                ? (JFrame) SwingUtilities.getWindowAncestor(this) : null;
        new AppointmentFormView(owner, appointment, new Runnable() {
            public void run() {
                loadAppointments();
            }
        }).setVisible(true);
    }

    private void editSelected() {
        Appointment appointment = getSelectedAppointment();
        if (appointment != null) {
            openForm(appointment);
        }
    }

    private void viewDetails() {
        Appointment value = getSelectedAppointment();
        if (value == null) {
            return;
        }
        String details = "Appointment: #" + value.getAppointmentNo()
                + "\nDate and time: " + value.getAppointmentDate() + " "
                + value.getAppointmentTime().toString().substring(0, 5)
                + "\nPatient: " + value.getPatient().getName()
                + "\nContact: " + value.getPatient().getContactNumber()
                + "\nAddress: " + value.getPatient().getAddress()
                + "\nDentist: " + value.getDentist().getName()
                + "\nTreatment: " + value.getTreatment().getTreatmentName()
                + "\nStatus: " + value.getStatus();
        JOptionPane.showMessageDialog(this, details, "Appointment details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void changeStatus(String status) {
        Appointment appointment = getSelectedAppointment();
        if (appointment == null) {
            return;
        }
        String result = new AppointmentController().validateStatusChange(appointment.getStatus(), status);
        if (!"VALID".equals(result)) {
            showMessage(result, false);
            return;
        }
        if ("CANCELLED".equals(status)) {
            int answer = JOptionPane.showConfirmDialog(this, "Cancel this appointment?",
                    "Cancel appointment", JOptionPane.YES_NO_OPTION);
            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }
        try {
            new AppointmentDAO().updateStatus(appointment.getAppointmentNo(), status);
            loadAppointments();
            showMessage("Appointment status changed to " + status + ".", true);
        } catch (Exception e) {
            showMessage("Unable to update the appointment.", false);
        }
    }

    private void showMessage(String message, boolean success) {
        lblMessage.setForeground(success ? new Color(36, 115, 66) : new Color(160, 45, 45));
        lblMessage.setText(message);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnCancel;
    private JButton btnComplete;
    private JButton btnConfirm;
    private JButton btnEdit;
    private JButton btnRegister;
    private JButton btnSearch;
    private JButton btnView;
    private JLabel lblMessage;
    private JLabel lblTitle;
    private JPanel pnlBottom;
    private JPanel pnlButtons;
    private JPanel pnlSearch;
    private JPanel pnlTop;
    private JScrollPane scrollAppointments;
    private JTable tblAppointments;
    private JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
