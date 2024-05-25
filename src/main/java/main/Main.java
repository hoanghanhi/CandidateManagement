package main;

import controller.AppController;
import controller.CandidateController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AppController appController = new AppController();
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            appController.showMenu();
            choice = scanner.nextInt();
            CandidateController candidateController = new CandidateController();
            switch (choice) {
                case 1:
                    candidateController.addCandidate();
                    break;
                case 2:
                    candidateController.showFullName();
                    break;
                case 3:

                default:
                    System.out.println("...");
                    break;
            }
        } while (choice != 6);

    }
}
