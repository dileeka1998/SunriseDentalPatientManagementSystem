package view;

import DAO.BillDAO;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportsView extends JPanel {

    public ReportsView() {
        initComponents();
        scrollReport.getViewport().setBackground(Color.WHITE);
    }

    private void initComponents() {//GEN-BEGIN:initComponents
        lblTitle = new JLabel("Billing report");
        pnlFilters = new JPanel();
        lblFrom = new JLabel("From");
        txtFrom = new JTextField(LocalDate.now().withDayOfMonth(1).toString(), 10);
        lblTo = new JLabel("To");
        txtTo = new JTextField(LocalDate.now().toString(), 10);
        btnLoad = new JButton("Load report");
        scrollReport = new JScrollPane();
        tblReport = new JTable();
        lblTotal = new JLabel("Total: Rs. 0.00");
        setBackground(new Color(244, 248, 244));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        setLayout(new java.awt.BorderLayout(10, 18));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        pnlFilters.setOpaque(false);
        pnlFilters.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        pnlFilters.add(lblTitle);
        pnlFilters.add(lblFrom);
        pnlFilters.add(txtFrom);
        pnlFilters.add(lblTo);
        pnlFilters.add(txtTo);
        btnLoad.setBackground(new Color(36, 115, 66));
        btnLoad.setForeground(Color.WHITE);
        btnLoad.addActionListener(e -> loadReport());
        pnlFilters.add(btnLoad);
        add(pnlFilters, java.awt.BorderLayout.NORTH);
        tblReport.setModel(new DefaultTableModel(new Object[][]{},
                new String[]{"Bill ID", "Appointment", "Patient", "Amount", "Date"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        tblReport.setRowHeight(28);
        scrollReport.setViewportView(tblReport);
        add(scrollReport, java.awt.BorderLayout.CENTER);
        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 15));
        add(lblTotal, java.awt.BorderLayout.SOUTH);
    }//GEN-END:initComponents

    private void loadReport() {
        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
        model.setRowCount(0);
        try {
            Date from = Date.valueOf(txtFrom.getText().trim());
            Date to = Date.valueOf(txtTo.getText().trim());
            if (from.after(to)) {
                JOptionPane.showMessageDialog(this, "The from date must be before the to date.",
                        "Invalid date range", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Object[]> rows = new BillDAO().report(from, to);
            BigDecimal total = BigDecimal.ZERO;
            for (Object[] row : rows) {
                model.addRow(row);
                total = total.add((BigDecimal) row[3]);
            }
            lblTotal.setText("Records: " + rows.size() + "     Total: Rs. " + total.toPlainString());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Enter both dates as YYYY-MM-DD.",
                    "Invalid date", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to load the billing report.",
                    "Report error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnLoad;
    private JLabel lblFrom, lblTitle, lblTo, lblTotal;
    private JPanel pnlFilters;
    private JScrollPane scrollReport;
    private JTable tblReport;
    private JTextField txtFrom, txtTo;
    // End of variables declaration//GEN-END:variables
}
