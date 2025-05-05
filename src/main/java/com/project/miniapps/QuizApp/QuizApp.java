package com.project.miniapps.QuizApp;

import java.util.Scanner;

public class QuizApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int score = 0;

        String[][] questions = {
                {"Capital Of India?", "New Delhi"},
                {"5 + 3 = ?", "8"},
                {"Java is a (Language/Car)?", "Language"}
        };

        for (String[] q : questions){
            System.out.println(q[0]);
            String ans = sc.nextLine();
            if (ans.equalsIgnoreCase(q[1])){
                score++;
            }
        }
        System.out.println("Your score: " + score + "/" + questions.length);
    }
}
