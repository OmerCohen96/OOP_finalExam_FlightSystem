package flights_manager.search_strategies;

import flights_manager.airlines_component.Flight;

import java.util.List;

public interface SearchStrategy {
    List<Flight> search(List<Flight> source);
}
