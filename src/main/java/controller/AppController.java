package controller;

public class AppController {
    public void showMenu() {
        System.out.println("-------------CANDIDATE MANAGEMENT---------");
        System.out.println("1. Add new candidate");
        System.out.println("2. Display fullname of all candidates");
        System.out.println("3. Display all candidates");
        System.out.println("4. Display sorted candidates");
        System.out.println("5. Update candidate");
        System.out.println("6. Exit");
        System.out.println("-----------------------------------------");
        System.out.print("Enter your choice: ");
    }
}
