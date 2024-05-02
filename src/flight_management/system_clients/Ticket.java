package flight_management.system_clients;

import flight_management.Flight;

public class Ticket {
    private static int TICKET_NUMBER;
    private final int serialTicketNum;
    private final int flight_code;
    private final String flightInfo;

    public Ticket (Flight flight){
        flight_code = flight.getFlight_code();
        serialTicketNum = ++TICKET_NUMBER;
        flightInfo = flight.toString();
    }

    public int getSerialTicketNum(){
        return serialTicketNum;
    }

    public int getFlightBarcode(){
        return this.flight_code;
    }

    public void showDetails(){
        System.out.println(ticketInfo());
    }

    @Override
    public String toString (){
        return ticketInfo();
    }

    private String ticketInfo() {
        return "Ticket number: #" + getSerialTicketNum() + "\n" + flightInfo;
    }
}
