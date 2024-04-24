import flights_manager.Flight;
import flights_manager.search_strategies.SearchFactory;
import flights_manager.search_strategies.SearchMethod;
import flights_manager.search_strategies.SearchStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum enums{
    ONE, TWO, THREE
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.nextBoolean()){
            System.out.println("again");
        }
    }

    public static void da (enums ... nums){
        for (enums num : nums)
            System.out.println(num);
    }
}