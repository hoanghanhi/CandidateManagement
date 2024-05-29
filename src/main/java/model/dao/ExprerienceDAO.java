package model.dao;

import common.DatabaseLogger;
import common.Log;
import model.entities.Candidate;
import model.entities.Experience;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ExprerienceDAO extends CandidateDAO {
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
        try (
                PreparedStatement myStmt = connection.prepareStatement("Select * from Experience", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = myStmt.executeQuery();
        ) {
//            String query = "insert into Experience(CandidateID, ExpInYear, ProSkill) values(?,?,?)";
//            Log.info(query);
//            PreparedStatement myStmt = connection.prepareStatement(query);
//            myStmt.setInt(1,candidate.getCandidateID());
//            myStmt.setInt(2,((Experience) candidate).getExpInYear());
//            myStmt.setString(3, ((Experience) candidate).getProSkill());
//
//            myStmt.execute();

            rs.moveToInsertRow();
            DatabaseLogger.logDatabaseConnection("Select * from Experience");
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateInt(2, ((Experience) candidate).getExpInYear());
            rs.updateString(3, ((Experience) candidate).getProSkill());
            rs.insertRow();
        } catch (SQLException e) {
            DatabaseLogger.logDatabaseException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateById(Candidate candidate, Connection connection) {
        super.updateById(candidate, connection);
        try (
                PreparedStatement myStmt = connection.prepareStatement("Select * from Experience", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = myStmt.executeQuery();
        ) {
            DatabaseLogger.logDatabaseConnection("Select * from Experience");
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateInt(2, ((Experience) candidate).getExpInYear());
            rs.updateString(3, ((Experience) candidate).getProSkill());
            rs.insertRow();
        } catch (Exception e) {
            DatabaseLogger.logDatabaseException(e);
            e.printStackTrace();
        }
    }

    @Override
    public void updateData(Candidate candidate) {
        super.updateData(candidate);
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        while (check) {
            System.out.println("------------------------------------------------------");
            System.out.println("Update Experice: ");
            System.out.println("1. Update year of experience");
            System.out.println("2. Update proskill");
            System.out.println("3. Exit");
            System.out.println("------------------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter year of experience");
                    int expInYear = Integer.parseInt(scanner.nextLine());
                    ((Experience) candidate).setExpInYear(expInYear);
                    candidate.showMe();
                    break;
                case 2:
                    System.out.println("Enter proSkill");
                    String proSkill = scanner.nextLine();
                    ((Experience) candidate).setProSkill(proSkill);
                    candidate.showMe();
                    break;
                case 3:
                    check = false;
                    break;
                default:
                    break;
            }
        }
    }
}
