package flight_management.clients_components;

import java.util.ArrayList;
import java.util.List;

public class Passenger implements FlightObserver {
    private int ID;
    private String name, lastName;
    private List<Ticket> tickets;

    private PassengerServiceFacade serviceFacade;


    public Passenger (int ID , String name, String lastName, PassengerServiceFacade bookingService){
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.tickets = new ArrayList<>();
        this.serviceFacade = bookingService;
    }

    private PassengerServiceFacade getServiceFacade(){
        return serviceFacade;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void purchaseTicket (int flightSerialNumber){
        Ticket newTicket = getServiceFacade().purchaseTicket(flightSerialNumber, this);
        if (newTicket == null)
            System.out.println("No matching flight found.");
        else
            getTickets().add(newTicket);
    }
    public void searchFlight (){
        getServiceFacade().searchFlight();
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
