package com.example.android_learning_tasks;

import java.util.Scanner;

public class Task_3 {
    static int option;
    static Scanner input;
    public static void main(String[] args){
        do {
            menuInitialize();
        } while (option != 5);

    }

    private static void menuInitialize() {
        input = new Scanner(System.in);
        System.out.println("Choose option from below to start: ");
        System.out.println("1. Even/Odd");
        System.out.println("2. Positive/Negative");
        System.out.println("3. Multiplication Table");
        System.out.println("4. Factorial");
        System.out.println("5. Exit");
        System.out.print("Enter Option Number: ");
        option = input.nextInt();

        switch (option){
            case 1: evenOdd();
                break;
            case 2: positiveNegative();
                break;
            case 3: multiplicationTable();
                break;
            case 4: factorial();
                break;
            case 5:
                System.out.print("Thank you!!");
                System.exit(0);
            default: System.out.println("Invalid option, try again!!");
        }
    }

    private static void factorial() {
        System.out.println("**Factorial**");
        System.out.print("Enter a Number: ");
        int num = input.nextInt();
        int fact = 1;
        for(int i = 1; i <= num; i++){
            fact *= i;
        }
        System.out.println("Factorial of " + num + " is " + fact);
    }

    private static void multiplicationTable() {
        System.out.println("**Multiplication Table**");
        System.out.print("Enter a Number: ");
        int num = input.nextInt();
        for(int i=1; i<=10; i++){
            System.out.println(num + " x " + i + " = " + (num * i));
        }
    }

    private static void positiveNegative() {
        System.out.println("**Positive/Negative Check**");
        System.out.print("Enter a Number: ");
        int num = input.nextInt();
        if(num < 0){
            System.out.println("Number is Negative");
        }
        else{
            System.out.println("Number is Positive");
        }
    }

    private static void evenOdd() {
        System.out.println("**Even/Odd Check**");
        System.out.print("Enter a Number: ");
        int num = input.nextInt();
        if(num % 2 == 0){
            System.out.println("Number is Even");
        }
        else{
            System.out.println("Number is Odd");
        }
    }
}
