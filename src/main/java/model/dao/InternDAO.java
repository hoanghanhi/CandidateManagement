package model.dao;

import common.DatabaseLogger;
import common.Log;
import model.entities.Candidate;
import model.entities.Intern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InternDAO extends CandidateDAO {
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

    @Override
    public void updateData(Candidate candidate) {
        super.updateData(candidate);
        Scanner scanner = new Scanner(System.in);
        boolean check = true;
        while (check){
            System.out.println("------------------------------------------------------");
            System.out.println("Update Intern: ");
            System.out.println("1. Update major: ");
            System.out.println("2. Update semester: ");
            System.out.println("3. Update university name: ");
            System.out.println("4. Exit");
            System.out.println("------------------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter major");
                    String majors = scanner.nextLine();
                    ((Intern) candidate).setMajors(majors);
                    candidate.showMe();
                    break;
                case 2:
                    System.out.println("Enter semester");
                    int semester= Integer.parseInt(scanner.nextLine());
                    ((Intern) candidate).setSemester(semester);
                    candidate.showMe();
                    break;
                case 3:
                    System.out.println("Enter university name");
                    String universityName = scanner.nextLine();
                    ((Intern) candidate).setUniversityName(universityName);
                    candidate.showMe();
                case 4:
                    check = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void addData(Candidate candidate, Connection connection) {
        super.addData(candidate, connection);
        try (
                PreparedStatement myStmt = connection.prepareStatement("Select * from Intern", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = myStmt.executeQuery();
        ) {
//            String query = "insert into Intern(CandidateID, Majors, Semester, UniversityName) values(?,?,?,?)";
//            Log.info(query);
//            PreparedStatement myStmt = connection.prepareStatement(query);
//            myStmt.setInt(1,candidate.getCandidateID());
//            myStmt.setString(2,((Intern) candidate).getMajors());
//            myStmt.setInt(3,((Intern) candidate).getSemester());
//            myStmt.setString(4,((Intern) candidate).getUniversityName());
//
//            myStmt.execute();

            DatabaseLogger.logDatabaseConnection("Select * from Intern");
            rs.moveToInsertRow();
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateString(2, ((Intern) candidate).getMajors());
            rs.updateInt(3, ((Intern) candidate).getSemester());
            rs.updateString(4, ((Intern) candidate).getUniversityName());
            rs.insertRow();
        } catch (SQLException e) {
            DatabaseLogger.logDatabaseException(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateById(Candidate candidate, Connection connectionPool) {
        super.updateById(candidate, connectionPool);
        try (
                PreparedStatement myStmt = connectionPool.prepareStatement("Select * from Intern", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = myStmt.executeQuery();
        ) {
            DatabaseLogger.logDatabaseConnection("Select * from Intern");
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateString(2, ((Intern) candidate).getMajors());
            rs.updateInt(3, ((Intern) candidate).getSemester());
            rs.updateString(4, ((Intern) candidate).getUniversityName());
            rs.insertRow();
        } catch (Exception e) {
            DatabaseLogger.logDatabaseException(e);
            e.printStackTrace();
        }
    }
}
