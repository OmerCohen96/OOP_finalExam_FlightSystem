package controlers.observer_components;

import java.util.ArrayList;
import java.util.List;

public class Passenger implements FlightObserver {
    private int ID;
    private String name, lastName;
    private List<Ticket> tickets;

    private PassengerServiceFacade service;


    public Passenger (int ID , String name, String lastName, PassengerServiceFacade bookingService){
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.tickets = new ArrayList<>();
        this.service = bookingService;
    }

    public PassengerServiceFacade getService(){
        return service;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void purchaseTicket (int flightSerialNumber){
        Ticket newTicket = getService().purchaseTicket(flightSerialNumber, this);
        if (newTicket == null)
            System.out.println("fuck");
        getTickets().add(newTicket);
    }

    @Override
    public void update(String message) {
        System.out.printf("""
                hello %s %s
                there is new update
                %s
                %n""",name, lastName, message);
    }
}
