package flights_manager.client_handle;

import Person.Passenger;
import flights_manager.BookingManager;
import flights_manager.Flight;
import flights_manager.search_strategies.SearchMethod;

import java.util.List;
import java.util.NoSuchElementException;

public class PassengerServiceFacade {

    private final BookingManager bookingManager;

    public PassengerServiceFacade (BookingManager bookingManager){
        this.bookingManager = bookingManager;
    }

    public void searchFlight (SearchMethod ... methods){
        List<Flight> flights = getBookingManager().searchFlight(methods);
        if (flights != null){
            System.out.println("Here's what we found: ");
            flights.forEach(System.out::println);
        } else {
            System.out.println("We couldn't find a suitable flight, Sorry");
        }
    }

    public Ticket purchaseFlight (int serialNumber, Passenger passenger){
        Flight flight = getBookingManager().getFlightByCode(serialNumber);
        if (flight != null) {
            getBookingManager().addNewPassenger(flight, passenger);
            return new Ticket(flight);
        }
        else
            throw new NoSuchElementException("Cant find flight with this Serial Number");
    }

    private BookingManager getBookingManager(){
        return this.bookingManager;
    }
}
