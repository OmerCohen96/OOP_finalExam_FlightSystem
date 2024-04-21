package flights_manager;

import Person.Passenger;

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

    private List<Flight> getAllFlights (){
        return getAirLineManager().getAirLineGroups().stream()
                .flatMap(airLine -> airLine.getAllFlights().stream())
                .toList();
    }

    private AirLineManager getAirLineManager(){
        return this.airLineManager;
    }


}
