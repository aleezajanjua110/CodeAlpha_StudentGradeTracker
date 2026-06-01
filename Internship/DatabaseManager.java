package Internship;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:gradetracker.db";
    public static Connection conn = null;

    public static void connect() throws Exception {
        conn = DriverManager.getConnection(DB_URL);
        createTables();
        insertDefaultAdmin();
    }

    private static void createTables() throws Exception {
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS users ("
                + "id       INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name     TEXT NOT NULL,"
                + "username TEXT NOT NULL UNIQUE,"
                + "password TEXT NOT NULL,"
                + "is_admin INTEGER NOT NULL DEFAULT 0)");

        stmt.execute("CREATE TABLE IF NOT EXISTS students ("
                + "id       INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name     TEXT NOT NULL,"
                + "roll_no  TEXT NOT NULL UNIQUE,"
                + "added_by TEXT NOT NULL)");

        stmt.execute("CREATE TABLE IF NOT EXISTS grades ("
                + "id         INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "student_id INTEGER NOT NULL,"
                + "subject    TEXT NOT NULL,"
                + "marks      REAL NOT NULL,"
                + "FOREIGN KEY(student_id) REFERENCES students(id))");

        stmt.close();
    }

    private static void insertDefaultAdmin() throws Exception {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(*) FROM users WHERE username = 'admin'");
        if (rs.getInt(1) == 0) {
            stmt.execute("INSERT INTO users (name, username, password, is_admin) "
                    + "VALUES ('Administrator', 'admin', 'admin123', 1)");
        }
        rs.close(); stmt.close();
    }

    public static User login(String username, String password) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("is_admin") == 1);
        }
        rs.close(); ps.close();
        return user;
    }

    public static boolean usernameExists(String username) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM users WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.getInt(1) > 0;
        rs.close(); ps.close();
        return exists;
    }

    public static void registerUser(String name, String username,
                                    String password) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (name, username, password, is_admin) VALUES (?, ?, ?, 0)");
        ps.setString(1, name);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.executeUpdate();
        ps.close();
    }

    public static int addStudent(String name, String rollNo,
                                 String addedBy) throws Exception {
        String sql = "INSERT INTO students (name, roll_no, added_by) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, rollNo);
        ps.setString(3, addedBy);
        ps.executeUpdate();

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        ps.close();
        return id;
    }

    public static void addGrade(int studentId, String subject,
                                double marks) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO grades (student_id, subject, marks) VALUES (?, ?, ?)");
        ps.setInt(1, studentId);
        ps.setString(2, subject);
        ps.setDouble(3, marks);
        ps.executeUpdate();
        ps.close();
    }

    public static ArrayList<Student> getAllStudents() throws Exception {
        ArrayList<Student> list = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT * FROM students ORDER BY id DESC");
        while (rs.next()) {
            Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("added_by"));
            s.setGrades(getGradesForStudent(s.getId()));
            list.add(s);
        }
        rs.close(); stmt.close();
        return list;
    }

    public static ArrayList<Student> getStudentsByUser(
            String username) throws Exception {
        ArrayList<Student> list = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM students WHERE added_by = ? ORDER BY id DESC");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("added_by"));
            s.setGrades(getGradesForStudent(s.getId()));
            list.add(s);
        }
        rs.close(); ps.close();
        return list;
    }

    public static ArrayList<Double> getGradesForStudent(
            int studentId) throws Exception {
        ArrayList<Double> grades = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement(
                "SELECT marks FROM grades WHERE student_id = ?");
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) grades.add(rs.getDouble("marks"));
        rs.close(); ps.close();
        return grades;
    }

    public static Student searchByName(String name) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM students WHERE LOWER(name) LIKE LOWER(?)");
        ps.setString(1, "%" + name + "%");
        ResultSet rs = ps.executeQuery();
        Student s = null;
        if (rs.next()) {
            s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("added_by"));
            s.setGrades(getGradesForStudent(s.getId()));
        }
        rs.close(); ps.close();
        return s;
    }

    public static Student searchByRoll(String rollNo) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM students WHERE LOWER(roll_no) = LOWER(?)");
        ps.setString(1, rollNo);
        ResultSet rs = ps.executeQuery();
        Student s = null;
        if (rs.next()) {
            s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("roll_no"),
                    rs.getString("added_by"));
            s.setGrades(getGradesForStudent(s.getId()));
        }
        rs.close(); ps.close();
        return s;
    }

    public static void updateStudent(int id, String name,
                                     String rollNo) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "UPDATE students SET name = ?, roll_no = ? WHERE id = ?");
        ps.setString(1, name);
        ps.setString(2, rollNo);
        ps.setInt(3, id);
        ps.executeUpdate();
        ps.close();
    }

    public static void deleteStudent(int id) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM grades WHERE student_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();

        ps = conn.prepareStatement("DELETE FROM students WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public static boolean rollExists(String rollNo) throws Exception {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM students WHERE roll_no = ?");
        ps.setString(1, rollNo);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.getInt(1) > 0;
        rs.close(); ps.close();
        return exists;
    }

    public static void disconnect() {
        try { if (conn != null) conn.close(); }
        catch (Exception ignored) {}
    }
}