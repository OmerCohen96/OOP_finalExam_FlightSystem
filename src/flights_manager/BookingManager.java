package flights_manager;

import Person.Passenger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingManager {

    final AirLineManager airLineManager;
    final Map<Flight , List<Passenger>> flightsBook;

    public BookingManager (AirLineManager airLineManager){
        this.airLineManager = airLineManager;
        this.flightsBook = new HashMap<>();
    }


}
