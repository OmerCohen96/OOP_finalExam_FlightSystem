package flights_manager.client_handle;

public interface PassengerServiceFacade {

    Ticket purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight();
}
