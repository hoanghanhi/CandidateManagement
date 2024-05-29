package model.dao;

import common.Log;
import model.entities.Candidate;
import model.entities.Intern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InternDAO extends CandidateDAO{
    public InternDAO() {
    }

    public void addData(Candidate candidate) {
        super.addData(candidate);
        Scanner scanner = new Scanner(System.in);
        candidate.setCandidateType(2);
        Intern intern = (Intern) candidate;
        System.out.println("Input major: ");
        intern.setMajors(scanner.nextLine());
        System.out.println("Input semester : ");
        intern.setSemester(Integer.parseInt(scanner.nextLine()));
        System.out.println("Input university name: ");
        intern.setUniversityName(scanner.nextLine());
    }

    public void addData(Candidate candidate, Connection connection){
        super.addData(candidate, connection);
        try {
            String query = "insert into Intern(CandidateID, Majors, Semester, UniversityName) values(?,?,?,?)";
            Log.info(query);
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1,candidate.getCandidateID());
            myStmt.setString(2,((Intern) candidate).getMajors());
            myStmt.setInt(3,((Intern) candidate).getSemester());
            myStmt.setString(4,((Intern) candidate).getUniversityName());

            myStmt.execute();
        } catch (SQLException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
