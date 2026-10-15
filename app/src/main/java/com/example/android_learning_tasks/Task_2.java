package com.example.android_learning_tasks;

import java.util.Scanner;

public class Task_2 {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter marks of subject 1: ");
        double marks1 = input.nextDouble();
        checkMarks(marks1);

        System.out.print("Enter marks of subject 2: ");
        double marks2 = input.nextDouble();
        checkMarks(marks2);

        System.out.print("Enter marks of subject 3: ");
        double marks3 = input.nextDouble();
        checkMarks(marks3);

        System.out.print("Enter marks of subject 4: ");
        double marks4 = input.nextDouble();
        checkMarks(marks4);

        System.out.print("Enter marks of subject 5: ");
        double marks5 = input.nextDouble();
        checkMarks(marks5);

        double[] marks = {marks1, marks2, marks3, marks4, marks5};

        double totalMarks = marks1 + marks2 + marks3 + marks4 + marks5;
        double percentage = (totalMarks / 500) * 100;

        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Percentage: " + percentage + "%");
        printGrade(percentage);

        boolean isFail = false;
        for (double mark : marks) {
            if (mark < 35) {
                isFail = true;
                break;
            }
        }
        if (isFail) {
            System.out.println("Status: Fail");
        } else {
            System.out.println("Status: Pass");
        }
    }

    public static void checkMarks(double marks){
        if(marks < 0 || marks > 100){
            System.out.println("Invalid marks, marks must be between 0 and 100");
            System.exit(0);
        }
    }

    public static void printGrade(double percentage){
        if(percentage >= 90){
            System.out.println("Grade: A");
        }
        else if(percentage >= 80){
            System.out.println("Grade: B");
        }
        else if(percentage >= 70){
            System.out.println("Grade: C");
        }
        else if(percentage >= 50){
            System.out.println("Grade: D");
        }
        else if(percentage >= 35){
            System.out.println("Grade: E");
        }
        else{
            System.out.println("Grade: F");
        }
    }
}
