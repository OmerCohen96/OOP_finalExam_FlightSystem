package controlers.observer_components;

public interface PassengerServiceFacade {

    Ticket purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight();
}
