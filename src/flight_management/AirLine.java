package flight_management;

import java.util.ArrayList;
import java.util.List;

/**
 * this abstract class represent a type of AirLine, company that offer outbound and inbound flights.
 * the concrete classes that extends from this class represent an AirLine company, either if its
 * a big AirLine company that obtain small airlines companies or a small airlines that work alone or
 * have been purchased by a large company.
 * <p>
 * inside this package im relied on Composite Design Pattern.
 * when we extract list of Flights or list Companies,
 * the 2 abstract functions collect the relevant data for big or small company,
 * for example, if we use getAllFlight() on some object of AirLine type, if this type instance of
 * big company, this function extract all the big company flights plus all the flights
 * of the small companies that owned by the big company.
 * if it small company , the function return the flights that belong to the small company,
 * the client should not be concerned about the instance type of the company as long as the class
 * extend from this AirLine abstract class.
 */

public abstract class AirLine {
    private final String companyName;
    private final List<Flight> flights;

    public AirLine(String name) {
        this.companyName = name;
        this.flights = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

//    Return the flights belonging to the current instance only.
//    ignoring flights of descendant airlines. For internal use only.
    protected List<Flight> getInternalFlights() {
        return this.flights;
    }

    void addFlight(Flight... flightList) {
        for (Flight flight : flightList)
            if (!getAllFlights().contains(flight))
                getInternalFlights().add(flight);
    }

    void removeFlight(Flight flight) {
        flights.remove(flight);
    }

    //--------------------------------------------------------------------------------------------------//

    /*
      Those two functions are at the core of the Composite design pattern:
      the Composite (GroupAirLine) and the Leaf (SingularAirLine) classes inherit from this class
      each of these classes must implement these functions in their own way,
      which is why they are abstract functions.
      The client doesn't need to worry about whether it's working with a big group company airline or a singular airline
     */

    /**
     * @return - list of all descendant airline flights, including those of the current instance itself
     */
    abstract List<Flight> getAllFlights();

    /** @return - list of all descendants airline companies, include the current instance itself
     */
    abstract List<AirLine> getAllCompanies();

    //--------------------------------------------------------------------------------------------------//

    @Override
    public String toString() {
        return getCompanyName();
    }
}
