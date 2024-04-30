package flight_management;

import my_date_format.MyDate;

/**
 This class represents a newly scheduled flight.
 If there is an intention to plan a new departing flight,
 it must be created from this class
 */

public class Flight {

    private static int SERIAL_NUMBER = 1;
    private final int flight_code;

    /* origin indicate The location the flight depart from */
    private final String origin; // Modified to be final because if the source -
    private final String destination; //or destination changes, it will be considered as another/new flight
    private final String airLineName; // modified as final, for the same reason as mentioned above..
    private MyDate departureTime;
    private MyDate arrivalTime;
    private double price;

    public Flight(String airLineName, String origin, String destination, MyDate departureTime, MyDate arrivalTime, double price) {
        this.airLineName = airLineName;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        // Creating a new flight increments this member by 1, and each flight receives a unique identifier number
        this.flight_code = SERIAL_NUMBER++;
    }

    // overloading of the main constructor if for the option to insert dates in string format
    public Flight(String airLineName, String origin, String destination,
                  String departureTime, String arrivalTime, double price) {
        this(airLineName, origin, destination, MyDate.of(departureTime), MyDate.of(arrivalTime), price);
    }

    public String getCompName() {
        return airLineName;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public MyDate getDepartureTime() {
        return departureTime;
    }

    public MyDate getArrivalTime() {
        return arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    // --------- Encapsulate setter methods with package-private access for use only by relevant classes ------

    void setDepartureTime(MyDate departureTime) {
        this.departureTime = departureTime;
    }

    void setArrivalTime(MyDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    void setPrice(double price) {
        this.price = price;
    }

    // -------- end scope --------------------------------------------------------------------------------------

    public int getFlight_code(){
        return this.flight_code;
    }

    @Override
    public String toString() {
        return String.format("""
                        Flight code number: %d
                        Air Line: %s
                        From: %s
                        To: %s
                        Departure Time: %s
                        Arrival Time: %s
                        Cost: %.2f
                        """,
                flight_code, getCompName(), getOrigin(), getDestination(), getDepartureTime(), getArrivalTime(),
                getPrice());
    }

}
