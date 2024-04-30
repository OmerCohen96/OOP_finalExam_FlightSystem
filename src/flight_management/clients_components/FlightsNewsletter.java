package flight_management.clients_components;

import flight_management.Flight;

public interface FlightsNewsletter {
    void subscribe (FlightObserver observer);
    void unsubscribe (FlightObserver observer);
    void notifyAllObserver (String message);
    void notifyAllPassengers(Flight flight, String message);
}
