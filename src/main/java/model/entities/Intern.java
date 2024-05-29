package model.entities;

import exception.EmailException;

import java.sql.Date;

public class Intern extends Candidate{
    private int candidateID;
    private String majors;
    private int semester;
    private String universityName;

    public Intern() {
    }

    public Intern(int candidateID, String fullName, Date birthDay, String phone, String email, int candidateType, int candidateID1, String majors, int semester, String universityName) throws EmailException {
        super(candidateID, fullName, birthDay, phone, email, candidateType);
        this.candidateID = candidateID1;
        this.majors = majors;
        this.semester = semester;
        this.universityName = universityName;
    }

    @Override
    public int getCandidateID() {
        return candidateID;
    }

    @Override
    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    @Override
    public String showMe() {
        return super.toString() + "Intern{" +
                ", majors='" + majors + '\'' +
                ", semester=" + semester +
                ", universityName='" + universityName + '\'' +
                '}';
    }
}
