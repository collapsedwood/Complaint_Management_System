package Gui;
import Service.ComplaintManager;
import Service.UserManager;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;
import model.*;

public class MainGUI extends JFrame {
    private final UserManager userManager;
    private final ComplaintManager complaintManager;
    private User activeUser;

    // Components that we need to access across different methods
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);
    private final JTextArea displayArea = new JTextArea(15, 30);

    public MainGUI(UserManager u, ComplaintManager c) {
        this.userManager = u;
        this.complaintManager = c;

        setTitle("Complaint Management System - JDBC Edition");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        showLoginScreen();
    }

    
    private void showLoginScreen() {
        getContentPane().removeAll(); 
        setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; add(txtPass, gbc);

        JButton btnLogin = new JButton("Login");
        JButton btnSignUp = new JButton("Register New Account");

        // Action Listeners (The "Wiring")
        btnLogin.addActionListener(e -> handleLogin());
        btnSignUp.addActionListener(e -> handleSignUp());

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnLogin, gbc);
        
        gbc.gridy = 3;
        add(btnSignUp, gbc);

        revalidate(); 
        repaint();
    }

    private void handleLogin() {
        try {
            String name = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (userManager.login(name, pass)) {
                activeUser = userManager.getCurrentUser();
                showDashboard();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignUp() {
        try {
            String name = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            userManager.SignUp(name, pass);
            JOptionPane.showMessageDialog(this, "Account Created! You can now log in.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void showDashboard() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));


        String roleText = (activeUser.getRole() == UserRole.Admin) ? "ADMIN" : "CUSTOMER";
        JLabel lblWelcome = new JLabel("Welcome, " + activeUser.getUserName() + " [" + roleText + "]", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 18));
        add(lblWelcome, BorderLayout.NORTH);

    
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        if (activeUser.getRole() == UserRole.Admin) {
            
            JButton btnAll = new JButton("View All Complaints");
            btnAll.addActionListener(e -> displayArea.setText(complaintManager.view_all_complaints()));

            JButton btnUpdate = new JButton("Update Status");
            btnUpdate.addActionListener(e -> handleUpdateStatus());

            JButton btnPromote = new JButton("Promote User");
            btnPromote.addActionListener(e -> handlePromote());

            JButton btnReport = new JButton("Save File Report");
            btnReport.addActionListener(e -> {
                try {
                    complaintManager.generate_report(activeUser);
                    JOptionPane.showMessageDialog(this, "Report saved to Report.txt");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            });

            btnPanel.add(btnAll);
            btnPanel.add(btnUpdate);
            btnPanel.add(btnPromote);
            btnPanel.add(btnReport);

        } else {
            
            JButton btnFile = new JButton("File New Complaint");
            btnFile.addActionListener(e -> handleFileComplaint());

            JButton btnView = new JButton("View My History");
            btnView.addActionListener(e -> displayArea.setText(complaintManager.view_complaint(activeUser)));

            btnPanel.add(btnFile);
            btnPanel.add(btnView);
        }

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.PINK);
        btnLogout.addActionListener(e -> { 
            activeUser = null; 
            txtUser.setText(""); 
            txtPass.setText(""); 
            showLoginScreen(); 
        });
        btnPanel.add(btnLogout);

        add(btnPanel, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private void handleFileComplaint() {
        String desc = JOptionPane.showInputDialog(this, "Describe your issue:");
        if (desc != null && !desc.trim().isEmpty()) {
            try {
                complaintManager.complaint_registration(activeUser, desc);
                JOptionPane.showMessageDialog(this, "Complaint Registered Successfully!");
                displayArea.setText(complaintManager.view_complaint(activeUser));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
            }
        }
    }

    private void handleUpdateStatus() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter Complaint ID:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);

            Status[] options = {Status.PENDING, Status.IN_PROGRESS, Status.RESOLVED};
            Status selected = (Status) JOptionPane.showInputDialog(null, "Choose New Status", "Update",
                                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            
            if (selected != null) {
                complaintManager.UpdateStatus(id, selected);
                JOptionPane.showMessageDialog(this, "Status Updated!");
                displayArea.setText(complaintManager.view_all_complaints());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Enter a valid numeric ID.");
        }
    }

    private void handlePromote() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Enter User ID to promote:");
            if (idStr == null) return;
            int id = Integer.parseInt(idStr);
            userManager.promoteToAdmin(id, activeUser);
            JOptionPane.showMessageDialog(this, "User ID " + id + " promoted to Admin.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}