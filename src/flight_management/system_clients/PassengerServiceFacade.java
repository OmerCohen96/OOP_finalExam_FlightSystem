package flight_management.system_clients;

import flight_management.observer_components.FlightObserver;
/**
 * This interface defines which operations can passenger approach in the bookingManager system
 */
public interface PassengerServiceFacade {

    Ticket purchaseTicket (int serialNumber, Passenger passenger);
    void searchFlight();
    void subscribeToPushService(FlightObserver observer);
    void unsubscribeFromPushService(FlightObserver observer);
}
