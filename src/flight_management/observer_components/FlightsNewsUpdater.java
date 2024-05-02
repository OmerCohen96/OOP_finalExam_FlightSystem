package flight_management.observer_components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FlightsNewsUpdater implements FlightsNewsletterInterface{
    private final Set<FlightObserver> observersList;
    public FlightsNewsUpdater(){
        observersList = new HashSet<>();
    }
    @Override
    public void subscribe(FlightObserver observer) {
        observersList.add(observer);
    }
    @Override
    public void unsubscribe(FlightObserver observer) {
        observersList.remove(observer);
    }
    @Override
    public void notifyAllObserver(String message) {
        observersList.forEach(flightObserver -> flightObserver.update(message));
    }
    @Override
    public void notifyAllObserver(Collection<? extends FlightObserver> observers, String message){
        observers.forEach(flightObserver -> flightObserver.update(message));
    }
}
