package flight_management.observer_components;

import flight_management.observer_components.FlightObserver;

import java.util.Collection;

public interface FlightsNewsletterInterface {
    void subscribe (FlightObserver observer);
    void unsubscribe (FlightObserver observer);
    void notifyAllObserver (String message);
    void notifyAllObserver (Collection<? extends FlightObserver> observers, String message);
}
