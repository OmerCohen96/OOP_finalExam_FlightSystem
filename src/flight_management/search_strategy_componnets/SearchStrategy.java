package flight_management.search_strategy_componnets;

import flight_management.Flight;

import java.util.List;

public interface SearchStrategy {
    List<Flight> search(List<Flight> source);
}
