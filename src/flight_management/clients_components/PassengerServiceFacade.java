package flight_management.clients_components;

public interface PassengerServiceFacade {

    Ticket purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight();
}
