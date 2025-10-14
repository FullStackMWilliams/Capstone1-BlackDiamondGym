package com.pluralsight;

/* BLACK DIAMOND GYM */


public class BlackDiamondGym {

                    // Color theme for CLI application

    public static final String BeigeBackground = "\u001B[100m";
    public static final String Purple = "\u001B[35m";
    public static final String Olive = "\u001B[32m";
    public static final String Amber = "\u001B[33m";
    public static final String Seafoam = "\u001B[36m";
    public static final String DeepBlue = "\u001B[34m";
    public static final String Aqua = "\u001B[96m";
    public static final String OceanBlue = "\u001B[94m";
    public static final String Gray = "\u001B[90m";
    public static final String RESET = "\u001B[0m";


    public static void main(String[] args) {
        displayHomeScreen();

    }

    public static void displayHomeScreen() {
        // clearScreen();

        System.out.println(BeigeBackground + "================================="+ RESET);
        System.out.println(Purple + " Welcome to BlackDiamond Gym" + RESET);
        System.out.println(BeigeBackground + "=================================" + RESET);
        System.out.println(Seafoam + "\nYour journey to strength and balance begins here." + RESET);
        System.out.println(Gray + "\n-----------------------------------------------------" + RESET);
        System.out.println(Olive + "[1]" + RESET + " Login");
        System.out.println(Olive + "[2]" + RESET + " Sign Up");
        System.out.println(Olive + "[3]" + RESET + " Exit");
        System.out.println(Gray + "-------------------------------------------------------");
        System.out.println(Amber + "\"Life is easier when you're stronger\".- Markus" + RESET);
    }
}