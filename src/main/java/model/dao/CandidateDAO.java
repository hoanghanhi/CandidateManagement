package model.dao;

import common.*;
import exception.EmailException;
import model.entities.Candidate;
import model.entities.Experience;
import model.entities.Fresher;
import model.entities.Intern;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void updateData(Candidate candidate){
        ValidateData validateData = new ValidateData();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Update field: ");
        boolean check = true;
        while (check){
            System.out.println("------------------------------------------------------");
            System.out.println("1. Update fullname");
            System.out.println("2. Update birthday");
            System.out.println("3. Update phone");
            System.out.println("4. Update email");
            System.out.println("5. Continue.");
            System.out.println("------------------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            while (choice < 1 || choice > 5){
                System.out.println("Moi ban nhap lua chon: ");
                choice = Integer.parseInt(scanner.nextLine());
            }
            switch (choice) {
                case 1:
                    System.out.println("Moi ban nhap full name");
                    String fullName = scanner.nextLine();
                    candidate.setFullName(fullName);
                    candidate.showMe();
                    break;
                case 2:
                    String birthday;
                    do {
                        birthday = scanner.nextLine();
                    } while (ConvertStringToDate.convertStringToDate(birthday) == null);
                    candidate.setBirthDay(ConvertStringToDate.convertStringToDate(birthday));

                    candidate.showMe();
                    break;
                case 3:
                    System.out.println("Ban hay nhap so dien thoai");
                    String phone = scanner.nextLine();
                    candidate.setPhone(phone);
                    candidate.showMe();
                    break;
                case 4:
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
                    candidate.showMe();
                    break;
                case 5:
                    check = false;
                    break;
            }
        }
    }

    public void addData(Candidate candidate, Connection connection) {
        try {
//            String query = "insert into Candidate(CandidateID, Fullname,Birthday, Phone, Email, CandidateType) values(?,?,?,?,?,?)";
//            Log.info(query);
//            PreparedStatement myStmt = connection.prepareStatement(query);
//            myStmt.setInt(1, candidate.getCandidateID());
//            myStmt.setString(2, candidate.getFullName());
//            myStmt.setDate(3, candidate.getBirthDay());
//            myStmt.setString(4, candidate.getPhone());
//            myStmt.setString(5, candidate.getEmail());
//            myStmt.setInt(6, candidate.getCandidateType());
//
//            myStmt.execute();
            PreparedStatement myStmt = connection.prepareStatement("SELECT * FROM candidate", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = myStmt.executeQuery();
            rs.moveToInsertRow();
            DatabaseLogger.logDatabaseConnection("SELECT * FROM candidate");
            rs.updateInt(1, candidate.getCandidateID());
            rs.updateString(2, candidate.getFullName());
            rs.updateDate(3, candidate.getBirthDay());
            rs.updateString(4, candidate.getPhone());
            rs.updateString(5, candidate.getEmail());
            rs.updateInt(6, candidate.getCandidateType());

            rs.insertRow();
        } catch (SQLException e) {
            DatabaseLogger.logDatabaseException(e);
            throw new RuntimeException(e);
        }
    }

    public boolean listFullName() {
        String query = "select Fullname from Candidate";
//        Log.info(query);
        DatabaseLogger.logDatabaseConnection(query);
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
            DatabaseLogger.logDatabaseException(e);
            throw new RuntimeException(e);
        }
        return true;
    }


    public List<Candidate> getList() {
        String query = "select * from Candidate left JOIN Experience ON Candidate.CandidateID = Experience.CandidateID  left JOIN Fresher ON Candidate.CandidateID = Fresher.CandidateID left JOIN Intern ON Candidate.CandidateID = Intern.CandidateID";
        DatabaseLogger.logDatabaseConnection(query);
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
            DatabaseLogger.logDatabaseException(e);
            throw new RuntimeException(e);
        } catch (EmailException e) {
            DatabaseLogger.logDatabaseException(e);
            throw new RuntimeException(e);
        }
    }

    private Candidate getCandidate(Candidate candidate, ResultSet rs) throws SQLException, EmailException{
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
        return candidate;
    }

    public Candidate getCandidateById(int id) {
        try (
                Connection connectionPool = ConnectionPool.getConnection();
                PreparedStatement stmt = connectionPool.prepareStatement("SELECT * FROM Candidate left JOIN Experience ON Candidate.CandidateID = Experience.CandidateID  left JOIN Fresher ON Candidate.CandidateID = Fresher.CandidateID left JOIN Intern ON Candidate.CandidateID = Intern.CandidateID WHERE Candidate.CandidateID = ?");
        ) {
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            Candidate candidate = null;
            while (rs.next()) {
                candidate = getCandidate(candidate,rs);
            }
            return candidate;
        } catch (Exception e) {
            DatabaseLogger.logDatabaseException(e);
            e.printStackTrace();
        }
        return null;
    }

    public void updateById(Candidate candidate, Connection connectionPool) {
        try (
                PreparedStatement stmt = connectionPool.prepareStatement("SELECT * FROM Candidate WHERE CandidateID = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ) {
            DatabaseLogger.logDatabaseConnection("SELECT * FROM Candidate WHERE CandidateID = ?");
            stmt.setInt(1,candidate.getCandidateID());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                rs.absolute(1);
                rs.updateInt(1, candidate.getCandidateID());
                rs.updateString(2, candidate.getFullName());
                rs.updateDate(3, candidate.getBirthDay());
                rs.updateString(4, candidate.getPhone());
                rs.updateString(5, candidate.getEmail());
                rs.updateInt(6, candidate.getCandidateType());

                rs.insertRow();
            }
        } catch (Exception e) {
            DatabaseLogger.logDatabaseException(e);
            e.printStackTrace();
        }
    }


}
