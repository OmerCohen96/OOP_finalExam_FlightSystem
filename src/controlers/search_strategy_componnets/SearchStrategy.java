package controlers.search_strategy_componnets;

import controlers.Flight;

import java.util.List;

public interface SearchStrategy {
    List<Flight> search(List<Flight> source);
}
