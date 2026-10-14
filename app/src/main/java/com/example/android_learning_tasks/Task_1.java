package com.example.android_learning_tasks;

import java.util.Scanner;

public class Task_1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a price: ");
        double price = input.nextDouble();
        if (price < 0) {
            System.out.println("Invalid price, price cannot be negative");
            return;
        }

        System.out.print("Enter a quantity: ");
        int quantity = input.nextInt();
        if (quantity < 0) {
            System.out.println("Invalid quantity, quantity cannot be negative");
            return;
        }

        System.out.print("Enter a discount(%): ");
        int discount = input.nextInt();
        if (discount < 0 || discount > 100) {
            System.out.println("Invalid discount, discount must be between 0 and 100");
            return;
        }

        double subtotal = price * quantity;
        double discountAmount = (subtotal * discount) / 100;
        double finalPayable = subtotal - discountAmount;

        System.out.println("Sub Total: " + subtotal);
        System.out.println("Discount: " + discountAmount);
        System.out.println("Final Payable: " + finalPayable);
    }
}
