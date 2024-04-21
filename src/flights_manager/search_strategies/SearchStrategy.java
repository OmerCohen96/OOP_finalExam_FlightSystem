package flights_manager.search_strategies;

import flights_manager.Flight;

import java.util.List;

public interface SearchStrategy {
    List<Flight> search(List<Flight> source);
}
