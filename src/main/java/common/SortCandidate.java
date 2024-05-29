package common;

import model.entities.Candidate;

import java.util.Comparator;

public class SortCandidate implements Comparator<Candidate> {

    @Override
    public int compare(Candidate c1, Candidate c2) {
        int typeCompare = c1.getCandidateType().compareTo(c2.getCandidateType());
        if (typeCompare != 0) {
            return typeCompare;
        }

        if(c1.getBirthDay() == null) return -1;
        if(c2.getBirthDay() == null) return 1;
        return c1.getBirthDay().compareTo(c2.getBirthDay());
    }
}