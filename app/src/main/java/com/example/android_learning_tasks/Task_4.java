package com.example.android_learning_tasks;

import java.util.Scanner;

public class Task_4 {
    private static final double StatingBalance = 5000;
    private static double balance = StatingBalance;
    static Scanner input = new Scanner(System.in);
    static int option;

    public static void main(String[] args){

        do {
            startTransaction();
        } while (option != 4);
    }

    private static void startTransaction() {
        System.out.println("*** Choose Option to Start Transaction ***");
        System.out.println("1. Deposit Amount");
        System.out.println("2. Withdraw Amount");
        System.out.println("3. Check Balance");
        System.out.println("4. Exit");
        System.out.print("Enter Option Number: ");
        option = input.nextInt();
        switch (option){
            case 1:
                depositAmount();
                break;
            case 2:
                withdrawAmount();
                break;
            case 3:
                checkBalance();
                break;
            case 4:
                System.out.print("Thank you!!");
                System.exit(0);
            default:
                System.out.println("Invalid option, try again!!");
        }
    }

    private static void checkBalance() {
        System.out.println("Available Balance: " + balance);
    }

    private static void withdrawAmount() {
        System.out.print("Enter Amount to Withdraw: ");
        double withdraw = input.nextDouble();
        if(withdraw > balance){
            System.out.println("Insufficient Balance");
            return;
        }else if(withdraw < 0){
            System.out.println("Invalid Amount, withdraw amount cannot be negative");
            return;
        } else if (withdraw == 0) {
            System.out.println("Invalid Amount");
            return;
        }
        balance -= withdraw;
        System.out.println("Rs. " + withdraw + " withdrawn successfully");
        checkBalance();
    }

    private static void depositAmount() {
        System.out.print("Enter Amount to Deposit: ");
        double deposit = input.nextDouble();
        if(deposit < 0){
            System.out.println("Invalid Amount, deposit amount cannot be negative");
            return;
        } else if (deposit == 0) {
            System.out.println("Invalid Amount");
            return;
        }
        balance += deposit;
        System.out.println("Rs. " + deposit + " deposited successfully");
        checkBalance();
    }
}
