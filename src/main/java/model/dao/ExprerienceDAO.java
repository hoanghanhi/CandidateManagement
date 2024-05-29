package model.dao;

import common.Log;
import model.entities.Candidate;
import model.entities.Experience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ExprerienceDAO extends CandidateDAO{
    public ExprerienceDAO() {
    }

    public void addData(Candidate candidate) {
        super.addData(candidate);

        Scanner scanner = new Scanner(System.in);
        candidate.setCandidateType(0);
        Experience experience = (Experience) candidate;
        System.out.println("Input year of experience : ");
        experience.setExpInYear(Integer.parseInt(scanner.nextLine()));
        System.out.println("Input professor skill : ");
        experience.setProSkill(scanner.nextLine());
    }

    public void addData(Candidate candidate, Connection connection) {
        super.addData(candidate, connection);
        try {
            String query = "insert into Experience(CandidateID, ExpInYear, ProSkill) values(?,?,?)";
            Log.info(query);
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1,candidate.getCandidateID());
            myStmt.setInt(2,((Experience) candidate).getExpInYear());
            myStmt.setString(3, ((Experience) candidate).getProSkill());

            myStmt.execute();
        } catch (SQLException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
