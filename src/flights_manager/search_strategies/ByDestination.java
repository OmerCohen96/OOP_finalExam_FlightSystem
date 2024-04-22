package flights_manager.search_strategies;

import flights_manager.Flight;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ByDestination implements SearchStrategy {

    Scanner scanner = new Scanner(System.in);

    @Override
    public List<Flight> search(List<Flight> source) {
        String destination = "";
        boolean isValid = false;
        while (!isValid){
            try {
                System.out.println("Please choose your destination: ");
                destination = scanner.nextLine().strip();
                char[] check = destination.toCharArray();
                if (check.length > 0) {
                    isValid = true;
                    for (Character c : check) {
                        if (!Character.isLetter(c)) {
                            System.out.println("Invalid destination, Try again");
                            isValid = false;
                            break;
                        }
                    }
                }

            } catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid String value.");
                scanner.nextLine();
            }
        }
        String finalDestination = destination;
        return source.stream().filter(flight -> flight.getDestination().equalsIgnoreCase(finalDestination)).toList();
    }

}
