package Internship;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GradeTrackerGUI extends JFrame {

    static User currentUser = null;

    JPanel     contentPanel;
    CardLayout cardLayout;
    DefaultTableModel tableModel;

    JTextField    tfName, tfRoll, tfSubject, tfMarks;
    JLabel        lblFormStatus;

    JTextField       tfSearch;
    JPanel           searchResultsPanel;

    public GradeTrackerGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        showLoginScreen();
    }

    // =========================================================================
    // LOGIN SCREEN
    // =========================================================================
    void showLoginScreen() {
        getContentPane().removeAll();
        setTitle("GradeTracker  |  Login");

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.SIDEBAR);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(UITheme.SIDEBAR2);
        left.setPreferredSize(new Dimension(380, 0));
        left.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        left.add(Box.createVerticalGlue());

        JLabel brand = new JLabel("~ GradeTracker ~");
        brand.setFont(new Font("Georgia", Font.BOLD, 28));
        brand.setForeground(UITheme.BEIGE);
        brand.setAlignmentX(CENTER_ALIGNMENT);
        left.add(brand);

        JLabel brandSub = new JLabel("Student Performance Management");
        brandSub.setFont(new Font("Georgia", Font.PLAIN, 14));
        brandSub.setForeground(UITheme.NAV_TEXT);
        brandSub.setAlignmentX(CENTER_ALIGNMENT);
        brandSub.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        left.add(brandSub);

        JSeparator div = new JSeparator();
        div.setForeground(UITheme.SAGE);
        div.setMaximumSize(new Dimension(60, 2));
        div.setAlignmentX(CENTER_ALIGNMENT);
        left.add(Box.createVerticalStrut(24));
        left.add(div);
        left.add(Box.createVerticalStrut(24));

        JLabel desc = new JLabel("<html><center style='color:#C4B8A8;font-size:12px'>"
                + "Complete Grade Management System<br>for schools and institutions.<br><br>"
                + "Track grades, analyze performance,<br>and manage student records.</center></html>");
        desc.setFont(UITheme.SMALL);
        desc.setAlignmentX(CENTER_ALIGNMENT);
        left.add(desc);
        left.add(Box.createVerticalGlue());

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(UITheme.BG);
        right.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        right.add(Box.createVerticalGlue());

        JLabel formTitle = new JLabel("Welcome Back!");
        formTitle.setFont(new Font("Georgia", Font.BOLD, 24));
        formTitle.setForeground(UITheme.OLIVE);
        formTitle.setAlignmentX(LEFT_ALIGNMENT);
        right.add(formTitle);

        JLabel formSub = new JLabel("Sign in to your GradeTracker account");
        formSub.setFont(UITheme.SUBTITLE);
        formSub.setForeground(UITheme.MUTED);
        formSub.setAlignmentX(LEFT_ALIGNMENT);
        formSub.setBorder(BorderFactory.createEmptyBorder(4, 0, 30, 0));
        right.add(formSub);

        right.add(fieldLabel("USERNAME"));
        JTextField tfUsername = styledField();
        tfUsername.setMaximumSize(new Dimension(Short.MAX_VALUE, 42));
        right.add(tfUsername);
        right.add(Box.createVerticalStrut(16));

        right.add(fieldLabel("PASSWORD"));
        JPasswordField tfPassword = styledPasswordField();
        right.add(tfPassword);
        right.add(Box.createVerticalStrut(24));

        JLabel lblStatus = new JLabel(" ");
        lblStatus.setFont(UITheme.BODY);
        lblStatus.setAlignmentX(LEFT_ALIGNMENT);
        right.add(lblStatus);
        right.add(Box.createVerticalStrut(8));

        JButton btnLogin = primaryBtn("Sign In");
        btnLogin.addActionListener(e -> {
            String uname = tfUsername.getText().trim();
            String pass  = new String(tfPassword.getPassword()).trim();
            if (uname.isEmpty() || pass.isEmpty()) {
                setStatus(lblStatus, "Please enter username and password.", false); return;
            }
            try {
                User found = DatabaseManager.login(uname, pass);
                if (found != null) { currentUser = found; showMainApp(); }
                else setStatus(lblStatus, "Invalid username or password.", false);
            } catch (Exception ex) {
                setStatus(lblStatus, "Error: " + ex.getMessage(), false);
            }
        });
        right.add(btnLogin);
        right.add(Box.createVerticalStrut(12));

        JButton btnGoRegister = outlineBtn("Create New Account");
        btnGoRegister.addActionListener(e -> showRegisterScreen());
        right.add(btnGoRegister);

        JLabel hint = new JLabel("Default admin — username: admin  |  password: admin123");
        hint.setFont(UITheme.SMALL);
        hint.setForeground(UITheme.MUTED);
        hint.setAlignmentX(LEFT_ALIGNMENT);
        hint.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        right.add(hint);
        right.add(Box.createVerticalGlue());

        root.add(left,  BorderLayout.WEST);
        root.add(right, BorderLayout.CENTER);
        setContentPane(root);
        revalidate(); repaint();
    }

    void showRegisterScreen() {
        getContentPane().removeAll();
        setTitle("GradeTracker  |  Register");

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.SIDEBAR);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(UITheme.SIDEBAR2);
        left.setPreferredSize(new Dimension(380, 0));
        left.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        left.add(Box.createVerticalGlue());

        JLabel brand = new JLabel("~ GradeTracker ~");
        brand.setFont(new Font("Georgia", Font.BOLD, 28));
        brand.setForeground(UITheme.BEIGE);
        brand.setAlignmentX(CENTER_ALIGNMENT);
        left.add(brand);

        JLabel brandSub = new JLabel("Student Performance Management");
        brandSub.setFont(new Font("Georgia", Font.PLAIN, 14));
        brandSub.setForeground(UITheme.NAV_TEXT);
        brandSub.setAlignmentX(CENTER_ALIGNMENT);
        left.add(brandSub);

        left.add(Box.createVerticalStrut(24));
        JSeparator div = new JSeparator();
        div.setForeground(UITheme.SAGE);
        div.setMaximumSize(new Dimension(60, 2));
        div.setAlignmentX(CENTER_ALIGNMENT);
        left.add(div);
        left.add(Box.createVerticalStrut(24));

        JLabel desc = new JLabel("<html><center style='color:#C4B8A8;font-size:12px'>"
                + "Create your account to start<br>tracking student grades and performance.<br><br>"
                + "Teachers and administrators can<br>manage student records efficiently.</center></html>");
        desc.setAlignmentX(CENTER_ALIGNMENT);
        left.add(desc);
        left.add(Box.createVerticalGlue());

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(UITheme.BG);
        right.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        right.add(Box.createVerticalGlue());

        JLabel formTitle = new JLabel("Create Account");
        formTitle.setFont(new Font("Georgia", Font.BOLD, 24));
        formTitle.setForeground(UITheme.OLIVE);
        formTitle.setAlignmentX(LEFT_ALIGNMENT);
        right.add(formTitle);

        JLabel formSub = new JLabel("Register to start tracking student grades");
        formSub.setFont(UITheme.SUBTITLE);
        formSub.setForeground(UITheme.MUTED);
        formSub.setAlignmentX(LEFT_ALIGNMENT);
        formSub.setBorder(BorderFactory.createEmptyBorder(4, 0, 24, 0));
        right.add(formSub);

        right.add(fieldLabel("FULL NAME"));
        JTextField tfName = styledField();
        tfName.setMaximumSize(new Dimension(Short.MAX_VALUE, 42));
        right.add(tfName); right.add(Box.createVerticalStrut(12));

        right.add(fieldLabel("USERNAME"));
        JTextField tfUser = styledField();
        tfUser.setMaximumSize(new Dimension(Short.MAX_VALUE, 42));
        right.add(tfUser); right.add(Box.createVerticalStrut(12));

        right.add(fieldLabel("PASSWORD"));
        JPasswordField tfPass = styledPasswordField();
        right.add(tfPass); right.add(Box.createVerticalStrut(12));

        right.add(fieldLabel("CONFIRM PASSWORD"));
        JPasswordField tfConfirm = styledPasswordField();
        right.add(tfConfirm); right.add(Box.createVerticalStrut(20));

        JLabel lblRegStatus = new JLabel(" ");
        lblRegStatus.setFont(UITheme.BODY);
        lblRegStatus.setAlignmentX(LEFT_ALIGNMENT);
        right.add(lblRegStatus); right.add(Box.createVerticalStrut(8));

        JButton btnRegister = primaryBtn("Create Account");
        btnRegister.addActionListener(e -> {
            String name  = tfName.getText().trim();
            String uname = tfUser.getText().trim();
            String pass  = new String(tfPass.getPassword()).trim();
            String confirm = new String(tfConfirm.getPassword()).trim();

            if (name.isEmpty() || uname.isEmpty() || pass.isEmpty()) {
                setStatus(lblRegStatus, "All fields are required.", false); return;
            }
            if (!pass.equals(confirm)) {
                setStatus(lblRegStatus, "Passwords do not match.", false); return;
            }
            try {
                if (DatabaseManager.usernameExists(uname)) {
                    setStatus(lblRegStatus, "Username already taken.", false); return;
                }
                DatabaseManager.registerUser(name, uname, pass);
                setStatus(lblRegStatus, "Account created! You can now login.", true);

                new Timer(2000, evt -> {
                    showLoginScreen();
                }).start();
            } catch (Exception ex) {
                setStatus(lblRegStatus, "Error: " + ex.getMessage(), false);
            }
        });
        right.add(btnRegister); right.add(Box.createVerticalStrut(12));

        JButton btnBack = outlineBtn("Already have an account? Sign In");
        btnBack.addActionListener(e -> showLoginScreen());
        right.add(btnBack);
        right.add(Box.createVerticalGlue());

        root.add(left,  BorderLayout.WEST);
        root.add(right, BorderLayout.CENTER);
        setContentPane(root);
        revalidate(); repaint();
    }

    void showMainApp() {
        getContentPane().removeAll();
        setTitle("GradeTracker  |  Student Performance Management System");
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);
        root.add(buildSidebar(), BorderLayout.WEST);

        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UITheme.BG);

        contentPanel.add(buildDashboard(),      "DASHBOARD");
        contentPanel.add(buildAddPanel(),       "ADD");
        contentPanel.add(buildViewPanel(),      "VIEW");
        contentPanel.add(buildSearchPanel(),    "SEARCH");
        contentPanel.add(buildAnalyticsPanel(), "ANALYTICS");
        contentPanel.add(buildExportPanel(),    "EXPORT");

        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);
        revalidate(); repaint();
    }

    JPanel buildSidebar() {
        JPanel sb = new JPanel();
        sb.setLayout(new BoxLayout(sb, BoxLayout.Y_AXIS));
        sb.setBackground(UITheme.SIDEBAR);
        sb.setPreferredSize(new Dimension(230, 0));
        sb.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(UITheme.SIDEBAR2);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(24, 20, 20, 20));
        logoPanel.setMaximumSize(new Dimension(230, 130));

        JLabel logoIcon = new JLabel("~ GradeTracker ~");
        logoIcon.setFont(new Font("Georgia", Font.BOLD, 18));
        logoIcon.setForeground(UITheme.BEIGE);
        logoIcon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel logoSub = new JLabel("Performance Management");
        logoSub.setFont(UITheme.SMALL);
        logoSub.setForeground(UITheme.NAV_TEXT);
        logoSub.setAlignmentX(CENTER_ALIGNMENT);
        logoSub.setBorder(BorderFactory.createEmptyBorder(4, 0, 10, 0));

        String roleText = currentUser.isAdmin() ? "Admin" : "Teacher";
        Color  roleBg   = currentUser.isAdmin() ? UITheme.OLIVE : UITheme.SAGE;
        JLabel roleBadge = new JLabel("  " + roleText + "  ");
        roleBadge.setFont(UITheme.LABEL);
        roleBadge.setForeground(UITheme.WHITE);
        roleBadge.setBackground(roleBg);
        roleBadge.setOpaque(true);
        roleBadge.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        roleBadge.setAlignmentX(CENTER_ALIGNMENT);

        logoPanel.add(logoIcon);
        logoPanel.add(logoSub);
        logoPanel.add(roleBadge);
        sb.add(logoPanel);
        sb.add(Box.createVerticalStrut(20));

        JLabel navLabel = new JLabel("  NAVIGATION");
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        navLabel.setForeground(UITheme.SAGE);
        navLabel.setAlignmentX(LEFT_ALIGNMENT);
        navLabel.setBorder(BorderFactory.createEmptyBorder(0, 18, 8, 0));
        sb.add(navLabel);

        JButton[] navBtns = new JButton[5];
        navBtns[0] = sidebarBtn("  Dashboard",       "DASHBOARD", navBtns);
        navBtns[1] = sidebarBtn("  Add Student",     "ADD",       navBtns);
        navBtns[2] = sidebarBtn(currentUser.isAdmin() ? "  All Students" : "  My Students", "VIEW", navBtns);
        navBtns[3] = sidebarBtn("  Search Student",  "SEARCH",    navBtns);
        navBtns[4] = sidebarBtn("  Analytics",        "ANALYTICS", navBtns);

        setActiveBtn(navBtns, navBtns[0]);

        JButton exportBtn = new JButton("  Export Report");
        exportBtn.setFont(UITheme.NAV);
        exportBtn.setForeground(UITheme.NAV_TEXT);
        exportBtn.setBackground(UITheme.SIDEBAR);
        exportBtn.setBorderPainted(false);
        exportBtn.setFocusPainted(false);
        exportBtn.setHorizontalAlignment(SwingConstants.LEFT);
        exportBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exportBtn.setMaximumSize(new Dimension(230, 44));
        exportBtn.setMinimumSize(new Dimension(230, 44));
        exportBtn.addActionListener(e -> {
            setActiveBtn(navBtns, null);
            cardLayout.show(contentPanel, "EXPORT");
        });

        for (JButton b : navBtns) sb.add(b);
        sb.add(exportBtn);
        sb.add(Box.createVerticalGlue());

        JButton btnLogout = new JButton("  Logout");
        btnLogout.setFont(UITheme.NAV);
        btnLogout.setForeground(UITheme.DANGER);
        btnLogout.setBackground(UITheme.SIDEBAR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setMaximumSize(new Dimension(230, 42));
        btnLogout.setMinimumSize(new Dimension(230, 42));
        btnLogout.addActionListener(e -> { currentUser = null; showLoginScreen(); });
        sb.add(btnLogout);

        JLabel footer = new JLabel("  v1.0  OOPsInJava");
        footer.setFont(UITheme.SMALL);
        footer.setForeground(UITheme.SAGE);
        footer.setBorder(BorderFactory.createEmptyBorder(4, 18, 0, 0));
        sb.add(footer);

        return sb;
    }

    JButton sidebarBtn(String label, String card, JButton[] allBtns) {
        JButton btn = new JButton(label);
        btn.setFont(UITheme.NAV);
        btn.setForeground(UITheme.NAV_TEXT);
        btn.setBackground(UITheme.SIDEBAR);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(230, 44));
        btn.setMinimumSize(new Dimension(230, 44));
        btn.setPreferredSize(new Dimension(230, 44));
        btn.addActionListener(e -> {
            setActiveBtn(allBtns, btn);
            cardLayout.show(contentPanel, card);
            if (card.equals("VIEW")) refreshTable();
        });
        return btn;
    }

    void setActiveBtn(JButton[] allBtns, JButton active) {
        for (JButton b : allBtns) {
            if (b == active) {
                b.setBackground(UITheme.OLIVE_DARK);
                b.setForeground(UITheme.WHITE);
                b.setFont(UITheme.NAV_BOLD);
                b.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));
            } else if (b != null) {
                b.setBackground(UITheme.SIDEBAR);
                b.setForeground(UITheme.NAV_TEXT);
                b.setFont(UITheme.NAV);
                b.setBorder(null);
            }
        }
    }

    JButton primaryBtn(String label) {
        JButton b = new JButton(label);
        b.setFont(UITheme.LABEL);
        b.setForeground(UITheme.WHITE);
        b.setBackground(UITheme.OLIVE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(11, 26, 11, 26));
        b.setMaximumSize(new Dimension(Short.MAX_VALUE, 44));
        b.setAlignmentX(LEFT_ALIGNMENT);

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(UITheme.OLIVE_DARK);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(UITheme.OLIVE);
            }
        });
        return b;
    }

    JButton outlineBtn(String label) {
        JButton b = new JButton(label);
        b.setFont(UITheme.LABEL);
        b.setForeground(UITheme.OLIVE);
        b.setBackground(UITheme.WHITE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.OLIVE, 1, true),
                BorderFactory.createEmptyBorder(10, 24, 10, 24)));
        b.setMaximumSize(new Dimension(Short.MAX_VALUE, 44));
        b.setAlignmentX(LEFT_ALIGNMENT);

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(UITheme.OLIVE_LIGHT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(UITheme.WHITE);
            }
        });
        return b;
    }

    void styleSecondaryBtn(JButton b, Color color) {
        b.setFont(UITheme.LABEL);
        b.setForeground(UITheme.WHITE);
        b.setBackground(color);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));

        Color darker = color.darker();
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(darker);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(color);
            }
        });
    }

    JTextField styledField() {
        JTextField tf = new JTextField();
        tf.setFont(UITheme.BODY);
        tf.setForeground(UITheme.TEXT);
        tf.setBackground(UITheme.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(UITheme.OLIVE, 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(UITheme.BORDER, 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
        });
        return tf;
    }

    JPasswordField styledPasswordField() {
        JPasswordField tf = new JPasswordField();
        tf.setFont(UITheme.BODY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        tf.setMaximumSize(new Dimension(Short.MAX_VALUE, 42));
        return tf;
    }

    JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(UITheme.OLIVE);
        l.setAlignmentX(LEFT_ALIGNMENT);
        l.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return l;
    }

    void setStatus(JLabel lbl, String msg, boolean success) {
        lbl.setText(msg);
        lbl.setForeground(success ? UITheme.SUCCESS : UITheme.DANGER);
    }

    void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    JLabel errorLabel(String msg) {
        JLabel l = new JLabel(msg);
        l.setFont(UITheme.BODY);
        l.setForeground(UITheme.DANGER);
        return l;
    }

    JPanel buildDashboard() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, UITheme.BORDER),
                BorderFactory.createEmptyBorder(16, 28, 16, 28)));

        JLabel titleLbl = new JLabel("Dashboard");
        titleLbl.setFont(new Font("Georgia", Font.BOLD, 20));
        titleLbl.setForeground(UITheme.OLIVE);

        JPanel rightInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightInfo.setOpaque(false);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        JLabel dateLbl = new JLabel("Today: " + today);
        dateLbl.setFont(UITheme.SMALL);
        dateLbl.setForeground(UITheme.MUTED);

        JLabel userLbl = new JLabel("Logged in: " + currentUser.getName());
        userLbl.setFont(UITheme.SMALL);
        userLbl.setForeground(UITheme.MUTED);

        rightInfo.add(dateLbl);
        rightInfo.add(userLbl);
        header.add(titleLbl,  BorderLayout.WEST);
        header.add(rightInfo, BorderLayout.EAST);
        outer.add(header, BorderLayout.NORTH);

        JPanel innerContent = new JPanel();
        innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.Y_AXIS));
        innerContent.setBackground(UITheme.BG);
        innerContent.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JLabel statsHeading = new JLabel("Overview");
        statsHeading.setFont(new Font("Georgia", Font.BOLD, 16));
        statsHeading.setForeground(UITheme.OLIVE);
        statsHeading.setAlignmentX(LEFT_ALIGNMENT);
        innerContent.add(statsHeading);
        innerContent.add(Box.createVerticalStrut(12));

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        statsRow.setBackground(UITheme.BG);
        statsRow.setMaximumSize(new Dimension(Short.MAX_VALUE, 120));
        statsRow.setAlignmentX(LEFT_ALIGNMENT);

        JLabel totalVal  = new JLabel("0");
        JLabel avgVal = new JLabel("0");
        JLabel highVal   = new JLabel("0");
        JLabel passVal = new JLabel("0");

        totalVal.setFont(UITheme.BIG_NUM);  totalVal.setForeground(UITheme.OLIVE);
        avgVal.setFont(UITheme.BIG_NUM); avgVal.setForeground(UITheme.SAGE);
        highVal.setFont(UITheme.BIG_NUM);   highVal.setForeground(UITheme.TAUPE);
        passVal.setFont(UITheme.BIG_NUM);   passVal.setForeground(UITheme.BEIGE_DARK);

        statsRow.add(dashStatCard("Total Students", totalVal, UITheme.OLIVE, UITheme.OLIVE_LIGHT));
        statsRow.add(dashStatCard("Class Average", avgVal, UITheme.SAGE, new Color(0xEDF2EA)));
        statsRow.add(dashStatCard("Highest Score", highVal, UITheme.TAUPE, new Color(0xF5EDE5)));
        statsRow.add(dashStatCard("Pass Rate %", passVal, UITheme.BEIGE_DARK, new Color(0xF5F0E6)));

        statsRow.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                try {
                    ArrayList<Student> all = currentUser.isAdmin()
                            ? DatabaseManager.getAllStudents()
                            : DatabaseManager.getStudentsByUser(currentUser.getUsername());
                    totalVal.setText(String.valueOf(all.size()));
                    if (all.size() > 0) {
                        double sum = 0;
                        double highest = 0;
                        int passed = 0;
                        for (Student s : all) {
                            sum += s.getAverage();
                            if (s.getAverage() > highest) highest = s.getAverage();
                            if (s.getAverage() >= 50) passed++;
                        }
                        avgVal.setText(String.format("%.1f", sum / all.size()));
                        highVal.setText(String.format("%.1f", highest));
                        passVal.setText(String.format("%.0f", (passed * 100.0 / all.size())));
                    }
                } catch (Exception ex) {
                    totalVal.setText("?"); avgVal.setText("?"); highVal.setText("?"); passVal.setText("?");
                }
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent e)   {}
        });

        innerContent.add(statsRow);
        innerContent.add(Box.createVerticalStrut(28));

        JLabel qLabel = new JLabel("Quick Actions");
        qLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        qLabel.setForeground(UITheme.OLIVE);
        qLabel.setAlignmentX(LEFT_ALIGNMENT);
        innerContent.add(qLabel);
        innerContent.add(Box.createVerticalStrut(12));

        JPanel acts = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        acts.setBackground(UITheme.BG);
        acts.setAlignmentX(LEFT_ALIGNMENT);

        JButton b1 = dashBtn("+ Add Student",     UITheme.OLIVE);
        JButton b2 = dashBtn(currentUser.isAdmin() ? "All Students" : "My Students", UITheme.SAGE);
        JButton b3 = dashBtn("Search Student",      UITheme.TAUPE);
        JButton b4 = dashBtn("Analytics",        new Color(0x8FBC8F));

        b1.addActionListener(e -> cardLayout.show(contentPanel, "ADD"));
        b2.addActionListener(e -> { cardLayout.show(contentPanel, "VIEW"); refreshTable(); });
        b3.addActionListener(e -> cardLayout.show(contentPanel, "SEARCH"));
        b4.addActionListener(e -> cardLayout.show(contentPanel, "ANALYTICS"));

        acts.add(b1); acts.add(b2); acts.add(b3); acts.add(b4);
        innerContent.add(acts);
        innerContent.add(Box.createVerticalStrut(28));

        JLabel infoHeading = new JLabel("System Info");
        infoHeading.setFont(new Font("Georgia", Font.BOLD, 16));
        infoHeading.setForeground(UITheme.OLIVE);
        infoHeading.setAlignmentX(LEFT_ALIGNMENT);
        innerContent.add(infoHeading);
        innerContent.add(Box.createVerticalStrut(12));

        JPanel infoRow = new JPanel(new GridLayout(1, 3, 16, 0));
        infoRow.setBackground(UITheme.BG);
        infoRow.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
        infoRow.setAlignmentX(LEFT_ALIGNMENT);

        infoRow.add(infoCard("System",   "GradeTracker v1.0",        UITheme.OLIVE));
        infoRow.add(infoCard("Institution", "Education Management", UITheme.SAGE));
        infoRow.add(infoCard("Role",     currentUser.isAdmin()
                ? "Administrator" : "Teacher",               UITheme.TAUPE));
        innerContent.add(infoRow);

        JScrollPane scroll = new JScrollPane(innerContent);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outer.add(scroll, BorderLayout.CENTER);
        return outer;
    }

    JPanel dashStatCard(String label, JLabel valueLabel, Color accent, Color bg) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 60), 1, true),
                BorderFactory.createEmptyBorder(20, 22, 20, 22)));
        valueLabel.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.SUBTITLE);
        lbl.setForeground(UITheme.MUTED);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        card.add(valueLabel);
        card.add(lbl);
        return card;
    }

    JPanel infoCard(String label, String value, Color accent) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 60), 1, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.SMALL);
        lbl.setForeground(UITheme.MUTED);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        JLabel val = new JLabel(value);
        val.setFont(UITheme.LABEL);
        val.setForeground(accent);
        val.setAlignmentX(LEFT_ALIGNMENT);
        val.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        card.add(lbl);
        card.add(val);
        return card;
    }

    JButton dashBtn(String label, Color bg) {
        JButton b = new JButton(label);
        b.setFont(UITheme.LABEL);
        b.setForeground(UITheme.WHITE);
        b.setBackground(bg);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Color darker = bg.darker();
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setBackground(darker);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setBackground(bg);
            }
        });
        return b;
    }

    JPanel buildAddPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG);
        outer.add(pageHeaderWithBack("Add New Student",
                "Fill in the details to add a new student", "DASHBOARD"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 28, 28));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(UITheme.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(28, 32, 28, 32)));
        form.setMaximumSize(new Dimension(580, Short.MAX_VALUE));
        form.setAlignmentX(LEFT_ALIGNMENT);

        form.add(fieldLabel("STUDENT NAME *"));
        tfName = styledField();
        tfName.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        form.add(tfName);
        form.add(Box.createVerticalStrut(14));

        form.add(fieldLabel("ROLL NUMBER *"));
        tfRoll = styledField();
        tfRoll.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        form.add(tfRoll);
        form.add(Box.createVerticalStrut(14));

        form.add(fieldLabel("SUBJECT NAME *"));
        tfSubject = styledField();
        tfSubject.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        form.add(tfSubject);
        form.add(Box.createVerticalStrut(14));

        form.add(fieldLabel("MARKS (0-100) *"));
        tfMarks = styledField();
        tfMarks.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
        form.add(tfMarks);

        content.add(form);
        content.add(Box.createVerticalStrut(16));

        lblFormStatus = new JLabel(" ");
        lblFormStatus.setFont(UITheme.BODY);
        lblFormStatus.setAlignmentX(LEFT_ALIGNMENT);
        content.add(lblFormStatus);
        content.add(Box.createVerticalStrut(10));

        JButton btnSave = primaryBtn("Save Student");
        btnSave.setAlignmentX(LEFT_ALIGNMENT);
        btnSave.addActionListener(e -> saveStudent());
        content.add(btnSave);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        outer.add(scroll, BorderLayout.CENTER);
        return outer;
    }

    void saveStudent() {
        try {
            String name = tfName.getText().trim();
            if (name.isEmpty()) {
                setStatus(lblFormStatus, "Error: Student name cannot be empty.", false);
                return;
            }

            String roll = tfRoll.getText().trim();
            if (roll.isEmpty()) {
                setStatus(lblFormStatus, "Error: Roll number cannot be empty.", false);
                return;
            }

            if (DatabaseManager.rollExists(roll)) {
                setStatus(lblFormStatus, "Error: Roll number already exists!", false);
                return;
            }

            String subject = tfSubject.getText().trim();
            if (subject.isEmpty()) {
                setStatus(lblFormStatus, "Error: Subject name cannot be empty.", false);
                return;
            }

            double marks = 0;
            try {
                marks = Double.parseDouble(tfMarks.getText().trim());
                if (marks < 0 || marks > 100) {
                    setStatus(lblFormStatus, "Error: Marks must be between 0 and 100.", false);
                    return;
                }
            } catch (NumberFormatException ex) {
                setStatus(lblFormStatus, "Error: Marks must be a valid number.", false);
                return;
            }

            int studentId = DatabaseManager.addStudent(name, roll, currentUser.getUsername());

            if (studentId == -1) {
                setStatus(lblFormStatus, "Error: Failed to add student.", false);
                return;
            }

            DatabaseManager.addGrade(studentId, subject, marks);

            setStatus(lblFormStatus, "✓ Student added successfully!", true);

            tfName.setText("");
            tfRoll.setText("");
            tfSubject.setText("");
            tfMarks.setText("");

            refreshTable();

            JOptionPane.showMessageDialog(this,
                    "Student added successfully!\n\nName: " + name + "\nRoll No: " + roll,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            setStatus(lblFormStatus, "Error: " + ex.getMessage(), false);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to add student:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    JPanel buildViewPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG);
        outer.add(pageHeaderWithBack(
                currentUser.isAdmin() ? "All Students" : "My Students",
                currentUser.isAdmin() ? "Complete list of all registered students" : "Students added by you",
                "DASHBOARD"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 28, 28, 28));

        String[] cols = {"ID", "Name", "Roll No", "Average", "Highest", "Lowest", "Grade", "Added By"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(UITheme.BODY);
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(UITheme.OLIVE_LIGHT);
        table.setSelectionForeground(UITheme.TEXT);

        JTableHeader hdr = table.getTableHeader();
        hdr.setFont(UITheme.LABEL);
        hdr.setBackground(UITheme.OLIVE);
        hdr.setForeground(UITheme.WHITE);
        hdr.setPreferredSize(new Dimension(0, 36));
        hdr.setBorder(BorderFactory.createEmptyBorder());
        hdr.setReorderingAllowed(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(UITheme.OLIVE);
                setForeground(UITheme.WHITE);
                setFont(UITheme.LABEL);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                setHorizontalAlignment(SwingConstants.LEFT);
                return this;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                if (!sel) {
                    setBackground(row % 2 == 0 ? UITheme.WHITE : UITheme.BG);
                    setForeground(UITheme.TEXT);
                    if (col == 6 && val != null) {
                        String grade = val.toString();
                        switch (grade) {
                            case "A+": setForeground(UITheme.SUCCESS); break;
                            case "A": setForeground(UITheme.OLIVE); break;
                            case "B": setForeground(UITheme.SAGE); break;
                            case "C": setForeground(UITheme.TAUPE); break;
                            case "D": setForeground(UITheme.WARNING); break;
                            case "F": setForeground(UITheme.DANGER); break;
                        }
                    }
                }
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(UITheme.BORDER, 1, true));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        content.add(scroll);
        content.add(Box.createVerticalStrut(14));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnRefresh = outlineBtn("Refresh");
        btnRefresh.addActionListener(e -> refreshTable());

        JButton btnEdit = new JButton("Edit Selected");
        styleSecondaryBtn(btnEdit, UITheme.SAGE);
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { showMsg("Please select a student to edit."); return; }
            showEditDialog((int) tableModel.getValueAt(row, 0));
        });

        JButton btnDelete = new JButton("Delete Selected");
        styleSecondaryBtn(btnDelete, UITheme.DANGER);
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { showMsg("Please select a student to delete."); return; }
            int id = (int) tableModel.getValueAt(row, 0);
            String name = (String) tableModel.getValueAt(row, 1);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete " + name + " permanently?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    DatabaseManager.deleteStudent(id);
                    refreshTable();
                    showMsg("Student deleted successfully!");
                } catch (Exception ex) {
                    showMsg("Error: " + ex.getMessage());
                }
            }
        });

        btnRow.add(btnRefresh);
        btnRow.add(btnEdit);
        btnRow.add(btnDelete);
        content.add(btnRow);

        JScrollPane outerScroll = new JScrollPane(content);
        outerScroll.setBorder(null);
        outerScroll.getVerticalScrollBar().setUnitIncrement(16);
        outer.add(outerScroll, BorderLayout.CENTER);
        refreshTable();
        return outer;
    }

    void refreshTable() {
        if (tableModel == null) return;
        tableModel.setRowCount(0);
        try {
            ArrayList<Student> list = currentUser.isAdmin()
                    ? DatabaseManager.getAllStudents()
                    : DatabaseManager.getStudentsByUser(currentUser.getUsername());
            for (Student s : list) {
                tableModel.addRow(new Object[]{
                        s.getId(), s.getName(), s.getRollNo(),
                        String.format("%.2f", s.getAverage()),
                        String.format("%.2f", s.getHighest()),
                        String.format("%.2f", s.getLowest()),
                        s.getGrade(), s.getAddedBy()
                });
            }
        } catch (Exception e) {
            showMsg("Error loading records: " + e.getMessage());
        }
    }

    void showEditDialog(int studentId) {
        JDialog dialog = new JDialog(this, "Edit Student #" + studentId, true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(UITheme.BG);
        form.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        form.add(fieldLabel("STUDENT NAME"));
        JTextField tfEditName = styledField();
        tfEditName.setMaximumSize(new Dimension(Short.MAX_VALUE, 38));
        form.add(tfEditName);
        form.add(Box.createVerticalStrut(12));

        form.add(fieldLabel("ROLL NUMBER"));
        JTextField tfEditRoll = styledField();
        tfEditRoll.setMaximumSize(new Dimension(Short.MAX_VALUE, 38));
        form.add(tfEditRoll);
        form.add(Box.createVerticalStrut(16));

        try {
            ArrayList<Student> students = DatabaseManager.getAllStudents();
            for (Student s : students) {
                if (s.getId() == studentId) {
                    tfEditName.setText(s.getName());
                    tfEditRoll.setText(s.getRollNo());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(UITheme.SMALL);
        form.add(lblMsg);
        form.add(Box.createVerticalStrut(8));

        JButton btnSave = primaryBtn("Update Student");
        btnSave.addActionListener(e -> {
            try {
                String name = tfEditName.getText().trim();
                String roll = tfEditRoll.getText().trim();
                DatabaseManager.updateStudent(studentId, name, roll);
                refreshTable();
                dialog.dispose();
                showMsg("Student updated successfully!");
            } catch (Exception ex) {
                setStatus(lblMsg, "Error: " + ex.getMessage(), false);
            }
        });
        form.add(btnSave);
        dialog.setContentPane(form);
        dialog.setVisible(true);
    }

    JPanel buildSearchPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG);
        outer.add(pageHeaderWithBack("Search Student",
                "Find a student by name or roll number", "DASHBOARD"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 28, 28));

        JPanel searchCard = new JPanel();
        searchCard.setLayout(new BoxLayout(searchCard, BoxLayout.Y_AXIS));
        searchCard.setBackground(UITheme.WHITE);
        searchCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.BORDER, 1, true),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        searchCard.setMaximumSize(new Dimension(Short.MAX_VALUE, 110));
        searchCard.setAlignmentX(LEFT_ALIGNMENT);

        JLabel searchTitle = new JLabel("Enter Student Name or Roll Number");
        searchTitle.setFont(UITheme.LABEL);
        searchTitle.setForeground(UITheme.MUTED);
        searchTitle.setAlignmentX(LEFT_ALIGNMENT);
        searchCard.add(searchTitle);
        searchCard.add(Box.createVerticalStrut(10));

        JPanel searchBar = new JPanel(new BorderLayout(10, 0));
        searchBar.setOpaque(false);
        searchBar.setAlignmentX(LEFT_ALIGNMENT);

        tfSearch = new JTextField();
        tfSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfSearch.setForeground(UITheme.TEXT);
        tfSearch.setBackground(UITheme.BG);
        tfSearch.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UITheme.OLIVE, 1, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));

        JButton btnSearch = new JButton("  Search") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.OLIVE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSearch.setFont(UITheme.LABEL);
        btnSearch.setForeground(UITheme.WHITE);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        btnSearch.setPreferredSize(new Dimension(110, 44));
        btnSearch.addActionListener(e -> runSearch());
        tfSearch.addActionListener(e -> runSearch());

        searchBar.add(tfSearch,  BorderLayout.CENTER);
        searchBar.add(btnSearch, BorderLayout.EAST);
        searchCard.add(searchBar);
        content.add(searchCard);
        content.add(Box.createVerticalStrut(20));

        JLabel resultsLabel = new JLabel("Results");
        resultsLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        resultsLabel.setForeground(UITheme.OLIVE);
        resultsLabel.setAlignmentX(LEFT_ALIGNMENT);
        content.add(resultsLabel);
        content.add(Box.createVerticalStrut(10));

        searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, BoxLayout.Y_AXIS));
        searchResultsPanel.setOpaque(false);
        searchResultsPanel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel hint = new JLabel("Type a name or roll number above and press Search.");
        hint.setFont(UITheme.BODY);
        hint.setForeground(UITheme.MUTED);
        searchResultsPanel.add(hint);
        content.add(searchResultsPanel);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outer.add(scrollPane, BorderLayout.CENTER);
        return outer;
    }

    void runSearch() {
        searchResultsPanel.removeAll();
        String query = tfSearch.getText().trim();
        if (query.isEmpty()) {
            searchResultsPanel.add(errorLabel("Please enter a name or roll number to search."));
        } else {
            try {
                Student student = DatabaseManager.searchByName(query);
                if (student == null) student = DatabaseManager.searchByRoll(query);

                if (student == null) {
                    searchResultsPanel.add(errorLabel("No student found for: \"" + query + "\""));
                } else {
                    searchResultsPanel.add(studentResultCard(student));
                }
            } catch (Exception e) {
                searchResultsPanel.add(errorLabel("Error: " + e.getMessage()));
            }
        }
        searchResultsPanel.revalidate();
        searchResultsPanel.repaint();
    }

    JPanel studentResultCard(Student s) {
        JPanel card = new JPanel(new GridLayout(0, 2, 12, 8));
        card.setBackground(UITheme.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 4, 0, 0, UITheme.OLIVE),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));
        card.setMaximumSize(new Dimension(560, Short.MAX_VALUE));
        card.setAlignmentX(LEFT_ALIGNMENT);

        Color gradeColor = UITheme.OLIVE;
        switch (s.getGrade()) {
            case "A+": gradeColor = UITheme.SUCCESS; break;
            case "A": gradeColor = UITheme.OLIVE; break;
            case "B": gradeColor = UITheme.SAGE; break;
            case "C": gradeColor = UITheme.TAUPE; break;
            case "D": gradeColor = UITheme.WARNING; break;
            case "F": gradeColor = UITheme.DANGER; break;
        }

        addCardRow(card, "ID", String.valueOf(s.getId()), UITheme.TEXT);
        addCardRow(card, "Name", s.getName(), UITheme.TEXT);
        addCardRow(card, "Roll No", s.getRollNo(), UITheme.TEXT);
        addCardRow(card, "Average", String.format("%.2f%%", s.getAverage()), UITheme.OLIVE);
        addCardRow(card, "Highest", String.format("%.2f%%", s.getHighest()), UITheme.SUCCESS);
        addCardRow(card, "Lowest", String.format("%.2f%%", s.getLowest()), UITheme.WARNING);
        addCardRow(card, "Grade", s.getGrade(), gradeColor);
        addCardRow(card, "Added By", s.getAddedBy(), UITheme.MUTED);

        return card;
    }

    void addCardRow(JPanel p, String key, String val, Color valColor) {
        JLabel k = new JLabel(key);
        k.setFont(UITheme.LABEL);
        k.setForeground(UITheme.MUTED);
        JLabel v = new JLabel(val);
        v.setFont(UITheme.BODY);
        v.setForeground(valColor);
        p.add(k);
        p.add(v);
    }

    JPanel buildAnalyticsPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG);
        outer.add(pageHeaderWithBack("Performance Analytics",
                "Class statistics and grade distribution", "DASHBOARD"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 28, 28));

        try {
            ArrayList<Student> students = currentUser.isAdmin()
                    ? DatabaseManager.getAllStudents()
                    : DatabaseManager.getStudentsByUser(currentUser.getUsername());

            if (students.isEmpty()) {
                content.add(errorLabel("No student data available for analytics."));
            } else {
                JPanel distCard = new JPanel();
                distCard.setLayout(new BoxLayout(distCard, BoxLayout.Y_AXIS));
                distCard.setBackground(UITheme.WHITE);
                distCard.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(UITheme.BORDER, 1, true),
                        BorderFactory.createEmptyBorder(20, 24, 20, 24)));
                distCard.setAlignmentX(LEFT_ALIGNMENT);
                distCard.setMaximumSize(new Dimension(Short.MAX_VALUE, 300));

                JLabel distTitle = new JLabel("Grade Distribution");
                distTitle.setFont(new Font("Georgia", Font.BOLD, 16));
                distTitle.setForeground(UITheme.OLIVE);
                distTitle.setAlignmentX(LEFT_ALIGNMENT);
                distCard.add(distTitle);
                distCard.add(Box.createVerticalStrut(16));

                int ap=0, a=0, b=0, c=0, d=0, f=0;
                for (Student s : students) {
                    switch (s.getGrade()) {
                        case "A+": ap++; break;
                        case "A": a++; break;
                        case "B": b++; break;
                        case "C": c++; break;
                        case "D": d++; break;
                        case "F": f++; break;
                    }
                }

                distCard.add(createGradeBar("A+ (90-100)", ap, students.size(), UITheme.SUCCESS));
                distCard.add(Box.createVerticalStrut(8));
                distCard.add(createGradeBar("A  (80-89)", a, students.size(), UITheme.OLIVE));
                distCard.add(Box.createVerticalStrut(8));
                distCard.add(createGradeBar("B  (70-79)", b, students.size(), UITheme.SAGE));
                distCard.add(Box.createVerticalStrut(8));
                distCard.add(createGradeBar("C  (60-69)", c, students.size(), UITheme.TAUPE));
                distCard.add(Box.createVerticalStrut(8));
                distCard.add(createGradeBar("D  (50-59)", d, students.size(), UITheme.WARNING));
                distCard.add(Box.createVerticalStrut(8));
                distCard.add(createGradeBar("F  (0-49)", f, students.size(), UITheme.DANGER));

                content.add(distCard);
                content.add(Box.createVerticalStrut(20));

                JPanel statsCard = new JPanel(new GridLayout(1, 4, 16, 0));
                statsCard.setBackground(UITheme.WHITE);
                statsCard.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(UITheme.BORDER, 1, true),
                        BorderFactory.createEmptyBorder(20, 24, 20, 24)));
                statsCard.setAlignmentX(LEFT_ALIGNMENT);
                statsCard.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));

                double total = 0;
                double highest = 0;
                double lowest = 100;
                for (Student s : students) {
                    total += s.getAverage();
                    if (s.getAverage() > highest) highest = s.getAverage();
                    if (s.getAverage() < lowest) lowest = s.getAverage();
                }
                double avg = total / students.size();
                int passed = 0;
                for (Student s : students) if (s.getAverage() >= 50) passed++;

                statsCard.add(createStatCard("Average Score", String.format("%.2f%%", avg), UITheme.OLIVE));
                statsCard.add(createStatCard("Highest Score", String.format("%.2f%%", highest), UITheme.SUCCESS));
                statsCard.add(createStatCard("Lowest Score", String.format("%.2f%%", lowest), UITheme.WARNING));
                statsCard.add(createStatCard("Pass Rate", String.format("%.1f%%", (passed * 100.0 / students.size())), UITheme.SAGE));

                content.add(statsCard);
            }
        } catch (Exception e) {
            content.add(errorLabel("Error loading analytics: " + e.getMessage()));
        }

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        outer.add(scrollPane, BorderLayout.CENTER);
        return outer;
    }

    private JPanel createGradeBar(String label, int count, int total, Color color) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.SMALL);
        lbl.setForeground(UITheme.MUTED);
        lbl.setPreferredSize(new Dimension(100, 25));

        int percentage = total > 0 ? (count * 100 / total) : 0;
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(percentage);
        bar.setStringPainted(true);
        bar.setString(count + " students (" + percentage + "%)");
        bar.setForeground(color);
        bar.setBackground(UITheme.BG);
        bar.setFont(UITheme.SMALL);

        panel.add(lbl, BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatCard(String label, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(color, 1, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Georgia", Font.BOLD, 20));
        val.setForeground(color);
        val.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.SMALL);
        lbl.setForeground(UITheme.MUTED);
        lbl.setAlignmentX(CENTER_ALIGNMENT);

        card.add(val);
        card.add(Box.createVerticalStrut(8));
        card.add(lbl);
        return card;
    }

    JPanel buildExportPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(UITheme.BG);
        outer.add(pageHeaderWithBack("Export Report",
                "Save student records to a text file", "DASHBOARD"), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UITheme.BG);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 28, 28));

        JLabel info = new JLabel("Click below to export all your accessible records as a formatted .txt report.");
        info.setFont(UITheme.BODY);
        info.setForeground(UITheme.TEXT);
        info.setAlignmentX(LEFT_ALIGNMENT);
        content.add(info);
        content.add(Box.createVerticalStrut(20));

        JLabel lblExportStatus = new JLabel(" ");
        lblExportStatus.setFont(UITheme.BODY);
        lblExportStatus.setAlignmentX(LEFT_ALIGNMENT);
        content.add(lblExportStatus);
        content.add(Box.createVerticalStrut(10));

        JButton btnExport = primaryBtn("Export Records to File");
        btnExport.setAlignmentX(LEFT_ALIGNMENT);
        btnExport.addActionListener(e -> {
            try {
                ArrayList<Student> list = currentUser.isAdmin()
                        ? DatabaseManager.getAllStudents()
                        : DatabaseManager.getStudentsByUser(currentUser.getUsername());
                String filename = ReportExporter.export(list, currentUser.getUsername());
                setStatus(lblExportStatus, "Report saved: " + filename, true);
                JOptionPane.showMessageDialog(this, "Report exported!\nFile: " + filename,
                        "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                setStatus(lblExportStatus, "Error: " + ex.getMessage(), false);
            }
        });
        content.add(btnExport);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        outer.add(scroll, BorderLayout.CENTER);
        return outer;
    }

    JPanel pageHeaderWithBack(String title, String subtitle, String backCard) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, UITheme.BORDER),
                BorderFactory.createEmptyBorder(16, 28, 16, 28)));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        left.setOpaque(false);

        JButton btnBack = new JButton("‹ Dashboard") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.OLIVE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnBack.setForeground(UITheme.WHITE);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        btnBack.setPreferredSize(new Dimension(120, 34));
        btnBack.addActionListener(e -> cardLayout.show(contentPanel, backCard));

        JPanel titleArea = new JPanel();
        titleArea.setLayout(new BoxLayout(titleArea, BoxLayout.Y_AXIS));
        titleArea.setOpaque(false);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Georgia", Font.BOLD, 18));
        t.setForeground(UITheme.OLIVE);

        JLabel s = new JLabel(subtitle);
        s.setFont(UITheme.SMALL);
        s.setForeground(UITheme.MUTED);
        s.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));

        titleArea.add(t);
        titleArea.add(s);

        left.add(btnBack);
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setForeground(UITheme.BORDER);
        sep.setPreferredSize(new Dimension(1, 36));
        left.add(sep);
        left.add(titleArea);

        header.add(left, BorderLayout.WEST);
        return header;
    }
}