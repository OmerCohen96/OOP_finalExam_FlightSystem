import flight_management.*;
import flight_management.system_clients.Passenger;
import flight_management.system_clients.Worker;
import my_date_format.MyDate;

import java.util.ArrayList;
import java.util.List;



// AirPortSystem represents the primary system for managing airport operations such as airlines,
// flight management, and worker and passenger interactions. This is a singleton class
// that incorporates key functionalities such as airline hierarchy management, flight booking,
// notifications for passengers/workers, and integration of various operational components.

public class AirPortSystem {
    public final static String AIR_PORT_NAME = "BEN-GURION AIR PORT";
    public final static String LOCATION = "Israel";

    private static AirPortSystem airPortSystem = null;

    private final AirLineManager airLineManager = AirLineManager.getInstance();
    private final BookingManager bookingManager = BookingManager.getInstance();

    private final List<Worker> workers;


    // singleton initialization ------------
    public static AirPortSystem getInstance() {
        if (airPortSystem == null)
            airPortSystem = new AirPortSystem();
        return airPortSystem;
    }
    private AirPortSystem(){
        workers = new ArrayList<>();
    }
    // -------------------------------------


    
    // Insert the initial set of airlines into the system //
    // This method demonstrates the use of the Composite design pattern. GroupAirLine objects 
    // represent composite nodes that can contain other GroupAirLine or SingularAirLine objects 
    // as their children, allowing the formation of a hierarchical airline structure.
    public void initialFirstAirLines() {
        // creating new AirLine Objects
        GroupAirLine airFrance = new GroupAirLine("Air France");
        SingularAirLine KLM = new SingularAirLine("KLM");
        SingularAirLine transavia = new SingularAirLine("Transavia");
        SingularAirLine littleFrance = new SingularAirLine("Little France");
        GroupAirLine ELAL = new GroupAirLine("ELAL");
        SingularAirLine israir = new SingularAirLine("Israir");
        SingularAirLine arkia = new SingularAirLine("Arkia");
        GroupAirLine international = new GroupAirLine("International");
        SingularAirLine british = new SingularAirLine("British Airways");
        SingularAirLine europe = new SingularAirLine("Europe Airways");
        GroupAirLine airChina = new GroupAirLine("air China");
        SingularAirLine japanAirways = new SingularAirLine("Japan Airways");
        // making hierarchy of airlines, big airline purchase another air lines
        international.addSubAirLines(british, europe);
        ELAL.addSubAirLines(israir, arkia);
        airFrance.addSubAirLines(KLM, transavia, littleFrance);
        // insert the data into the air line manager
        airLineManager.addAirLineGroup(international, ELAL, airFrance);
        // also insert singular airline and group airline that operate independently
        airLineManager.addAirLineGroup(airChina, japanAirways);
    }

