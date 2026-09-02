package view;

import Controller.PatientController;
import Model.Patient;
import java.awt.Color;
import javax.swing.*;

public class PatientRegistrationView extends JDialog {

    private Patient patient;
    private Runnable afterSave;

    public PatientRegistrationView(JFrame parent) {
        this(parent, null, null);
    }

    public PatientRegistrationView(JFrame parent, Patient patient, Runnable afterSave) {
        super(parent, true);
        this.patient = patient;
        this.afterSave = afterSave;
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        btnSave.setEnabled(true);
        btnSave.setToolTipText(null);
        lblMessage.setText("Enter the patient details.");
        if (patient != null) {
            setTitle("Edit patient");
            lblTitle.setText("Edit patient");
            txtName.setText(patient.getName());
            txtContact.setText(patient.getContactNumber());
            txtAddress.setText(patient.getAddress());
        }
        setLocationRelativeTo(parent);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblContact = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        scrollAddress = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        lblMessage = new javax.swing.JLabel();
        btnValidate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Register patient");
        setBackground(new java.awt.Color(255, 255, 255));
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblTitle.setText("Register patient");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N

        lblName.setText("Full name");
        lblName.setLabelFor(txtName);

        txtName.setColumns(28);

        lblContact.setText("Contact number");
        lblContact.setLabelFor(txtContact);

        txtContact.setColumns(28);

        lblAddress.setText("Address");
        lblAddress.setLabelFor(txtAddress);

        txtAddress.setColumns(28);
        txtAddress.setRows(4);
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        scrollAddress.setViewportView(txtAddress);

        lblMessage.setText("Enter the patient details.");

        btnValidate.setText("Check details");
        btnValidate.setBackground(new java.awt.Color(36, 115, 66));
        btnValidate.setForeground(new java.awt.Color(255, 255, 255));
        btnValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidateActionPerformed(evt);
            }
        });

        btnSave.setText("Save patient");
        btnSave.setEnabled(true);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(lblName)
                    .addComponent(txtName)
                    .addComponent(lblContact)
                    .addComponent(txtContact)
                    .addComponent(lblAddress)
                    .addComponent(scrollAddress)
                    .addComponent(lblMessage)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel)
                        .addGap(10, 10, 10)
                        .addComponent(btnValidate)
                        .addGap(10, 10, 10)
                        .addComponent(btnSave)))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitle)
                .addGap(20, 20, 20)
                .addComponent(lblName)
                .addGap(5, 5, 5)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(lblContact)
                .addGap(5, 5, 5)
                .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(lblAddress)
                .addGap(5, 5, 5)
                .addComponent(scrollAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblMessage)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnValidate)
                    .addComponent(btnSave))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidateActionPerformed
        PatientController controller = new PatientController();
        String result = controller.validateInput(txtName.getText(),
                txtAddress.getText(), txtContact.getText());
        if (result.equals("VALID")) {
            lblMessage.setForeground(new Color(36, 115, 66));
            lblMessage.setText("Patient details are valid.");
        } else {
            lblMessage.setForeground(new Color(160, 45, 45));
            lblMessage.setText(result);
        }
    }//GEN-LAST:event_btnValidateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        PatientController controller = new PatientController();
        String result = controller.validateInput(txtName.getText(), txtAddress.getText(),
                txtContact.getText());
        if (!"VALID".equals(result)) {
            lblMessage.setForeground(new Color(160, 45, 45));
            lblMessage.setText(result);
            return;
        }
        try {
            boolean saved;
            if (patient == null) {
                saved = controller.savePatient(txtName.getText(), txtAddress.getText(),
                        txtContact.getText()) > 0;
            } else {
                saved = controller.updatePatient(patient.getPatientId(), txtName.getText(),
                        txtAddress.getText(), txtContact.getText());
            }
            if (saved) {
                JOptionPane.showMessageDialog(this, "Patient details saved successfully.",
                        "Patient saved", JOptionPane.INFORMATION_MESSAGE);
                if (afterSave != null) {
                    afterSave.run();
                }
                dispose();
            }
        } catch (Exception e) {
            lblMessage.setForeground(new Color(160, 45, 45));
            lblMessage.setText("Unable to save the patient. Please try again.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        closeForm();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        closeForm();
    }//GEN-LAST:event_formWindowClosing

    private void closeForm() {
        if (!txtName.getText().isEmpty() || !txtContact.getText().isEmpty()
                || !txtAddress.getText().isEmpty()) {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Discard these patient details?", "Close form", JOptionPane.YES_NO_OPTION);
            if (answer != JOptionPane.YES_OPTION) {
                return;
            }
        }
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnValidate;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrollAddress;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
