package view;

import java.awt.Color;
import javax.swing.*;

public class HelpView extends JPanel {

    public HelpView() {
        initComponents();
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        lblTitle = new JLabel("Help");
        scrollHelp = new JScrollPane();
        txtHelp = new JTextArea();
        setBackground(new Color(244, 248, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setLayout(new java.awt.BorderLayout(10, 18));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        add(lblTitle, java.awt.BorderLayout.NORTH);
        txtHelp.setEditable(false);
        txtHelp.setLineWrap(true);
        txtHelp.setWrapStyleWord(true);
        txtHelp.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtHelp.setText("SUNRISE DENTAL - USER HELP\n\n"
                + "1. Patients\nStaff can search, register and edit patient details.\n\n"
                + "2. Appointments\nStaff can create an appointment by selecting the patient, dentist, treatment, date and time. Use Confirm, Complete or Cancel to update its status.\n\n"
                + "3. Dentist view\nDentists can view their appointments and mark a visit as completed or cancelled.\n\n"
                + "4. Billing\nStaff can enter a completed appointment number, add the consultation fee and generate or reprint the receipt.\n\n"
                + "5. Reports\nStaff can select a date range to view billing totals.\n\n"
                + "6. Exit\nUse Log out to return to the login screen or Exit to close the application safely.");
        scrollHelp.setViewportView(txtHelp);
        add(scrollHelp, java.awt.BorderLayout.CENTER);
    }//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel lblTitle;
    private JScrollPane scrollHelp;
    private JTextArea txtHelp;
    // End of variables declaration//GEN-END:variables
}
