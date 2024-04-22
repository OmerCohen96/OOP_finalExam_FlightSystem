package flights_manager.client_handle;

import flights_manager.Flight;

public class Ticket {

    private final int serialTicketNum;
    private final String flightInfo;

    public Ticket (Flight flight){
        serialTicketNum = flight.getFlight_code();
        flightInfo = flight.toString();
    }

    public int getSerialTicketNum(){
        return serialTicketNum;
    }

    public void showDetails(){
        System.out.println(flightInfo);
    }
}