    //Insert the initial set of flights into the system
    public void initialFirstFlights() {
        String location = AirPortSystem.LOCATION;
        // Create example flights
        Flight flight1
                = new Flight("arkia", location, "Australia", "17/5/24 8:00", "17/5/24 12:00", 250.5);
        Flight flight2
                = new Flight("KLM", location, "Brazil", "17/5/24 14:30", "17/5/24 18:45", 550.75);
        Flight flight3
                = new Flight("air China", location, "Canada", "18/5/24 10:15", "19/5/24 6:30", 920.0);
        Flight flight4
                = new Flight("ELAL", location, "Denmark", "19/6/24 23:45", "20/6/24 15:20", 1250.25);
        Flight flight5
                = new Flight("air France", location, "france", "21/5/24 8:00", "21/5/24 12:30", 420.99);
        Flight flight6
                = new Flight("air China", location, "Israel", "22/5/24 16:45", "22/5/24 19:10", 320.5);
        Flight flight7
                = new Flight("Europe Airways", location, "Rome", "23/5/24 12:30", "23/5/24 15:20", 280.75);
        Flight flight8
                = new Flight("israir", location, "Germany", "24/5/24 9:15", "24/5/24 11:30", 180.0);
        Flight flight9
                = new Flight("international", location, "India", "25/5/24 21:00", "26/5/24 2:45", 380.25);
        Flight flight10
                = new Flight("ELAL", "Thailand", location, "26/5/24 13:20", "26/5/24 15:10", 150.99);
        Flight flight11
                = new Flight("japan Airways", location, "South korea", "27/5/24 8:45", "27/5/24 10:30", 200.5);
        Flight flight12
                = new Flight("israir", location, "spain", "28/5/24 14:00", "28/5/24 16:20", 320.75);
        Flight flight13
                = new Flight("ELAL", location, "united state", "29/5/24 19:30", "30/5/24 1:15", 680.0);
        Flight flight14
                = new Flight("air china", location, "Dubai", "30/5/24 6:45", "30/5/24 11:10", 470.25);
        Flight flight15
                = new Flight("transavia", location, "Greece", "31/5/24 10:15", "31/5/24 12:45", 180.99);
        Flight flight16
                = new Flight("ELAL", "Ukraine", location, "1/6/24 14:30", "1/6/24 16:10", 220.5);
        Flight flight17
                = new Flight("Europe Airways", location, "Venezuela", "2/6/24 11:45", "2/6/24 13:00", 90.75);
        Flight flight18
                = new Flight("KLM", location, "Netherlands", "3/6/24 8:00", "3/6/24 10:15", 120.0);
        Flight flight19
                = new Flight("air China", location, "Thailand", "4/6/24 17:20", "4/6/24 19:00", 150.25);
        Flight flight20
                = new Flight("japan Airways", location, "japan", "5/6/24 10:30", "5/6/24 12:00", 100.99);
        Flight flight21
                = new Flight("Europe Airways", location, "Egypt", "6/6/24 13:45", "6/6/24 15:30", 180.5);

        // Insert flights to the booking manager
        bookingManager.addNewFlight(flight1,flight2,flight3,flight4,flight5,flight6,flight7,flight8,flight9
                ,flight10,flight11,flight12,flight13,flight14,flight15,flight16,flight17,flight18,flight19,flight20,flight21);
    }

    
    // Add new workers to the workers list and subscribes them to the airport's notification service.
    public void addWorker (Worker worker){
        workers.add(worker);
        getBookingManager().subscribeToPushService(worker);
    }
    
    // This method returns a list of all workers currently employed at the airport.
    public List<Worker> getWorkers() {
        return workers;
    }
    
    public AirLineManager getAirLineManager() {
        return airLineManager;
    }

    public BookingManager getBookingManager() {
        return bookingManager;
    }

