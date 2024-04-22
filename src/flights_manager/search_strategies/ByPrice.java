package flights_manager.search_strategies;

import flights_manager.Flight;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ByPrice implements SearchStrategy {

    Scanner scanner = new Scanner(System.in);

    @Override
    public List<Flight> search(List<Flight> source) {
        double maxPrice = 0;
        boolean isValid = false;
        while (!isValid){
            try {
                System.out.println("Please choose maximum price: ");
                maxPrice = scanner.nextDouble();
                isValid = true;
            } catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid double value.");
                scanner.nextLine();
            }
        }
        final double price = maxPrice;
        return source.stream().filter(flight -> flight.getPrice() <= price).toList();
    }

}
