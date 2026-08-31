package view;

import java.awt.Color;
import javax.swing.*;

public class PatientManagementView extends JPanel {

    private JFrame parent;

    public PatientManagementView() {
        this(null);
    }

    public PatientManagementView(JFrame parent) {
        this.parent = parent;
        initComponents();
        tblPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPatients.getViewport().setBackground(Color.WHITE);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        pnlSearch = new javax.swing.JPanel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        scrollPatients = new javax.swing.JScrollPane();
        tblPatients = new javax.swing.JTable();
        pnlBottom = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();

        setBackground(new java.awt.Color(244, 248, 244));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 24, 24));
        this.setLayout(new java.awt.BorderLayout(10, 20));

        pnlTop.setOpaque(false);
        pnlTop.setLayout(new java.awt.BorderLayout(10, 15));

        lblTitle.setText("Patients");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));

        pnlTop.add(lblTitle, java.awt.BorderLayout.NORTH);

        pnlSearch.setOpaque(false);
        pnlSearch.setLayout(new java.awt.FlowLayout(0, 8, 0));

        lblSearch.setText("Patient ID, name or contact");
        lblSearch.setLabelFor(txtSearch);

        pnlSearch.add(lblSearch);

        txtSearch.setColumns(18);
        txtSearch.setEnabled(false);

        pnlSearch.add(txtSearch);

        btnSearch.setText("Search");
        btnSearch.setEnabled(false);
        btnSearch.setToolTipText("Patient storage is not connected yet.");

        pnlSearch.add(btnSearch);

        pnlTop.add(pnlSearch, java.awt.BorderLayout.CENTER);

        this.add(pnlTop, java.awt.BorderLayout.NORTH);

        tblPatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Patient ID", "Name", "Contact", "Address" }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        tblPatients.setRowHeight(30);

        scrollPatients.setViewportView(tblPatients);

        this.add(scrollPatients, java.awt.BorderLayout.CENTER);

        pnlBottom.setOpaque(false);
        pnlBottom.setLayout(new java.awt.BorderLayout(10, 12));

        lblMessage.setText("Patient storage is not connected. You can check the entry form.");

        pnlBottom.add(lblMessage, java.awt.BorderLayout.NORTH);

        pnlButtons.setOpaque(false);
        pnlButtons.setLayout(new java.awt.FlowLayout(2, 5, 5));

        btnEdit.setText("Edit patient");
        btnEdit.setEnabled(false);
        btnEdit.setToolTipText("Patient storage is not connected yet.");

        pnlButtons.add(btnEdit);

        btnRegister.setText("Register patient");
        btnRegister.setBackground(new java.awt.Color(36, 115, 66));
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        pnlButtons.add(btnRegister);

        pnlBottom.add(pnlButtons, java.awt.BorderLayout.SOUTH);

        this.add(pnlBottom, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        JFrame owner = parent;
        if (owner == null && SwingUtilities.getWindowAncestor(this) instanceof JFrame) {
            owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        }
        new PatientRegistrationView(owner).setVisible(true);
    }//GEN-LAST:event_btnRegisterActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JScrollPane scrollPatients;
    private javax.swing.JTable tblPatients;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
