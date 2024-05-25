package model.entities;

import java.sql.Date;

public class Experience extends Candidate {
    private int candidateID;
    private int expInYear;
    private String proSkill;

    public Experience() {
    }

    public Experience(int candidateID, String fullName, String birthDay, String phone, String email, int candidateType, int candidateID1, int expInYear, String proSkill) {
        super(candidateID, fullName, birthDay, phone, email, candidateType);
        this.candidateID = candidateID1;
        this.expInYear = expInYear;
        this.proSkill = proSkill;
    }

    @Override
    public int getCandidateID() {
        return candidateID;
    }

    @Override
    public void setCandidateID(int candidateID) {
        this.candidateID = candidateID;
    }

    public int getExpInYear() {
        return expInYear;
    }

    public void setExpInYear(int expInYear) {
        this.expInYear = expInYear;
    }

    public String getProSkill() {
        return proSkill;
    }

    public void setProSkill(String proSkill) {
        this.proSkill = proSkill;
    }

    @Override
    public void showMe() {
        super.showMe();
        System.out.println("Experience year: " + expInYear);
        System.out.println("Professor skill: " + proSkill);
    }
}
