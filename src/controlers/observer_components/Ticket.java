package controlers.observer_components;

import controlers.Flight;

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
        System.out.println(getFlightInfo());
    }

    public String getFlightInfo() {
        return "Ticket number: " + getSerialTicketNum() + "\n" + flightInfo;
    }

    @Override
    public String toString (){
        return getFlightInfo();
    }
}
