package flights_manager.client_handle;

import java.util.List;

public class Passenger implements FlightObserver{
    private int ID;
    private String name, lastName;
    private List<Ticket> tickets;


    public Passenger (int ID , String name, String lastName){

    }


    @Override
    public void updateFlightStatus(String message) {

    }
}
