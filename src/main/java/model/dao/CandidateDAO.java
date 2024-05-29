package model.dao;

import common.ConnectionPool;
import common.Log;
import exception.EmailException;
import model.entities.Candidate;
import model.entities.Experience;
import model.entities.Fresher;
import model.entities.Intern;
import common.ConvertStringToDate;
import common.ValidateData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class CandidateDAO {
    ValidateData validateData = new ValidateData();

    public CandidateDAO() {
    }

    public void addData(Candidate candidate) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input id: ");
        candidate.setCandidateID(Integer.parseInt(scanner.nextLine()));
        System.out.println("Input name : ");
        candidate.setFullName(scanner.nextLine());
        System.out.println("Input birthday: ");
        String birthday;
        do {
            birthday = scanner.nextLine();
        } while (ConvertStringToDate.convertStringToDate(birthday) == null);
        candidate.setBirthDay(ConvertStringToDate.convertStringToDate(birthday));
        String email;
        do {
            try {
                System.out.println("Input email:");
                email = scanner.nextLine();
                if (email.isEmpty()) {
                    throw new EmailException("Not empty this field");
                } else if (!validateData.validateEmail(email)) {
                    throw new EmailException("Invalid format email");
                }
            } catch (EmailException e) {
                System.out.println(e.getMessage());
                Log.error(e.getMessage());
                email = "";
            } catch (Exception e) {
                System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
                Log.error(e.getMessage());
                email = "";
            }
        } while (!validateData.validateEmail(email));
        candidate.setEmail(email);
        System.out.println("Input phone : ");
        candidate.setPhone(scanner.nextLine());
    }

    public void addData(Candidate candidate, Connection connection) {
        try {
            String query = "insert into Candidate(CandidateID, Fullname,Birthday, Phone, Email, CandidateType) values(?,?,?,?,?,?)";
            Log.info(query);
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1, candidate.getCandidateID());
            myStmt.setString(2, candidate.getFullName());
            myStmt.setDate(3, candidate.getBirthDay());
            myStmt.setString(4, candidate.getPhone());
            myStmt.setString(5, candidate.getEmail());
            myStmt.setInt(6, candidate.getCandidateType());

            myStmt.execute();

        } catch (SQLException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean listFullName() {
        String query = "select Fullname from Candidate";
        Log.info(query);
        List<String> fullName = new ArrayList<>();
        int count = 0;
        try (
                Connection connectionPool = ConnectionPool.getConnection();
                PreparedStatement stmt = connectionPool.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                fullName.add(rs.getString(1));
            }
            for (String s : fullName) {
                StringBuffer stringBuffer = new StringBuffer(", ");
                if (count < fullName.size() - 1) {
                    System.out.print(s + stringBuffer);
                    count++;
                } else {
                    System.out.print(s);
                }
            }
            System.out.println();
        } catch (SQLException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }


    public List<Candidate> getList() {
        String query = "select * from Candidate left JOIN Experience ON Candidate.CandidateID = Experience.CandidateID  left JOIN Fresher ON Candidate.CandidateID = Fresher.CandidateID left JOIN Intern ON Candidate.CandidateID = Intern.CandidateID";
        Log.info(query);
        List<Candidate> candidateList = new ArrayList<>();
        try (
                Connection connectionPool = ConnectionPool.getConnection();
                PreparedStatement stmt = connectionPool.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Candidate candidate = null;
                int candidateID = rs.getInt("CandidateID");
                String fullname = rs.getString("FullName");
                Date birthday = rs.getDate("BirthDay");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                int type = rs.getInt("CandidateType");
                if (type == 0) {
                    int year = rs.getInt("ExpInYear");
                    String skill = rs.getString("ProSkill");
                    candidate = new Experience(candidateID, fullname, birthday, phone, email, type, candidateID, year, skill);
                } else if (type == 1) {
                    Date date = rs.getDate("GraduateDate");
                    String rank = rs.getString("GraduateRank");
                    String education = rs.getString("Education");
                    candidate = new Fresher(candidateID, fullname, birthday, phone, email, type, candidateID, date, rank, education);
                } else if (type == 2) {
                    String major = rs.getString("Majors");
                    int semester = rs.getInt("Semester");
                    String university = rs.getString("UniversityName");
                    candidate = new Intern(candidateID, fullname, birthday, phone, email, type, candidateID, major, semester, university);
                }
                candidateList.add(candidate);
            }
            return candidateList;
        } catch (SQLException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (EmailException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }





}
