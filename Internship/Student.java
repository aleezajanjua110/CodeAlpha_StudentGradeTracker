package Internship;

import java.util.ArrayList;

public class Student {
    private int    id;
    private String name;
    private String rollNo;
    private String addedBy;
    private ArrayList<Double> grades;

    public Student(int id, String name, String rollNo, String addedBy) {
        this.id      = id;
        this.name    = name;
        this.rollNo  = rollNo;
        this.addedBy = addedBy;
        this.grades  = new ArrayList<>();
    }

    public int    getId()        { return id; }
    public String getName()      { return name; }
    public String getRollNo()    { return rollNo; }
    public String getAddedBy()   { return addedBy; }

    public void setName(String name)     { this.name = name; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public ArrayList<Double> getGrades() { return grades; }
    public void setGrades(ArrayList<Double> grades) { this.grades = grades; }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        double sum = 0;
        for (double g : grades) sum += g;
        return sum / grades.size();
    }

    public double getHighest() {
        if (grades.isEmpty()) return 0;
        double max = grades.get(0);
        for (double g : grades) if (g > max) max = g;
        return max;
    }

    public double getLowest() {
        if (grades.isEmpty()) return 0;
        double min = grades.get(0);
        for (double g : grades) if (g < min) min = g;
        return min;
    }

    public String getGrade() {
        double avg = getAverage();
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %-20s | Roll: %-10s | Avg: %.2f | Grade: %s",
                id, name, rollNo, getAverage(), getGrade());
    }
}