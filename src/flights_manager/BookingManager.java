package flights_manager;

import flights_manager.airlines_component.AirLine;
import flights_manager.client_handle.FlightObserver;
import flights_manager.client_handle.FlightsNewsletter;
import flights_manager.client_handle.Passenger;
import flights_manager.client_handle.Ticket;
import flights_manager.search_strategies.SearchFactory;
import flights_manager.search_strategies.SearchMethod;
import flights_manager.search_strategies.SearchStrategy;
import flights_manager.client_handle.PassengerServiceFacade;
import java.util.*;

// TODO: 24/04/2024 make it static class
public class BookingManager implements FlightsNewsletter, PassengerServiceFacade {

    final AirLineManager airLineManager;
    final Map<Flight , List<Passenger>> flightsBook;
    final private List<FlightObserver> observers;

    public BookingManager (AirLineManager airLineManager){
        this.airLineManager = airLineManager;
        this.flightsBook = new HashMap<>();
        this.observers = new ArrayList<>();
    }

//    public void addNewFlight(Flight ... flights){
//        getAirLineManager().addNewFlight(flights);
//        for (Flight flight : flights){
//            getMap().put(flight, new ArrayList<>());
//        }
//    }

    public void addNewFlight(Flight ... flights){
        String compName;
        AirLine airLine;
        for (Flight flight : flights){
            compName = flight.getCompName();
            airLine = getAirLineManager().getAirLineComponent(compName);
            if (airLine != null){
                airLine.addFlight(flight);
                if (!getMap().containsKey(flight)){
                    getMap().put(flight, new ArrayList<>());
                    // TODO: 24/04/2024 add notify fo observers
                }
            } else {
                throw new NoSuchElementException(
                        String.format("""
                                        There is no such company %s 
                                        for insert a new flight, the Company name field must-
                                        related with name from the airlines pool""",
                                compName));
            }
        }
    }

    public Ticket purchaseTicket (int serialNumber, Passenger passenger){
        Flight flight = getFlightByCode(serialNumber);
        if (flight != null) {
            addNewPassenger(flight, passenger);
            return new Ticket(flight);
        }
        else
            throw new NoSuchElementException("We couldn't find a flight with this serial number");
    }

    public void searchFlight (){
        Scanner scanner = new Scanner(System.in);
        SearchMethod[] methods = SearchMethod.values();
        List<SearchMethod> chosenMethods = new ArrayList<>();
        System.out.println("Please indicate the categories you'd like to search by.\n" +
                "Enter 'true' to include a category or 'false' to dismiss it");
        boolean isValid;
        for (SearchMethod method : methods){
            System.out.printf("Search %s ?%n", method);
            isValid = false;
            while (!isValid) {
                try
                {
                    if (scanner.nextBoolean())
                        chosenMethods.add(method);
                    isValid = true;
                }
                catch (NoSuchElementException o)
                {
                    System.out.println("Please follow the instructions \n" +
                            "Enter 'true' to include a category or 'false' to dismiss it.");
                }
            }
        }
        List<Flight> results = this.search(chosenMethods.toArray(SearchMethod[]::new));
        if (results != null)
            results.forEach(System.out::println);
        else
            System.out.println("We couldn't find any matching flights. We apologize for the inconvenience.");
    }

    public List<Flight> search(SearchMethod... searchMethods){
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

    // TODO: 24/04/2024
    public void updateFlightTime (Flight flight){


        getMap().get(flight).forEach(passenger -> passenger.updateFlightStatus("sas"));
    }

    // TODO: 24/04/2024
    public void canceledFlight (Flight flight){

    }


    @Override
    public void subscribe(FlightObserver observer) {

    }

    @Override
    public void unsubscribe(FlightObserver observer) {

    }

    @Override
    public void notifyAllObserver(String message) {

    }

    @Override
    public void notifyAllPassengers(Flight flight, String message) {

    }
}
