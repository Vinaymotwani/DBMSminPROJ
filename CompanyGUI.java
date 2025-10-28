import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CompanyManagementGUI extends JFrame {

    // Database connection
    Connection con;

    // --- Core CMS Components ---
    private JTabbedPane tabbedPane;
    private JPanel employeePanel; // Panel to hold the Employee GUI elements
    private JPanel financePanel;  // Panel for Finance/Invoices module
    private JPanel projectPanel;  // Panel for Project Management module

    // --- Employee Module Components (Retained from original code) ---
    JTextField tfId, tfName, tfEmail, tfMobile, tfDept, tfRole;
    JButton btnAdd, btnUpdate, btnDelete, btnView, btnReset;
    JTextArea taDisplay;

    // --- Constructor: Setup & Initialization ---
    public CompanyManagementGUI() {
        // 1. Database Connection (Kept the same)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employee_mng10?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root",
                "arnavmanojkale" // <-- REPLACE WITH YOUR MYSQL PASSWORD
            );
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Failed: " + e.getMessage());
            System.exit(0);
        }

        // 2. Window Setup
        setTitle("Integrated Company Management System");
        setSize(800, 650); // Increased size for better tab viewing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout for the main frame

        // 3. Create JTabbedPane
        tabbedPane = new JTabbedPane();
        
        // Setup individual modules (Panels)
        setupEmployeeModule();
        setupFinanceModule();
        setupProjectModule();

        // Add the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // ------------------------------------------
    // --- MODULE SETUP METHODS ---
    // ------------------------------------------

    /**
     * Integrates the existing Employee GUI logic into a panel/tab.
     */
    private void setupEmployeeModule() {
        employeePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Re-adding Labels and Text Fields to the employeePanel
        gbc.gridx=0; gbc.gridy=0; employeePanel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx=1; tfId = new JTextField(20); employeePanel.add(tfId, gbc);

        gbc.gridx=0; gbc.gridy=1; employeePanel.add(new JLabel("Name:"), gbc);
        gbc.gridx=1; tfName = new JTextField(20); employeePanel.add(tfName, gbc);

        gbc.gridx=0; gbc.gridy=2; employeePanel.add(new JLabel("Email:"), gbc);
        gbc.gridx=1; tfEmail = new JTextField(20); employeePanel.add(tfEmail, gbc);

        gbc.gridx=0; gbc.gridy=3; employeePanel.add(new JLabel("Mobile:"), gbc);
        gbc.gridx=1; tfMobile = new JTextField(20); employeePanel.add(tfMobile, gbc);

        gbc.gridx=0; gbc.gridy=4; employeePanel.add(new JLabel("Department:"), gbc);
        gbc.gridx=1; tfDept = new JTextField(20); employeePanel.add(tfDept, gbc);

        gbc.gridx=0; gbc.gridy=5; employeePanel.add(new JLabel("Role:"), gbc);
        gbc.gridx=1; tfRole = new JTextField(20); employeePanel.add(tfRole, gbc);

        // Buttons
        JPanel panelButtons = new JPanel();
        btnAdd = new JButton("Add"); btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete"); btnView = new JButton("View All");
        btnReset = new JButton("Reset");
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete); panelButtons.add(btnView);
        panelButtons.add(btnReset);

        gbc.gridx=0; gbc.gridy=6; gbc.gridwidth=2; employeePanel.add(panelButtons, gbc);

        // Display Area
        taDisplay = new JTextArea(15,50);
        taDisplay.setEditable(false);
        JScrollPane scroll = new JScrollPane(taDisplay);
        gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2; employeePanel.add(scroll, gbc);

        // Button Actions (Kept the same)
        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnView.addActionListener(e -> viewEmployees());
        btnReset.addActionListener(e -> resetFields());

        // Add to the main tabbed pane
        tabbedPane.addTab("HR / Employee Management", employeePanel);
    }
    
    /**
     * Placeholder setup for the Finance/Invoicing Module.
     */
    private void setupFinanceModule() {
        financePanel = new JPanel(new BorderLayout());
        JTextArea financeArea = new JTextArea("Finance Module: \n\nPlaceholder for Invoice, Transaction, and Expense Management tables and forms.");
        financeArea.setEditable(false);
        financePanel.add(new JScrollPane(financeArea), BorderLayout.CENTER);

        // Example: Add a button specific to this module
        JButton btnAddInvoice = new JButton("Add New Invoice");
        financePanel.add(btnAddInvoice, BorderLayout.NORTH);
        
        btnAddInvoice.addActionListener(e -> JOptionPane.showMessageDialog(this, "Simulating: Launching 'Add Invoice' Form..."));

        tabbedPane.addTab("Finance / Invoicing", financePanel);
    }
    
    /**
     * Placeholder setup for the Project Management Module.
     */
    private void setupProjectModule() {
        projectPanel = new JPanel(new BorderLayout());
        JTextArea projectArea = new JTextArea("Project Management Module: \n\nPlaceholder for Project List, Task Tracking, and Resource Allocation features.");
        projectArea.setEditable(false);
        projectPanel.add(new JScrollPane(projectArea), BorderLayout.CENTER);

        // Example: Add a button specific to this module
        JButton btnViewProjects = new JButton("View All Active Projects");
        projectPanel.add(btnViewProjects, BorderLayout.NORTH);
        
        btnViewProjects.addActionListener(e -> JOptionPane.showMessageDialog(this, "Simulating: Displaying Active Projects Table..."));

        tabbedPane.addTab("Project Management", projectPanel);
    }

    // ------------------------------------------
    // --- EMPLOYEE MANAGEMENT METHODS (from original code) ---
    // ------------------------------------------

    void addEmployee() { /* ... existing implementation ... */
        try {
             String sql = "INSERT INTO employee(emp_name, emp_email, emp_mobile, emp_dep, emp_role) VALUES(?,?,?,?,?)";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setString(1, tfName.getText());
             pst.setString(2, tfEmail.getText());
             pst.setString(3, tfMobile.getText());
             pst.setString(4, tfDept.getText());
             pst.setString(5, tfRole.getText());
             pst.executeUpdate();
             JOptionPane.showMessageDialog(this, "Employee Added Successfully!");
             resetFields();
        } catch(Exception e) {
             JOptionPane.showMessageDialog(this, "Error Adding Employee: " + e.getMessage());
        }
    }

    void updateEmployee() { /* ... existing implementation ... */
        try {
             String idText = tfId.getText().trim();
             if (idText.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Please enter Employee ID to update.");
                 return;
             }
             String sql = "UPDATE employee SET emp_name=?, emp_email=?, emp_mobile=?, emp_dep=?, emp_role=? WHERE emp_id=?";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setString(1, tfName.getText());
             pst.setString(2, tfEmail.getText());
             pst.setString(3, tfMobile.getText());
             pst.setString(4, tfDept.getText());
             pst.setString(5, tfRole.getText());
             pst.setInt(6, Integer.parseInt(idText));
             int rows = pst.executeUpdate();
             if(rows>0) JOptionPane.showMessageDialog(this, "Employee Updated Successfully!");
             else JOptionPane.showMessageDialog(this, "Employee ID Not Found!");
             resetFields();
        } catch(Exception e) {
             JOptionPane.showMessageDialog(this, "Error Updating Employee: " + e.getMessage());
        }
    }

    void deleteEmployee() { /* ... existing implementation ... */
        try {
             String idText = tfId.getText().trim();
             if (idText.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Please enter Employee ID to delete.");
                 return;
             }
             int empId = Integer.parseInt(idText);
             String sql = "DELETE FROM employee WHERE emp_id=?";
             PreparedStatement pst = con.prepareStatement(sql);
             pst.setInt(1, empId);
             int rows = pst.executeUpdate();
             if (rows > 0) {
                 JOptionPane.showMessageDialog(this, "Employee Deleted Successfully!");
             } else {
                 JOptionPane.showMessageDialog(this, "Employee ID Not Found!");
             }
             resetFields();
        } catch (NumberFormatException nfe) {
             JOptionPane.showMessageDialog(this, "Invalid Employee ID. Please enter a valid number.");
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error Deleting Employee: " + e.getMessage());
        }
    }

    void viewEmployees() { /* ... existing implementation ... */
        try {
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM employee");
             taDisplay.setText("");
             while(rs.next()) {
                 taDisplay.append(
                     "ID: " + rs.getInt("emp_id") +
                     " | Name: " + rs.getString("emp_name") +
                     " | Email: " + rs.getString("emp_email") +
                     " | Mobile: " + rs.getString("emp_mobile") +
                     " | Department: " + rs.getString("emp_dep") +
                     " | Role: " + rs.getString("emp_role") + "\n"
                 );
             }
        } catch(Exception e) {
             JOptionPane.showMessageDialog(this, "Error Viewing Employees: " + e.getMessage());
        }
    }

    void resetFields() {
        tfId.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfMobile.setText("");
        tfDept.setText("");
        tfRole.setText("");
    }
    
    // ------------------------------------------
    // --- MAIN METHOD ---
    // ------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompanyManagementGUI());
    }
}