    public static void main(String[] args) {
        // This is the entry point for demonstrating various functionalities of the AirPortSystem,
        // including initializing airlines, managing flights, passengers, and workers.

        // Create a singleton instance of the airport system
        AirPortSystem benGurioun = AirPortSystem.getInstance();

        // Step 1: Initialize the system with predefined airline groups and flights

        benGurioun.initialFirstAirLines(); // Initialize the system with a default hierarchy of airlines
        benGurioun.initialFirstFlights(); // Add a predefined list of flights to the booking manager


// ----------- Demonstrating Airline Management ------------------------------------------//

        // Step 2: Create new airline objects and organize them into a hierarchy

        // Creating small Airlines (leaf nodes) that can be part of a larger airline group hierarchy.
        // Demonstrates the use of the Composite design pattern.
        SingularAirLine wizz = new SingularAirLine("wizz");
        SingularAirLine delta = new SingularAirLine("delta");

        // Creating big Airlines (composite nodes)
        // That can includes subsidiaries airlines
        GroupAirLine easyjet = new GroupAirLine("easy jet");
        GroupAirLine lufthansa = new GroupAirLine("lufthansa");

        // Add subsidiaries (leaf nodes) to the airline groups
        easyjet.addSubAirLines(wizz); // easyJet owns wizz
        lufthansa.addSubAirLines(delta); // Lufthansa owns Delta

        // Create a larger composite airline group with nested relationships
        easyjet.addSubAirLines(lufthansa); // Lufthansa becomes a subsidiary of easyJet

        // Adding 'easyjet,' which now includes several subsidiaries, to the AirLineManager
        benGurioun.getAirLineManager().addAirLineGroup(easyjet); // Add the new hierarchy to the system


// ----------------- Demonstrating Flights Management ------------------------------------------------//

        // Step 3: Add custom flight entries

        // create object that represent date from the MyDate class
        // There is a specific format detailed inside the MyDate class documentation

        MyDate departure = MyDate.of("2/5/24 23:50");
        MyDate arriving = MyDate.of("8.5.24 12:50");

        // Create new departing flights and ensure the `compName` corresponds to an existing airline

        Flight toTheMoon = new Flight("lufthansa", LOCATION, "moon", departure, arriving, 80_000);

        // We can also create a flight by inserting a string instead of creating MyDate object beforehand.
        // Make sure to insert the valid String format.
        Flight toAntarctica = new Flight(
                "easy jet", LOCATION ,"Antarctica", "19/10/24 16:40", "30/12/24 15:00", 1000);

        // Add the flights objects through the BookingManager API
        benGurioun.getBookingManager().addNewFlight(toAntarctica, toTheMoon);


        // -------------------------------- Passengers Interaction ---------------------------------//


        // Step 4: Create passengers and demonstrate their operations
        Passenger alice = new Passenger(150, "alice", "cohen", benGurioun.getBookingManager());
        Passenger bob = new Passenger(200, "bob", "cohen", benGurioun.getBookingManager());


        // Passengers can search for desirable flight, and show the result on the screen
        // call this function represent the user an interactive menu
        // for choosing desire flight in runtime.
        System.out.println("Demonstrate the interactive menu for searching flight \n");

        // Searching flights using the Strategy pattern for matched desired flights
        alice.searchFlight();

        System.out.println("End of the interactive menu for searching flight \n");

        // Bob purchases tickets for flights with specific flightSerialNumber
        bob.purchaseTicket(23);
        bob.purchaseTicket(5);


        System.out.println("List of all passengers assigned to the booking manager");

        // Print the whole passengers the purchased flights and assign to the system
        benGurioun.getBookingManager().getAllPassengers().forEach(System.out::println);

        System.out.println("List of all flights that Bob is assigned to");
        // Show all flights that Bob is assigned to
        bob.getTickets().forEach(System.out::println);


        System.out.println("Change flight time, all passengers assign to that flight will be notified automatically");
        // Demonstrating flight deletion and its impact on registered observers
        // Here i changed departure flight date and all passengers assign to that flight
        // getting notify automatically (in this case just bob get notify)
        MyDate changeTime = MyDate.of("19/10/24 19:00");
        benGurioun.getBookingManager().updateTimeFlight(
                benGurioun.getBookingManager().getFlightByCode(23), changeTime);


        // A passenger can subscribe to the notification service of the airport.
        // The system notifies the subscribers about new changes or canceled flights.
        alice.getPushes();


        // Demonstrating flight deletion and its impact on registered observers
        Flight canceledFlight = benGurioun.getBookingManager().getFlightByCode(5); // Get flight object by code
        benGurioun.getBookingManager().deleteFlight(canceledFlight); // Delete the flight


        // -------------------------------- Workers Management -----------------------------------//

        // Step 5: Create a worker in the system and demonstrate their operations

        Worker tomas = new Worker(50, "tomas", "gitman", benGurioun.getBookingManager());

        // Add the worker to the worker list and automatically subscribe to notifications
        benGurioun.addWorker(tomas);

        // Workers can perform operations through the WorkerServiceFacade
        // Example: Tomas updates the flight timings
        tomas.getWorkerService().updateTimeFlight(
                tomas.getWorkerService().getFlightByCode(1), // getting desire flight
                MyDate.of("18/9/24 16:48"), MyDate.of("19/9/24 22:50") // Assign new depart and arrival time
        );


        // -------------------------------- Testing Zone ------------------------------------------//
        // This section is meant for additional testing or debugging new features.

    }

}
