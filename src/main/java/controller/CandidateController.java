package controller;

import common.ConnectionPool;
import common.Log;
import common.SortCandidate;
import model.dao.CandidateDAO;
import model.dao.ExprerienceDAO;
import model.dao.FresherDAO;
import model.dao.InternDAO;
import model.entities.Candidate;
import model.entities.Experience;
import model.entities.Fresher;
import model.entities.Intern;

import java.sql.Connection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CandidateController {
    public void addCandidate(List<Candidate> candidateList) {

        try (
                Connection connectionPool = ConnectionPool.getConnection();

        ) {
            connectionPool.setAutoCommit(false);

            for (Candidate candidate : candidateList) {
                if (candidate instanceof Experience) {
                    ExprerienceDAO exprerienceDAO = new ExprerienceDAO();
                    exprerienceDAO.addData(candidate, connectionPool);
                } else if (candidate instanceof Fresher) {
                    FresherDAO fresherDAO = new FresherDAO();
                    fresherDAO.addData(candidate, connectionPool);
                } else if (candidate instanceof Intern) {
                    InternDAO internDAO = new InternDAO();
                    internDAO.addData(candidate, connectionPool);
                }
            }
            connectionPool.commit();
            connectionPool.setAutoCommit(true);
        } catch (Exception e) {
            Log.error(e.getMessage());
//            try {
//                connectionPool.rollback();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
            System.out.println("Error (All information will not save to database).");
        }
    }

    public void addCandidate() {
        Scanner scanner = new Scanner(System.in);
        String opt = "c";
        List<Candidate> candidateList = new LinkedList<>();
        do {
            System.out.println("moi ban chon loai candidate");
            int type = Integer.parseInt(scanner.nextLine());
            switch (type) {
                case 0:
                    Candidate experience = new Experience();
                    CandidateDAO experienceDAO = new ExprerienceDAO();
                    experienceDAO.addData(experience);
                    candidateList.add(experience);
                    break;
                case 1:
                    Candidate fresher = new Fresher();
                    CandidateDAO fresherDAO = new FresherDAO();
                    fresherDAO.addData(fresher);
                    candidateList.add(fresher);
                    break;
                case 2:
                    Candidate intern = new Intern();
                    CandidateDAO internDAO = new InternDAO();
                    internDAO.addData(intern);
                    candidateList.add(intern);
            }
            System.out.println("continue?");
            opt = scanner.nextLine();
        } while (!opt.equals("q"));
        CandidateController candidateController = new CandidateController();
        candidateController.addCandidate(candidateList);
        System.out.println("num: " + Candidate.getCandidateCount());
        Candidate.setCandidateCount(0);
    }

    public void showFullName() {
        CandidateDAO candidateDAO = new CandidateDAO();
        candidateDAO.listFullName();
    }

    public void showAll() {
        try {
            CandidateDAO candidateDAO = new CandidateDAO();

            showMe(candidateDAO.getList());
        }catch (Exception e){
            Log.error(e.getMessage());
            System.out.println("Error");
        }
    }
    public void showMe(List<Candidate> candidateList) {
        try {
            for (Candidate candidate : candidateList) {
                System.out.println(candidate.showMe());
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
            System.out.println("Error");
        }
    }

    public void showSortedList() {
        try {
            CandidateDAO candidateDAO = new CandidateDAO();
            List<Candidate> candidateList = candidateDAO.getList();
            Collections.sort(candidateList, new SortCandidate());
            showMe(candidateList);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error");
            Log.error(e.getMessage());
        }
    }
}
