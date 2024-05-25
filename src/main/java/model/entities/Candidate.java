package model.entities;

import java.sql.Date;

public class Candidate {
    private static int candidateCount;

    private int candidateID;
    private String fullName;
    private String birthDay;
    private String phone;
    private String email;
    private int candidateType;

    public static int getCandidateCount() {
        return candidateCount;
    }

    public static void setCandidateCount(int candidateCount) {
        Candidate.candidateCount = candidateCount;
    }

    public int getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(int candidateType) {
        this.candidateType = candidateType;
    }

    public Candidate() {
        candidateCount++;
    }

    public Candidate(int candidateID, String fullName, String birthDay, String phone, String email, int candidateType) {
        this();
        this.candidateID = candidateID;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.phone = phone;
        this.email = email;
        this.candidateType = candidateType;
    }

    public void showMe() {
        System.out.println("Candidate ID: " + candidateID);
        System.out.println("Full Name: " + fullName);
        System.out.println("Birth Date: " + birthDay);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Candidate Type: " + candidateType);
    }

}
