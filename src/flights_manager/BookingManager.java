package flights_manager;

import Person.Passenger;
import flights_manager.search_strategies.SearchFactory;
import flights_manager.search_strategies.SearchMethod;
import flights_manager.search_strategies.SearchStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BookingManager {

    final AirLineManager airLineManager;
    final Map<Flight , List<Passenger>> flightsBook;

    public BookingManager (AirLineManager airLineManager){
        this.airLineManager = airLineManager;
        this.flightsBook = new HashMap<>();
    }

    public List<Flight> searchFlight (SearchMethod... searchMethods){
        List<Flight> results = getAllFlights();
        SearchStrategy searchStrategy;
        for (SearchMethod method : searchMethods){
            searchStrategy = SearchFactory.generate(method);
            results = searchStrategy.search(results);
        }
        return results;
    }

    public Flight getFlightByCode (int serialNumber){
        return getAllFlights().stream()
                .filter(flight -> flight.getFlight_code() == serialNumber)
                .findFirst().orElse(null);
    }

    public void addNewPassenger (Flight flight , Passenger passenger){
        getMap().get(flight).add(passenger);
    }
    public void addNewFlight(Flight ... flights){
        getAirLineManager().addNewFlight(flights);
        for (Flight flight : flights){
            getMap().put(flight, new ArrayList<>());
        }
    }

    private Map<Flight , List<Passenger>> getMap (){
        return this.flightsBook;
    }

    private List<Flight> getAllFlights (){
        return getAirLineManager().getAirLineGroups().stream()
                .flatMap(airLine -> airLine.getAllFlights().stream())
                .toList();
    }

    private AirLineManager getAirLineManager(){
        return this.airLineManager;
    }


}
