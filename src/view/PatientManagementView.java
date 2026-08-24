package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientManagementView extends JPanel {

    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnRegister;
    private JButton btnEdit;
    private JTable tblPatients;

    public PatientManagementView(final JFrame parent) {
        setLayout(new BorderLayout(10, 20));
        setBackground(new Color(244, 248, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel pnlTop = new JPanel(new BorderLayout(10, 15));
        pnlTop.setOpaque(false);
        JLabel lblTitle = new JLabel("Patients");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlTop.add(lblTitle, BorderLayout.NORTH);
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlSearch.setOpaque(false);
        JLabel lblSearch = new JLabel("Patient ID, name or contact");
        txtSearch = new JTextField(18);
        lblSearch.setLabelFor(txtSearch);
        btnSearch = new JButton("Search");
        btnSearch.setEnabled(false);
        btnSearch.setToolTipText("Patient storage is not connected yet.");
        txtSearch.setEnabled(false);
        pnlSearch.add(lblSearch);
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);
        pnlTop.add(pnlSearch, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        tblPatients = new JTable(new DefaultTableModel(new Object[][] {},
                new String[] { "Patient ID", "Name", "Contact", "Address" }) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblPatients.setRowHeight(30);
        tblPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPatients = new JScrollPane(tblPatients);
        scrollPatients.getViewport().setBackground(Color.WHITE);
        add(scrollPatients, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout(10, 12));
        pnlBottom.setOpaque(false);
        pnlBottom.add(new JLabel("Patient storage is not connected. You can check the entry form."),
                BorderLayout.NORTH);
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setOpaque(false);
        btnRegister = new JButton("Register patient");
        btnRegister.setBackground(new Color(36, 115, 66));
        btnRegister.setForeground(Color.WHITE);
        btnEdit = new JButton("Edit patient");
        btnEdit.setEnabled(false);
        btnEdit.setToolTipText("Patient storage is not connected yet.");
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnRegister);
        pnlBottom.add(pnlButtons, BorderLayout.SOUTH);
        add(pnlBottom, BorderLayout.SOUTH);

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                new PatientRegistrationView(parent).setVisible(true);
            }
        });
    }
}
