package controlers.observer_components;

import controlers.Flight;
import controlers.observer_components.FlightObserver;

public interface FlightsNewsletter {
    void subscribe (FlightObserver observer);
    void unsubscribe (FlightObserver observer);
    void notifyAllObserver (String message);
    void notifyAllPassengers(Flight flight, String message);
}
