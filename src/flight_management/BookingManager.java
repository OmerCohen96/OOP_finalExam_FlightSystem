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

    private static BookingManager bookingManager = null; // Singleton instance of BookingManager

    /**
     * Retrieves the singleton instance of BookingManager.
     * @return The singleton instance of BookingManager.
     */
    public static BookingManager getInstance() {
        if (bookingManager == null)
            bookingManager = new BookingManager();
        return bookingManager;
    }


    final AirLineManager airLineManager; // Manages airline operations
    final Map<Flight, List<Passenger>> flightsBook; // Stores booked flights and associated passengers
    final private FlightsNewsletterInterface newsUpdater; //component that handle of updates for subscribers about flight news

    /**
     * Private constructor to enforce singleton pattern.
     */
    private BookingManager (){
        this.airLineManager = AirLineManager.getInstance();
        this.flightsBook = new HashMap<>();
        this.newsUpdater = new FlightsNewsUpdater();
    }

    //---------------------- handle with flights-----------------------------------------
    public void addNewFlight(Flight ... flights){
        String compName;
        AirLine airLine;
        for (Flight flight : flights){
            compName = flight.getCompName();
            airLine = airLineManager.findAirLineComponent(compName); // Find the airline component based on flight's company name
            if (airLine != null){
                airLine.addFlight(flight); // Add flight to the airline
                if (!getFlightsBook().containsKey(flight)){
                    getFlightsBook().put(flight, new ArrayList<>()); // Initialize passenger list for the new flight
                    String message = String.format("""
                            "New flight arrival!"
                            
                            %s
                            """, flight);
                    getNewsUpdater().notifyAllObserver(message); // Notify all subscribers about the new flight
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

    /**
     * Searches for flights based on user input.
     */
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
                        chosenMethods.add(method); // Add the chosen search enum that describe desire algorithm
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
        List<Flight> results = this.searchFlight(chosenMethods.toArray(SearchEnum[]::new)); // Perform search based on chosen methods
        if (!results.isEmpty()) {
            System.out.println("MATCHING RESULTS: \n");
            results.forEach(System.out::println); // Print search results
            System.out.println("END RESULTS\n");
        }else {
            System.out.println("We couldn't find any matching flights. We apologize for the inconvenience.\n");
        }
    }

    /**
     * Searches for flights based on specified search criteria.
     * @param searchEnums The search criteria.
     * @return A list of flights matching the search criteria.
     */
    public List<Flight> searchFlight(SearchEnum... searchEnums){
        List<Flight> results = getAllFlights();
        SearchStrategy searchStrategy;
        for (SearchEnum method : searchEnums){
            searchStrategy = SearchFactory.generate(method);// Generate search strategy based on chosen method
            results = searchStrategy.search(results);
        }
        return results;
    }

    /**
     * Retrieves a flight by its serial number.
     * @param serialNumber The serial number of the flight.
     * @return The flight with the specified serial number, or null if not found.
     */
    public Flight getFlightByCode (int serialNumber){
        return getAllFlights().stream()
                .filter(flight -> flight.getFlight_code() == serialNumber)
                .findFirst().orElse(null);
    }

    /**
     * Retrieves all flights.
     * @return A list of all flights.
     */
    public List<Flight> getAllFlights (){
        return airLineManager.getAirLineGroups().stream()
                .flatMap(airLine -> airLine.getAllFlights().stream())
                .toList();
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
        AirLine airLine = airLineManager.findAirLineComponent(flight.getCompName());
        getNewsUpdater().notifyAllObserver(getFlightsBook().get(flight), message);// Notify all passengers about flight cancellation
        getNewsUpdater().notifyAllObserver(message);// Notify all subscribers about flight cancellation
        airLine.removeFlight(flight);// Remove flight from the airline
        getFlightsBook().remove(flight);// Remove flight booking details
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
        getNewsUpdater().notifyAllObserver(getFlightsBook().get(flight), message); // Notify all passengers about flight cancellation
        getNewsUpdater().notifyAllObserver(message); // Notify all subscribers about flight cancellation
    }

    public void updateTimeFlight (Flight flight, MyDate newDepartTime){
        updateTimeFlight(flight, newDepartTime, flight.getArrivalTime());
    }

    private Map<Flight , List<Passenger>> getFlightsBook(){
        return this.flightsBook;
    }
//-----------------------------------------------------------------------------------------------------------------

    // -------------------------------------- API of operation to passenger--------------------------------------
    /**
     * Purchases a ticket for a passenger for the specified flight.
     * Then, he is registered in the flight booking map
     * @param flightSerialNumber The serial number of the flight.
     * @param passenger The passenger purchasing the ticket.
     * @return The ticket purchased.
     * @throws NoSuchElementException If the specified flight is not found.
     */
    public Ticket purchaseTicket (int flightSerialNumber, Passenger passenger){
        Flight flight = getFlightByCode(flightSerialNumber);
        if (flight != null) {
            addPassenger(flight, passenger); // The passenger is then registered in the flight booking map, associated with his flight.
            return new Ticket(flight);
        }
        else
            throw new NoSuchElementException("We couldn't find a flight with this serial number");
    }

    private void addPassenger(Flight flight , Passenger passenger){
        getFlightsBook().get(flight).add(passenger);
    }
    @Override
    public void subscribeToPushService(FlightObserver observer) {
        getNewsUpdater().subscribe(observer);
    }
    @Override
    public void unsubscribeFromPushService(FlightObserver observer) {
        getNewsUpdater().unsubscribe(observer);
    }

//-----------------------------------------------------------------------------------------------------------------

    // Get all passengers booked on flights
    public List<Passenger> getAllPassengers(){
        return getFlightsBook().values().stream().flatMap(Collection::stream).distinct().toList();
    }

    private FlightsNewsletterInterface getNewsUpdater() {
        return newsUpdater;
    }
}
