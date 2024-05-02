package flight_management.system_clients;

import flight_management.observer_components.FlightObserver;

public interface PassengerServiceFacade {

    Ticket purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight();
    void subscribeToPushService(FlightObserver observer);
    void unsubscribeFromPushService(FlightObserver observer);
}
