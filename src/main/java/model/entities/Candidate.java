package model.entities;

import exception.EmailException;

import java.sql.Date;

public abstract class Candidate {
    private static int candidateCount;

    private int candidateID;
    private String fullName;
    private Date birthDay;
    private String phone;
    private String email;
    private Integer candidateType;

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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
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

    public Integer getCandidateType() {
        return candidateType;
    }

    public void setCandidateType(Integer candidateType) {
        this.candidateType = candidateType;
    }

    public Candidate() {
        candidateCount++;
    }

    public Candidate(int candidateID, String fullName, Date birthDay, String phone, String email, Integer candidateType) throws EmailException {
        this();
        this.candidateID = candidateID;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.phone = phone;
        this.email = email;
        this.candidateType = candidateType;
    }

    public abstract String showMe();

    @Override
    public String toString() {
        return "Candidate{" +
                "candidateID=" + candidateID +
                ", fullName='" + fullName + '\'' +
                ", birthDay=" + birthDay +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", candidateType=" + candidateType +
                '}';
    }
}
