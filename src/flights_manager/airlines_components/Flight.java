package flights_manager.airlines_components;

import my_date_format.MyDate;

/**
 * this class represents a new scheduled flight.
 * if there is an intention to plan a new departing flight, from a decision of any airline,
 * its must be created from this class.
 */

public class Flight {

    private static int SERIAL_NUMBER = 1;
    private final int flight_code;
    private final String origin; // final because if the dest/source changes it will be considered as another/new flight
    private final String destination;
    private final String airLineName; // final cause the same reason above
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
        this.flight_code = SERIAL_NUMBER++;
    }

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

    public void setDepartureTime(MyDate departureTime) {
        this.departureTime = departureTime;
    }

    public MyDate getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(MyDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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
