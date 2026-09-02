package view;

import Model.User;
import Model.Appointment;
import DAO.AppointmentDAO;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainView extends JFrame {

    private boolean isStaff;
    private User signedInUser;

    public MainView(User user) {
        if (user == null || (!"STAFF".equals(user.getUserType())
                && !"DENTIST".equals(user.getUserType()))) {
            throw new IllegalArgumentException("An authorized user is required");
        }
        initComponents();
        signedInUser = user;
        isStaff = "STAFF".equals(user.getUserType());
        String role = isStaff ? "Staff" : "Dentist";
        lblUser.setText("Signed in: " + user.getUsername() + " (" + role + ")");
        lblRole.setText("Signed in as " + role);
        if (!isStaff) {
            btnToday.setText("My appointments");
            lblToday.setText("My appointments");
            pnlNavigation.remove(btnPatients);
            pnlPages.remove(pnlPatients);
            pnlNavigation.remove(btnAppointments);
            pnlNavigation.remove(btnBilling);
            pnlNavigation.remove(btnReports);
            pnlPages.remove(pnlAllAppointments);
            pnlPages.remove(pnlBilling);
            pnlPages.remove(pnlReports);
        }
        scrollAppointments.getViewport().setBackground(Color.WHITE);
        loadTodayAppointments();
        setLocationRelativeTo(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        pnlNavigation = new javax.swing.JPanel();
        btnToday = new javax.swing.JButton();
        btnPatients = new javax.swing.JButton();
        btnAppointments = new javax.swing.JButton();
        btnBilling = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        pnlPages = new javax.swing.JPanel();
        pnlToday = new javax.swing.JPanel();
        lblToday = new javax.swing.JLabel();
        scrollAppointments = new javax.swing.JScrollPane();
        tblAppointments = new javax.swing.JTable();
        lblAppointments = new javax.swing.JLabel();
        pnlPatients = new view.PatientManagementView();
        pnlAllAppointments = new view.AppointmentManagementView();
        pnlBilling = new view.BillingView();
        pnlReports = new view.ReportsView();
        pnlHelp = new view.HelpView();
        pnlFooter = new javax.swing.JPanel();
        lblRole = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sunrise Dental - Patient Management System");
        setMinimumSize(new java.awt.Dimension(760, 520));
        setPreferredSize(new java.awt.Dimension(900, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlHeader.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 15, 24));
        pnlHeader.setLayout(new java.awt.BorderLayout(15, 15));

        lblTitle.setText("Sunrise Dental");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(36, 115, 66));
        pnlHeader.add(lblTitle, java.awt.BorderLayout.WEST);

        lblUser.setText("Signed in");
        pnlHeader.add(lblUser, java.awt.BorderLayout.EAST);

        pnlNavigation.setBackground(new java.awt.Color(255, 255, 255));
        pnlNavigation.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        btnToday.setText("Today");
        btnToday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodayActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnToday);

        btnPatients.setText("Patients");
        btnPatients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatientsActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnPatients);

        btnAppointments.setText("Appointments");
        btnAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointmentsActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnAppointments);

        btnBilling.setText("Billing");
        btnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillingActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnBilling);

        btnReports.setText("Reports");
        btnReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportsActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnReports);

        btnHelp.setText("Help");
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        pnlNavigation.add(btnHelp);

        pnlHeader.add(pnlNavigation, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlHeader, java.awt.BorderLayout.NORTH);

        pnlPages.setLayout(new java.awt.CardLayout());

        pnlToday.setBackground(new java.awt.Color(244, 248, 244));
        pnlToday.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
        pnlToday.setLayout(new java.awt.BorderLayout(10, 20));

        lblToday.setText("Today's appointments");
        lblToday.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnlToday.add(lblToday, java.awt.BorderLayout.NORTH);

        tblAppointments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", "Time", "Patient", "Dentist", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAppointments.setRowHeight(30);
        scrollAppointments.setViewportView(tblAppointments);

        pnlToday.add(scrollAppointments, java.awt.BorderLayout.CENTER);

        lblAppointments.setText("Appointments are not connected yet.");
        pnlToday.add(lblAppointments, java.awt.BorderLayout.SOUTH);

        pnlPages.add(pnlToday, "today");
        pnlPages.add(pnlPatients, "patients");
        pnlPages.add(pnlAllAppointments, "appointments");
        pnlPages.add(pnlBilling, "billing");
        pnlPages.add(pnlReports, "reports");
        pnlPages.add(pnlHelp, "help");

        getContentPane().add(pnlPages, java.awt.BorderLayout.CENTER);

        pnlFooter.setBackground(new java.awt.Color(255, 255, 255));
        pnlFooter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 5));

        lblRole.setText("Signed in");
        pnlFooter.add(lblRole);

        btnLogout.setText("Log out");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        pnlFooter.add(btnLogout);

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        pnlFooter.add(btnExit);

        getContentPane().add(pnlFooter, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTodayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodayActionPerformed
        loadTodayAppointments();
        ((CardLayout) pnlPages.getLayout()).show(pnlPages, "today");
    }//GEN-LAST:event_btnTodayActionPerformed

    private void btnPatientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatientsActionPerformed
        if (isStaff) {
            ((CardLayout) pnlPages.getLayout()).show(pnlPages, "patients");
        }
    }//GEN-LAST:event_btnPatientsActionPerformed

    private void btnAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentsActionPerformed
        if (isStaff) {
            pnlAllAppointments.loadAppointments();
            ((CardLayout) pnlPages.getLayout()).show(pnlPages, "appointments");
        }
    }//GEN-LAST:event_btnAppointmentsActionPerformed

    private void btnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillingActionPerformed
        if (isStaff) {
            ((CardLayout) pnlPages.getLayout()).show(pnlPages, "billing");
        }
    }//GEN-LAST:event_btnBillingActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        if (isStaff) {
            ((CardLayout) pnlPages.getLayout()).show(pnlPages, "reports");
        }
    }//GEN-LAST:event_btnReportsActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        ((CardLayout) pnlPages.getLayout()).show(pnlPages, "help");
    }//GEN-LAST:event_btnHelpActionPerformed

    private void loadTodayAppointments() {
        DefaultTableModel model = (DefaultTableModel) tblAppointments.getModel();
        model.setRowCount(0);
        try {
            Integer dentistId = isStaff ? null : signedInUser.getId();
            List<Appointment> rows = new AppointmentDAO().search("", dentistId, true);
            for (Appointment appointment : rows) {
                model.addRow(new Object[]{appointment.getAppointmentNo(),
                    appointment.getAppointmentTime().toString().substring(0, 5),
                    appointment.getPatient().getName(), appointment.getDentist().getName(),
                    appointment.getStatus()});
            }
            lblAppointments.setForeground(new Color(36, 115, 66));
            lblAppointments.setText(rows.size() + " appointment(s) scheduled for today.");
        } catch (Exception e) {
            lblAppointments.setForeground(new Color(160, 45, 45));
            lblAppointments.setText("Unable to load today's appointments.");
        }
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int answer = JOptionPane.showConfirmDialog(this,
                "Log out of Sunrise Dental?", "Log out", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            new LoginView().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        exitApplication();
    }//GEN-LAST:event_btnExitActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitApplication();
    }//GEN-LAST:event_formWindowClosing

    private void exitApplication() {
        int answer = JOptionPane.showConfirmDialog(this,
                "Close Sunrise Dental?", "Exit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnAppointments;
    private javax.swing.JButton btnBilling;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPatients;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnToday;
    private javax.swing.JLabel lblAppointments;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblToday;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlNavigation;
    private javax.swing.JPanel pnlPages;
    private view.PatientManagementView pnlPatients;
    private view.AppointmentManagementView pnlAllAppointments;
    private view.BillingView pnlBilling;
    private view.HelpView pnlHelp;
    private view.ReportsView pnlReports;
    private javax.swing.JPanel pnlToday;
    private javax.swing.JScrollPane scrollAppointments;
    private javax.swing.JTable tblAppointments;
    // End of variables declaration//GEN-END:variables
}
