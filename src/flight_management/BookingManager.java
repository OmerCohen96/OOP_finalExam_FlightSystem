package flight_management;

import flight_management.system_clients.*;
import flight_management.observer_components.FlightObserver;
import flight_management.observer_components.FlightsNewsUpdater;
import flight_management.observer_components.FlightsNewsletterInterface;
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

public class BookingManager implements PassengerServiceFacade, WorkerServiceFacade {

    private static BookingManager bookingManager = null; // the single object will be stored inside this field

    //the "constructor" of the singleton
    public static BookingManager getInstance() {
        if (bookingManager == null)
            bookingManager = new BookingManager();
        return bookingManager;
    }

    final AirLineManager airLineManager;
    final Map<Flight, List<Passenger>> flightsBook;
    final private FlightsNewsletterInterface newsUpdater;

    private BookingManager (){
        this.airLineManager = AirLineManager.getInstance();
        this.flightsBook = new HashMap<>();
        this.newsUpdater = new FlightsNewsUpdater();
    }

    public void addNewFlight(Flight ... flights){
        String compName;
        AirLine airLine;
        for (Flight flight : flights){
            compName = flight.getCompName();
            airLine = getAirLineManager().findAirLineComponent(compName);
            if (airLine != null){
                airLine.addFlight(flight);
                if (!getFlightsBook().containsKey(flight)){
                    getFlightsBook().put(flight, new ArrayList<>());
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

    @Override
    public void subscribeToPushService(FlightObserver observer) {
        getNewsUpdater().subscribe(observer);
    }

    @Override
    public void unsubscribeFromPushService(FlightObserver observer) {
        getNewsUpdater().unsubscribe(observer);
    }

    public List<Flight> searchFlight(SearchEnum... searchEnums){
        List<Flight> results = getAllFlights();
        SearchStrategy searchStrategy;
        for (SearchEnum method : searchEnums){
            searchStrategy = SearchFactory.generate(method);
            results = searchStrategy.search(results);
        }
        return results;
    }

    @Override
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
                    boolean answer = scanner.nextBoolean();
                    if (answer)
                        chosenMethods.add(method);
                    isValid = true;
                }
                catch (IllegalArgumentException | InputMismatchException e)
                {
                    System.out.println("Please follow the instructions \n" +
                            "Write 'true' to include the suggested category above or 'false' to dismiss it.");
                    scanner.nextLine();
                }
            }
        }
        List<Flight> results = this.searchFlight(chosenMethods.toArray(SearchEnum[]::new));
        if (results != null)
            results.forEach(System.out::println);
        else
            System.out.println("We couldn't find any matching flights. We apologize for the inconvenience.");
    }

    @Override
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
        getFlightsBook().get(flight).add(passenger);
    }

    private Map<Flight , List<Passenger>> getFlightsBook(){
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

    public void updateTimeFlight (Flight flight, MyDate newDepartTime, MyDate newArrvlTime){
        String oldFlight = flight.toString();
        String oldDeparture = flight.getDepartureTime().toString();
        String oldArriving = flight.getArrivalTime().toString();
        String messageBuilder = "";
        if (!flight.getDepartureTime().equals(newDepartTime)) {
            flight.setDepartureTime(newDepartTime);
            messageBuilder += String.format("From departure at %s to departure at %s\n", oldDeparture, newDepartTime);
        }
        if (!flight.getArrivalTime().equals(newArrvlTime)) {
            flight.setArrivalTime(newArrvlTime);
            messageBuilder += String.format("From Arrival time at %s to Arrival at %s\n", oldArriving, newArrvlTime);
        }
        String message = String.format("""
                        The time of flight number #%d to %s has been changed.
                        %s
                        """
                            , flight.getFlight_code(),flight.getDestination(), messageBuilder);
        getNewsUpdater().notifyAllObserver(getFlightsBook().get(flight), message);
        getNewsUpdater().notifyAllObserver(message);
    }
    public void updateTimeFlight (Flight flight, MyDate newDepartTime){
        updateTimeFlight(flight, newDepartTime, flight.getArrivalTime());
    }

    public void deleteFlight(Flight flight){
        String canceledFlight = flight.toString();
        String message = String.format(
                """
                        Flight number: #%d
                        with this details:

                        %s\t\tHas been canceled. We apologize for the inconvenience.""",
                flight.getFlight_code(), canceledFlight
        );
        AirLine airLine = getAirLineManager().findAirLineComponent(flight.getCompName());
        getNewsUpdater().notifyAllObserver(getFlightsBook().get(flight), message);
        getNewsUpdater().notifyAllObserver(message);
        airLine.removeFlight(flight);
        getFlightsBook().remove(flight);
    }

    public List<Passenger> getAllPassengers(){
        return getFlightsBook().values().stream().flatMap(Collection::stream).distinct().toList();
    }

    private FlightsNewsletterInterface getNewsUpdater() {
        return newsUpdater;
    }
}
