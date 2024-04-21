package flights_manager;

import flights_manager.search_strategies.SearchMethod;

public interface PassengerServiceInterface {

    void purchaseTicket ();
    void searchFlight(SearchMethod... methods);
}
