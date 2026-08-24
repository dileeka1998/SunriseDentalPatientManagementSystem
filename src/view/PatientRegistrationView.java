package view;

import Controller.PatientController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PatientRegistrationView extends JDialog {

    private JTextField txtName;
    private JTextField txtContact;
    private JTextArea txtAddress;
    private JLabel lblMessage;
    private JButton btnValidate;
    private JButton btnSave;
    private JButton btnCancel;

    public PatientRegistrationView(JFrame parent) {
        super(parent, "Register patient", true);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JLabel lblTitle = new JLabel("Register patient");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        JLabel lblName = new JLabel("Full name");
        JLabel lblContact = new JLabel("Contact number");
        JLabel lblAddress = new JLabel("Address");
        txtName = new JTextField(28);
        txtContact = new JTextField(28);
        txtAddress = new JTextArea(4, 28);
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        lblName.setLabelFor(txtName);
        lblContact.setLabelFor(txtContact);
        lblAddress.setLabelFor(txtAddress);
        JScrollPane scrollAddress = new JScrollPane(txtAddress);
        lblMessage = new JLabel("Storage is not connected. Details will not be saved.");
        btnValidate = new JButton("Check details");
        btnSave = new JButton("Save patient");
        btnSave.setEnabled(false);
        btnSave.setToolTipText("Patient storage is not connected yet.");
        btnValidate.setBackground(new Color(36, 115, 66));
        btnValidate.setForeground(Color.WHITE);
        btnCancel = new JButton("Cancel");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(24)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(lblName).addComponent(txtName)
                    .addComponent(lblContact).addComponent(txtContact)
                    .addComponent(lblAddress).addComponent(scrollAddress)
                    .addComponent(lblMessage)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel).addGap(10)
                        .addComponent(btnValidate).addGap(10).addComponent(btnSave)))
                .addGap(24));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(24).addComponent(lblTitle).addGap(20)
                .addComponent(lblName).addGap(5)
                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(14).addComponent(lblContact).addGap(5)
                .addComponent(txtContact, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(14).addComponent(lblAddress).addGap(5)
                .addComponent(scrollAddress, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                .addGap(18).addComponent(lblMessage).addGap(18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel).addComponent(btnValidate).addComponent(btnSave))
                .addGap(24));

        btnValidate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                PatientController controller = new PatientController();
                String result = controller.validateInput(txtName.getText(),
                        txtAddress.getText(), txtContact.getText());
                if (result.equals("VALID")) {
                    lblMessage.setForeground(new Color(36, 115, 66));
                    lblMessage.setText("Details are valid. Nothing has been saved.");
                } else {
                    lblMessage.setForeground(new Color(160, 45, 45));
                    lblMessage.setText(result);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeForm();
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeForm();
            }
        });
        pack();
        setResizable(false);
    }

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
}
