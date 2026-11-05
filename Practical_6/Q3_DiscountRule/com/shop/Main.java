package com.shop;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, DiscountRule> rules = Rules.all();
        List<Double> prices = List.of(500.0, 1200.0, 2500.0, 80.0);

        System.out.println("Discount rules:");
        System.out.println("  1. 10% off");
        System.out.println("  2. Flat Rs 100 off");
        System.out.println("  3. 20% off on prices above Rs 1000");
        System.out.print("Choose a rule: ");

        Scanner sc = new Scanner(System.in);
        DiscountRule rule = rules.get(sc.nextLine().trim());
        if (rule == null) {
            System.out.println("Invalid rule.");
            return;
        }

        for (double p : prices) {
            double finalPrice = Math.max(0, rule.apply(p));
            System.out.printf("Price %8.2f -> after discount %8.2f%n", p, finalPrice);
        }
    }
}
