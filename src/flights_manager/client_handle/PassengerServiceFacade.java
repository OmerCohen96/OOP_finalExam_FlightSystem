package flights_manager.client_handle;

import flights_manager.search_strategies.SearchMethod;

public interface PassengerServiceFacade {

    Ticket purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight();
}
