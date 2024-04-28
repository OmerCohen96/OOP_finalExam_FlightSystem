package flights_manager.search_strategies;

import flights_manager.airlines_component.Flight;
import my_date_format.MyDate;

import java.util.List;
import java.util.Scanner;

public class ByDate implements SearchStrategy{
    Scanner scanner = new Scanner(System.in);

    @Override
    public List<Flight> search(List<Flight> source) {
        System.out.println("please enter require date for departure: ");
        String dateStr = scanner.next();
        while (!MyDate.isValid(dateStr)) {
            System.out.printf("""
                                invalid input!
                                please enter valid input that represent date
                                in this format: %s
                                %n""", MyDate.FORMAT);
            dateStr = scanner.next();
        }
        MyDate requestDate = MyDate.of(dateStr);
        return source.stream().filter(x -> x.getDepartureTime().equalsByDate(requestDate)).toList();
    }
}
