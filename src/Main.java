import ca.concordia.coen346.server.PIDManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        Scanner reader = new Scanner(System.in);
        System.out.println("Choose your Name: ");

        String hello  = reader.nextLine();
        System.out.println("your destiny is " + hello);

    }
}