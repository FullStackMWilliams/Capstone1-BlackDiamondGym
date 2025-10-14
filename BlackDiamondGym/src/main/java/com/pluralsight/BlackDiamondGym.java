package com.pluralsight;

                                            /* BLACK DIAMOND GYM */


import java.util.List;
import java.util.Scanner;

import static com.pluralsight.Colors.*;

public class BlackDiamondGym {

    private static final Scanner in = new Scanner(System.in);






    public static void main(String[] args) {
        displayHomeScreen();

    }

    public static void displayHomeScreen() {
        // clearScreen();

        System.out.println(Colors.BeigeBackground + "================================="+ RESET);
        System.out.println(DeepBlue + " Welcome to BlackDiamond Gym" + RESET);
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