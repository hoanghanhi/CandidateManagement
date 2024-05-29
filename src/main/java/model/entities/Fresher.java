package model.entities;

import exception.EmailException;

import java.sql.Date;

public class Fresher extends Candidate {
    private int candidateID;
    private Date graduateDate;
    private String graduateRank;
    private String education;

    public Fresher() {
    }

    public Fresher(int candidateID, String fullName, Date birthDay, String phone, String email, int candidateType, int candidateID1, Date graduateDate, String graduateRank, String education) throws EmailException {
        super(candidateID, fullName, birthDay, phone, email, candidateType);
        this.candidateID = candidateID1;
        this.graduateDate = graduateDate;
        this.graduateRank = graduateRank;
        this.education = education;
    }

    @Override
    public int getCandidateID() {
        return candidateID;
    }

    @Override
    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public Date getGraduateDate() {
        return graduateDate;
    }

    public void setGraduateDate(Date graduateDate) {
        this.graduateDate = graduateDate;
    }

    public String getGraduateRank() {
        return graduateRank;
    }

    public void setGraduateRank(String graduateRank) {
        this.graduateRank = graduateRank;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public String showMe() {
        return super.toString() + "Fresher{"  +
                ", graduateDate=" + graduateDate +
                ", graduateRank='" + graduateRank + '\'' +
                ", education='" + education + '\'' +
                '}';
    }
}
