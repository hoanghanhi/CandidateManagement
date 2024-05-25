package model.dao;

import model.entities.Candidate;
import model.entities.Fresher;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class FresherDAO extends CandidateDAO{
    public FresherDAO() {
    }

    public void addData(Candidate candidate) {
        super.addData(candidate);
        Scanner scanner = new Scanner(System.in);
        candidate.setCandidateType(1);
        Fresher fresher = (Fresher) candidate;
        System.out.println("Input graduate date : ");
        fresher.setGraduateDate(scanner.nextLine());
        System.out.println("Input graduate rank : ");
        fresher.setGraduateRank(scanner.nextLine());
        System.out.println("Input education : ");
        fresher.setEducation(scanner.nextLine());
    }

    public void addData(Candidate candidate, Connection connection) {
        super.addData(candidate, connection);
        try {
            String query = "insert into Fresher(CandidateID, GraduateDate, GraduateRank, Education) values(?,?,?,?)";
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1,candidate.getCandidateID());
            myStmt.setString(2,((Fresher)candidate).getGraduateDate());
            myStmt.setString(3,((Fresher) candidate).getGraduateRank());
            myStmt.setString(4,((Fresher) candidate).getEducation());
            myStmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
