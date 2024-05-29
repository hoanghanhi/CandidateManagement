package main;

import common.ConnectionPool;
import controller.AppController;
import controller.CandidateController;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppController appController = new AppController();
        CandidateController candidateController = new CandidateController();
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        try (
                Connection connectionPool = ConnectionPool.getConnection();
                ) {
            do {
                appController.showMenu();
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        candidateController.addCandidate();
                        break;
                    case 2:
                        candidateController.showFullName();
                        break;
                    case 3:
                        candidateController.showAll();
                        break;
                    case 4:
                        candidateController.showSortedList();
                        break;
                    case 5:
                        candidateController.updateById();
                        break;
                    default:
                        System.out.println("...");
                        break;
                }
            } while (choice != 6);
        }catch (Exception e){
            System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
        }

//        log.logFile();
    }
}
