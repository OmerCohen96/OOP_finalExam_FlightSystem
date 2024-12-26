import flight_management.*;
import flight_management.system_clients.Passenger;
import flight_management.system_clients.Worker;
import my_date_format.MyDate;

import java.util.ArrayList;
import java.util.List;

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


    // Insert the initial set of airlines into the system
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
        String origin = AirPortSystem.LOCATION;
        // making some flights
        Flight flight1
                = new Flight("arkia", origin, "Australia", "17/5/24 8:00", "17/5/24 12:00", 250.5);
        Flight flight2
                = new Flight("KLM", origin, "Brazil", "17/5/24 14:30", "17/5/24 18:45", 550.75);
        Flight flight3
                = new Flight("air China", origin, "Canada", "18/5/24 10:15", "19/5/24 6:30", 920.0);
        Flight flight4
                = new Flight("ELAL", origin, "Denmark", "19/6/24 23:45", "20/6/24 15:20", 1250.25);
        Flight flight5
                = new Flight("air France", origin, "france", "21/5/24 8:00", "21/5/24 12:30", 420.99);
        Flight flight6
                = new Flight("air China", origin, "Israel", "22/5/24 16:45", "22/5/24 19:10", 320.5);
        Flight flight7
                = new Flight("Europe Airways", origin, "Rome", "23/5/24 12:30", "23/5/24 15:20", 280.75);
        Flight flight8
                = new Flight("israir", origin, "Germany", "24/5/24 9:15", "24/5/24 11:30", 180.0);
        Flight flight9
                = new Flight("international", origin, "India", "25/5/24 21:00", "26/5/24 2:45", 380.25);
        Flight flight10
                = new Flight("ELAL", "Thailand", origin, "26/5/24 13:20", "26/5/24 15:10", 150.99);
        Flight flight11
                = new Flight("japan Airways", origin, "South korea", "27/5/24 8:45", "27/5/24 10:30", 200.5);
        Flight flight12
                = new Flight("israir", origin, "spain", "28/5/24 14:00", "28/5/24 16:20", 320.75);
        Flight flight13
                = new Flight("ELAL", origin, "united state", "29/5/24 19:30", "30/5/24 1:15", 680.0);
        Flight flight14
                = new Flight("air china", origin, "Dubai", "30/5/24 6:45", "30/5/24 11:10", 470.25);
        Flight flight15
                = new Flight("transavia", origin, "Greece", "31/5/24 10:15", "31/5/24 12:45", 180.99);
        Flight flight16
                = new Flight("ELAL", "Ukraine", origin, "1/6/24 14:30", "1/6/24 16:10", 220.5);
        Flight flight17
                = new Flight("Europe Airways", origin, "Venezuela", "2/6/24 11:45", "2/6/24 13:00", 90.75);
        Flight flight18
                = new Flight("KLM", origin, "Netherlands", "3/6/24 8:00", "3/6/24 10:15", 120.0);
        Flight flight19
                = new Flight("air China", origin, "Thailand", "4/6/24 17:20", "4/6/24 19:00", 150.25);
        Flight flight20
                = new Flight("japan Airways", origin, "japan", "5/6/24 10:30", "5/6/24 12:00", 100.99);
        Flight flight21
                = new Flight("Europe Airways", origin, "Egypt", "6/6/24 13:45", "6/6/24 15:30", 180.5);

        // insert to the booking manager
        bookingManager.addNewFlight(flight1,flight2,flight3,flight4,flight5,flight6,flight7,flight8,flight9
                ,flight10,flight11,flight12,flight13,flight14,flight15,flight16,flight17,flight18,flight19,flight20,flight21);
    }

    public void addWorker (Worker worker){
        workers.add(worker);
        getBookingManager().subscribeToPushService(worker);
    }

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
        // initial the system
        AirPortSystem benGurioun = AirPortSystem.getInstance();
        benGurioun.initialFirstAirLines(); // to expand the database and enhance its diversity
        benGurioun.initialFirstFlights(); // same as above


// ----------- creating airline object --------------------------------------------------------------------------------
        // create new small airline object
        SingularAirLine wizz = new SingularAirLine("wizz");
        SingularAirLine delta = new SingularAirLine("delta");
        // create an airline that can purchase other airlines
        GroupAirLine easyjet = new GroupAirLine("easy jet");
        GroupAirLine lufthansa = new GroupAirLine("lufthansa");
        // purchase airline
        easyjet.addSubAirLines(wizz);
        lufthansa.addSubAirLines(delta);
        // group air line can purchase another air line
        easyjet.addSubAirLines(lufthansa);

        // adding air line to the system
        benGurioun.getAirLineManager().addAirLineGroup(easyjet);

// ----------------- creating and adding flights -------------------------------------------------------------------------
        // create object that represent date from the static method of MyDate class
        // There is a specific format detailed in the MyDate class documentation

        MyDate departure = MyDate.of("2/5/24 23:50");
        MyDate arriving = MyDate.of("8.5.24 12:50");

        //Create new departing flights, ensuring to insert compName of an airline that existing inside the system

        Flight toTheMoon = new Flight("lufthansa", LOCATION, "moon", departure, arriving, 80_000);

        // We can also create a flight by inserting a string instead of creating a MyDate object beforehand.
        // Make sure to insert the valid format.
        Flight toAntarctica = new Flight(
                "easy jet", LOCATION ,"Antarctica", "19/10/24 16:40", "30/12/24 15:00", 1000);

        // we can add flights throw the API of the booking manager
        // the booking manager can handle all the flights and their passengers.
        benGurioun.getBookingManager().addNewFlight(toAntarctica, toTheMoon);

//-----------------------------------creating passengers object and demonstrating their operations ---------------------
        //create new passengers
        Passenger alice = new Passenger(150, "alice", "cohen", benGurioun.getBookingManager());
        Passenger bob = new Passenger(200, "bob", "cohen", benGurioun.getBookingManager());

        //passengers can search for desirable flight, and show the result on the screen
        alice.searchFlight();

        //Purchase a flight. The system can identify which flight the passenger desires by the flight code number
        bob.purchaseTicket(23);
        bob.purchaseTicket(5);

        benGurioun.getBookingManager().getAllPassengers().forEach(System.out::println);

        //this is his all tickets
        bob.getTickets().forEach(System.out::println);

        //If the time of his flight changes, he will receive a notification about it
        //same about canceled flight
        MyDate changeTime = MyDate.of("19/10/24 19:00");
        benGurioun.getBookingManager().updateTimeFlight(
                benGurioun.getBookingManager().getFlightByCode(23), changeTime);


        // The passenger can subscribe to the notification service of the airport.
        // The system notifies the subscribers about every new change or canceled flight.
        alice.getPushes();

        // delete flight from the system, all the observers getting notify
        Flight deletedFlight = benGurioun.getBookingManager().getFlightByCode(5);
        benGurioun.getBookingManager().deleteFlight(deletedFlight);


//-----------------------------------creating worker ----------------------------------------------------------------
        Worker tomas = new Worker(50, "tomas", "gitman", benGurioun.getBookingManager());

        // adding him to the workers list,
        // then he will be added automatically to the notification service
        benGurioun.addWorker(tomas);

        // workers can perform operations described in the WorkerServiceFacade.
        // he just needs to call to getWorkerService function.
        //example for worker change flight time
        tomas.getWorkerService().updateTimeFlight(
                tomas.getWorkerService().getFlightByCode(1), // getting desire flight
                MyDate.of("18/9/24 16:48"), MyDate.of("19/9/24 22:50")
        );

// -------------------------------------------- testing zone ---------------------------------------------------------

    }

}
