package flight_management.system_clients;

import flight_management.observer_components.FlightObserver;

import java.util.ArrayList;
import java.util.List;

public class Passenger implements FlightObserver {
    private final int ID;
    private final String name;
    private final String lastName;
    private final List<Ticket> tickets;
    private final PassengerServiceFacade serviceFacade;

    public Passenger (int ID , String name, String lastName, PassengerServiceFacade bookingManager){
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.tickets = new ArrayList<>();
        this.serviceFacade = bookingManager;
    }

    private PassengerServiceFacade getServiceFacade(){
        return serviceFacade;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void purchaseTicket (int flightSerialNumber){
        Ticket newTicket = getServiceFacade().purchaseTicket(flightSerialNumber, this);
        if (newTicket == null) {
            System.out.println("No matching flight found.");
        }
        else {
            System.out.printf("""
                    The purchase was completed successfully
                    here is the flight info:
                    	 %s
                    %n""", newTicket);
            getTickets().add(newTicket);
        }
    }

    public void searchFlight (){
        getServiceFacade().searchFlight();
    }

    public void getPushes(){
        getServiceFacade().subscribeToPushService(this);
    }

    public void stopPushes(){
        getServiceFacade().unsubscribeFromPushService(this);
    }

    @Override
    public void update(String message) {
        System.out.printf("""
                Hello %s %s,
                There is new update:
                %s
                %n""",name, lastName, message);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
