package Internship;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReportExporter {

    public static String export(ArrayList<Student> students,
                                String exportedBy) throws Exception {
        String filename = "GradeReport_" + exportedBy + "_"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"))
                + ".txt";
        PrintWriter pw = new PrintWriter(new FileWriter(filename));

        pw.println("============================================");
        pw.println("     STUDENT GRADE TRACKER — REPORT");
        pw.println("     Exported by : " + exportedBy);
        pw.println("     Date        : " + LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        pw.println("     Total Students: " + students.size());
        pw.println("============================================\n");

        if (students.isEmpty()){
            pw.println("No student records found.");
            pw.close();
            return filename;
        }

        double classTotal = 0;
        Student topStudent    = students.get(0);
        Student bottomStudent = students.get(0);

        for (Student s : students){
            classTotal += s.getAverage();
            if (s.getAverage() > topStudent.getAverage())    topStudent    = s;
            if (s.getAverage() < bottomStudent.getAverage()) bottomStudent = s;
        }
        double classAvg = classTotal / students.size();

        pw.println("CLASS SUMMARY");
        pw.println("--------------------------------------------");
        pw.printf("  Class Average  : %.2f%%%n", classAvg);
        pw.printf("  Top Student    : %s (%.2f%%)%n",
                topStudent.getName(), topStudent.getAverage());
        pw.printf("  Lowest Student : %s (%.2f%%)%n",
                bottomStudent.getName(), bottomStudent.getAverage());
        pw.println("--------------------------------------------\n");

        pw.println("INDIVIDUAL RECORDS");
        pw.println("--------------------------------------------");

        int i = 1;
        for (Student s : students) {
            pw.println("Record #" + i++);
            pw.println("  Name     : " + s.getName());
            pw.println("  Roll No  : " + s.getRollNo());
            pw.printf ("  Average  : %.2f%%%n", s.getAverage());
            pw.printf ("  Highest  : %.2f%%%n", s.getHighest());
            pw.printf ("  Lowest   : %.2f%%%n", s.getLowest());
            pw.println("  Grade    : " + s.getGrade());
            pw.println("  Added By : " + s.getAddedBy());
            pw.println("--------------------------------------------");
        }

        pw.close();
        return filename;
    }
}