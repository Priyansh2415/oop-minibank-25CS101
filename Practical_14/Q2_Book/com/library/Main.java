package com.library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        LibraryService lib = new LibraryService();
        Scanner sc = new Scanner(System.in);
        final String FILE = "library.dat";

        while (true) {
            System.out.print("\n1.Add  2.List  3.Save  4.Load  5.Exit > ");
            switch (sc.nextLine().trim()) {
                case "1" -> {
                    System.out.print("Title : ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    lib.add(new Book(title, sc.nextLine()));
                }
                case "2" -> {
                    System.out.println("Books (" + lib.count() + "):");
                    lib.all().forEach(b -> System.out.println("  " + b.title() + " by " + b.author()));
                }
                case "3" -> { lib.save(FILE); System.out.println("Saved " + lib.count() + " book(s) to " + FILE); }
                case "4" -> { lib.load(FILE); System.out.println("Loaded " + lib.count() + " book(s) from " + FILE); }
                case "5" -> { System.out.println("Goodbye!"); return; }
                default  -> System.out.println("Invalid option");
            }
        }
    }
}
