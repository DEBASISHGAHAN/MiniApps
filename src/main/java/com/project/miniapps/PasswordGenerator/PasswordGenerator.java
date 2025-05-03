package com.project.miniapps.PasswordGenerator;

import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {
    public static void main(String[] args) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter password length: ");
        int length = sc.nextInt();

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i=0; i<length; i++){
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        System.out.println("Generated Password: " + password);
    }
}
