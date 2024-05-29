package model.dao;

import common.DatabaseLogger;
import common.Log;
import model.entities.Candidate;
import model.entities.Fresher;
import common.ConvertStringToDate;

import java.sql.*;
import java.util.Scanner;

public class FresherDAO extends CandidateDAO {
    public FresherDAO() {
    }

    public void addData(Candidate candidate) {
        super.addData(candidate);
        Scanner scanner = new Scanner(System.in);
        candidate.setCandidateType(1);
        Fresher fresher = (Fresher) candidate;
        System.out.println("Input graduate date : ");
        fresher.setGraduateDate(ConvertStringToDate.convertStringToDate(scanner.nextLine()));
        System.out.println("Input graduate rank : ");
        fresher.setGraduateRank(scanner.nextLine());
        System.out.println("Input education : ");
        fresher.setEducation(scanner.nextLine());
    }

    public void addData(Candidate candidate, Connection connection) {
        super.addData(candidate, connection);
        try (
                PreparedStatement myStmt = connection.prepareStatement("Select * from Fresher", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = myStmt.executeQuery();
        ) {
//            String query = "insert into Fresher(CandidateID, GraduateDate, GraduateRank, Education) values(?,?,?,?)";
//            Log.info(query);
//            PreparedStatement myStmt = connection.prepareStatement(query);
//            myStmt.setInt(1,candidate.getCandidateID());
//            myStmt.setDate(2,((Fresher)candidate).getGraduateDate());
//            myStmt.setString(3,((Fresher) candidate).getGraduateRank());
//            myStmt.setString(4,((Fresher) candidate).getEducation());
//            myStmt.execute();
            DatabaseLogger.logDatabaseConnection("Select * from Fresher");
            rs.moveToInsertRow();
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateDate(2, ((Fresher) candidate).getGraduateDate());
            rs.updateString(3, ((Fresher) candidate).getGraduateRank());
            rs.updateString(4, ((Fresher) candidate).getEducation());
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
                PreparedStatement myStmt = connectionPool.prepareStatement("Select * from Fresher", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = myStmt.executeQuery();
        ) {
            DatabaseLogger.logDatabaseConnection("Select * from Fresher");
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateDate(2, ((Fresher) candidate).getGraduateDate());
            rs.updateString(3, ((Fresher) candidate).getGraduateRank());
            rs.updateString(4, ((Fresher) candidate).getEducation());
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
        while (check){
            System.out.println("------------------------------------------------------");
            System.out.println("Update Fresher: ");
            System.out.println("1. Update Graduate date: ");
            System.out.println("2. Update graduate rank: ");
            System.out.println("3. Update education: ");
            System.out.println("4. Exit ");
            System.out.println("------------------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter Graduate date ");
                    Date granduateDate = ConvertStringToDate.convertStringToDate(scanner.nextLine());
                    ((Fresher) candidate).setGraduateDate(granduateDate);
                    candidate.showMe();
                    break;
                case 2:
                    System.out.println("Enter graduate rank ");
                    String graduateRank = scanner.nextLine();
                    ((Fresher) candidate).setGraduateRank(graduateRank);
                    candidate.showMe();
                    break;
                case 3:
                    System.out.println("Enter education ");
                    String education= scanner.nextLine();
                    ((Fresher) candidate).setEducation(education);
                    candidate.showMe();
                case 4:
                    check = false;
                    break;
                default:
                    break;
            }
        }
    }


    //    @Override
//    public void showMe(Candidate candidate) {
//        super.showMe(candidate);
//        System.out.print("\tGraduate date: " + ((Fresher)candidate).getGraduateDate());
//        System.out.print("\tGraduate rank: " + ((Fresher)candidate).getGraduateRank());
//        System.out.print("\tEducation: " + ((Fresher)candidate).getEducation());
//
//    }
}
