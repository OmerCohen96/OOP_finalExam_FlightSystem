package flight_management;

import flight_management.clients_components.*;
import flight_management.search_strategy_componnets.SearchEnum;
import flight_management.search_strategy_componnets.SearchFactory;
import flight_management.search_strategy_componnets.SearchStrategy;
import my_date_format.MyDate;

import java.util.*;

/**
 * this class responsible for managing flights, flight bookings
 * and notifications for passengers and flight observers.
 * <p>
 * this class utilizes the Singleton design pattern, meaning that it can be instantiated only once.
 * since this project is intended for use in a single airport,
 * I believe it's appropriate to ensure there is only one instance of it.
 * <p>
 * also, this class implements the FlightsNewsletter interface,
 * allowing flight observers to subscribe and unsubscribe for updates.
 * <p>
 * if fact,
 * This class acts as an observable component in the construction of the Observer design pattern.
 * </p>
 *
 **/

public class BookingManager implements FlightsNewsletter, PassengerServiceFacade {

    private static BookingManager bookingManager = null; // the single object will be stored inside this field

    //the "constructor" of the singleton
    public static BookingManager getInstance() {
        if (bookingManager == null)
            bookingManager = new BookingManager();
        return bookingManager;
    }

    final AirLineManager airLineManager;
    final Map<Flight, List<Passenger>> flightsBook;
    final private Set<FlightObserver> flightObservers;

    private BookingManager (){
        this.airLineManager = AirLineManager.getInstance();
        this.flightsBook = new HashMap<>();
        this.flightObservers = new HashSet<>();
    }

    /**
     * recieve new flights
     */
    public void addNewFlight(Flight ... flights){
        String compName;
        AirLine airLine;
        for (Flight flight : flights){
            compName = flight.getCompName();
            airLine = getAirLineManager().findAirLineComponent(compName);
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

    public void searchFlight (){
        Scanner scanner = new Scanner(System.in);
        SearchEnum[] methods = SearchEnum.values();
        List<SearchEnum> chosenMethods = new ArrayList<>();
        System.out.println("Please indicate the categories you'd like to search by.\n" +
                "Enter 'true' to include a category or 'false' to dismiss it");
        boolean isValid;
        for (SearchEnum method : methods){
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
        List<Flight> results = this.searchFlight(chosenMethods.toArray(SearchEnum[]::new));
        if (results != null)
            results.forEach(System.out::println);
        else
            System.out.println("We couldn't find any matching flights. We apologize for the inconvenience.");
    }

    private List<Flight> searchFlight(SearchEnum... searchEnums){
        List<Flight> results = getAllFlights();
        SearchStrategy searchStrategy;
        for (SearchEnum method : searchEnums){
            searchStrategy = SearchFactory.generate(method);
            results = searchStrategy.search(results);
        }
        return results;
    }

    public Ticket purchaseTicket (int flightSerialNumber, Passenger passenger){
        Flight flight = getFlightByCode(flightSerialNumber);
        if (flight != null) {
            addPassenger(flight, passenger);
            return new Ticket(flight);
        }
        else
            throw new NoSuchElementException("We couldn't find a flight with this serial number");
    }

    public Flight getFlightByCode (int serialNumber){
        return getAllFlights().stream()
                .filter(flight -> flight.getFlight_code() == serialNumber)
                .findFirst().orElse(null);
    }
    public void addPassenger(Flight flight , Passenger passenger){
        getMap().get(flight).add(passenger);
    }

    private Map<Flight , List<Passenger>> getMap (){
        return this.flightsBook;
    }

    public List<Flight> getAllFlights (){
        return getAirLineManager().getAirLineGroups().stream()
                .flatMap(airLine -> airLine.getAllFlights().stream())
                .toList();
    }

    private AirLineManager getAirLineManager(){
        return this.airLineManager;
    }

    // TODO: 24/04/2024 more detailed
    public void updateTimeFlight (Flight flight, MyDate newDepartTime, MyDate newArrvlTime){
        String oldFlight = flight.toString();
        if (!flight.getDepartureTime().equals(newDepartTime))
            flight.setDepartureTime(newDepartTime);
        if (!flight.getArrivalTime().equals(newArrvlTime))
            flight.setArrivalTime(newArrvlTime);
        String message = String.format( // TODO: 24/04/2024  expaned the message
                "the time of flight number #%d has been changed", flight.getFlight_code());
        notifyAllPassengers(flight, message);
        notifyAllObserver(message);

    }

    public void updateTimeFlight (Flight flight, MyDate newDepartTime){
        updateTimeFlight(flight, newDepartTime, flight.getArrivalTime());
    }

    // TODO: 24/04/2024
    public void cancelFlight (Flight flight){
        String canceledFlight = flight.toString();
        String message = String.format(
                "Flight details:\n%s\thas been canceled, we sorry", canceledFlight
        );
        AirLine airLine = getAirLineManager().findAirLineComponent(flight.getCompName());
        notifyAllPassengers(flight,message);
        notifyAllObserver(message);
        airLine.removeFlight(flight);
        getMap().remove(flight);
    }

    private Set<FlightObserver> getFlightObservers(){
        return this.flightObservers;
    }

    @Override
    public void subscribe(FlightObserver observer) {
        getFlightObservers().add(observer);
    }

    @Override
    public void unsubscribe(FlightObserver observer) {
        getFlightObservers().remove(observer);
    }

    @Override
    public void notifyAllObserver(String message) {
        for (FlightObserver observer : getFlightObservers())
            observer.update(message);
    }

    @Override
    public void notifyAllPassengers(Flight flight, String message) {
        for (Passenger passenger : getMap().get(flight))
            passenger.update(message);
    }
}
