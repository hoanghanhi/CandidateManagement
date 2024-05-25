package model.dao;

import common.Connect;
import exception.BirthdayException;
import exception.EmailException;
import model.entities.Candidate;
import common.ValidateData;

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
        System.out.println("hihihi");
        String birthday;
        do {
            try{
                System.out.println("Input birthday: ");
                birthday = scanner.nextLine();
                if (birthday.isEmpty()) {
                    throw new BirthdayException("Not empty this field");
                }else if (Integer.parseInt(birthday.substring(6,10)) < 1900) {
                    throw new BirthdayException("Please enter older 1900: ");
                }else if (!validateData.validateDate(birthday)) {
                    throw new BirthdayException("Invalid format date");
                }
            } catch (BirthdayException e){
                System.out.println(e.getMessage());
                birthday = "";
            } catch (Exception e){
                System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
                birthday = "";
            }
        } while (!validateData.validateDate(birthday));
        candidate.setBirthDay(birthday);
        String email;
        do {
            try {
                System.out.println("input email:");
                email = scanner.nextLine();
                if (email.isEmpty()) {
                    throw new EmailException("Not empty this field");
                } else if (!validateData.validateEmail(email)) {
                    throw new EmailException("Invalid format email");
                }
            } catch (EmailException e) {
                System.out.println(e.getMessage());
                email = "";
            } catch (Exception e) {
                System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
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
            PreparedStatement myStmt = connection.prepareStatement(query);
            myStmt.setInt(1,candidate.getCandidateID());
            myStmt.setString(2,candidate.getFullName());
            myStmt.setString(3, candidate.getBirthDay());
            myStmt.setString(4, candidate.getPhone());
            myStmt.setString(5, candidate.getEmail());
            myStmt.setInt(6,candidate.getCandidateType());

            myStmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean listFullName (){
        Connection connection = Connect.getConnection();
        String query = "select Fullname from Candidate";
        List<String> fullName = new ArrayList<>();
        int count = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                fullName.add(rs.getString(1));
            }
            for (String s : fullName){
                StringBuffer stringBuffer = new StringBuffer(", ");
                if (count < fullName.size()-1) {
                    System.out.print(s + stringBuffer);
                    count++;
                } else {
                    System.out.print(s);
                }
            }
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
