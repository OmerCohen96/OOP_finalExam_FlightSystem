package flight_management.clients_components;

import flight_management.Flight;

public class Ticket {
    private static int TICKET_NUMBER;
    private final int serialTicketNum;
    private final String flightInfo;

    public Ticket (Flight flight){
        serialTicketNum = ++TICKET_NUMBER;
        flightInfo = flight.toString();
    }

    public int getSerialTicketNum(){
        return serialTicketNum;
    }

    public void showDetails(){
        System.out.println(ticketInfo());
    }

    @Override
    public String toString (){
        return ticketInfo();
    }

    private String ticketInfo() {
        return "Ticket number: " + getSerialTicketNum() + "\n" + flightInfo;
    }
}
