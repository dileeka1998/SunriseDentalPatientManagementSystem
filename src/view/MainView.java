package view;

import Model.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainView extends JFrame {

    private JPanel pnlPages;
    private CardLayout pages;
    private JButton btnToday;
    private JButton btnPatients;
    private JButton btnLogout;
    private JButton btnExit;

    public MainView(User user) {
        if (user == null || (!"STAFF".equals(user.getUserType())
                && !"DENTIST".equals(user.getUserType()))) {
            throw new IllegalArgumentException("An authorized user is required");
        }
        initComponents(user);
    }

    private void initComponents(User user) {
        final boolean isStaff = "STAFF".equals(user.getUserType());
        String role = isStaff ? "Staff" : "Dentist";
        setTitle("Sunrise Dental - Patient Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new java.awt.Dimension(760, 520));
        setLocationRelativeTo(null);

        JPanel pnlHeader = new JPanel(new BorderLayout(15, 15));
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 24, 15, 24));
        JLabel lblTitle = new JLabel("Sunrise Dental");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(36, 115, 66));
        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(new JLabel("Signed in: " + user.getUsername() + " (" + role + ")"),
                BorderLayout.EAST);

        JPanel pnlNavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlNavigation.setBackground(Color.WHITE);
        btnToday = new JButton(isStaff ? "Today" : "My appointments");
        btnPatients = new JButton("Patients");
        pnlNavigation.add(btnToday);
        if (isStaff) {
            pnlNavigation.add(btnPatients);
        }
        pnlHeader.add(pnlNavigation, BorderLayout.SOUTH);
        add(pnlHeader, BorderLayout.NORTH);

        pages = new CardLayout();
        pnlPages = new JPanel(pages);
        JPanel pnlToday = new JPanel(new BorderLayout(10, 20));
        pnlToday.setBackground(new Color(244, 248, 244));
        pnlToday.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        JLabel lblToday = new JLabel(isStaff ? "Today's appointments" : "My appointments");
        lblToday.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlToday.add(lblToday, BorderLayout.NORTH);
        JTable tblAppointments = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Number", "Time", "Patient", "Dentist", "Status" }) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblAppointments.setRowHeight(30);
        JScrollPane scrollAppointments = new JScrollPane(tblAppointments);
        scrollAppointments.getViewport().setBackground(Color.WHITE);
        pnlToday.add(scrollAppointments, BorderLayout.CENTER);
        pnlToday.add(new JLabel("Appointments are not connected yet."), BorderLayout.SOUTH);
        pnlPages.add(pnlToday, "today");
        if (isStaff) {
            pnlPages.add(new PatientManagementView(this), "patients");
        }
        add(pnlPages, BorderLayout.CENTER);

        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.add(new JLabel("Signed in as " + role));
        btnLogout = new JButton("Log out");
        btnExit = new JButton("Exit");
        pnlFooter.add(btnLogout);
        pnlFooter.add(btnExit);
        add(pnlFooter, BorderLayout.SOUTH);

        btnToday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                pages.show(pnlPages, "today");
            }
        });
        btnPatients.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (isStaff) {
                    pages.show(pnlPages, "patients");
                }
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int answer = JOptionPane.showConfirmDialog(MainView.this,
                        "Log out of Sunrise Dental?", "Log out", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    new LoginView().setVisible(true);
                    dispose();
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitApplication();
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitApplication();
            }
        });
    }

    private void exitApplication() {
        int answer = JOptionPane.showConfirmDialog(this,
                "Close Sunrise Dental?", "Exit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
