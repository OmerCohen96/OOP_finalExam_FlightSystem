package flights_manager.client_handle;

public interface FlightsNewsletter {
    void subscribe (FlightObserver observer);
    void unsubscribe (FlightObserver observer);
    void notifyAllObserver (String message);
}
