package flights_manager.client_handle;

import flights_manager.airlines_components.Flight;

public interface FlightsNewsletter {
    void subscribe (FlightObserver observer);
    void unsubscribe (FlightObserver observer);
    void notifyAllObserver (String message);
    void notifyAllPassengers(Flight flight, String message);
}
