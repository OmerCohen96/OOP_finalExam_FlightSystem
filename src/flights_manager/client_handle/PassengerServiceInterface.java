package flights_manager.client_handle;

import Person.Passenger;
import flights_manager.search_strategies.SearchMethod;

public interface PassengerServiceInterface {

    void purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight(SearchMethod... methods);
}