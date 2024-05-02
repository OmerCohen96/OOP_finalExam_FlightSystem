package flight_management.system_clients;

import flight_management.Flight;
import my_date_format.MyDate;

import java.util.List;

/**
 * This interface defines which operations can worker approach in the bookingManager system
 */
public interface WorkerServiceFacade {
    Flight getFlightByCode (int serialNumber);
    List<Flight> getAllFlights ();
    void searchFlight ();
    void updateTimeFlight (Flight flight, MyDate newDepartTime, MyDate newArrvlTime);
    void updateTimeFlight (Flight flight, MyDate newDepartTime);
    void deleteFlight(Flight flight);
}
