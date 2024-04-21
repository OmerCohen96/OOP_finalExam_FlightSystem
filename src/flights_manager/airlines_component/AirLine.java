package flights_manager.airlines_component;

import flights_manager.Flight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this abstract class represent a type of AirLine, company that offer outbound and inbound flights.
 * the concrete classes that extends from this class represent an AirLine company, either if its
 * a big AirLine company that obtain small airlines companies or a small airlines that work alone or
 * have been purchased by a large company.
 * <p>
 * in this package im relied on Composite Design Pattern.
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

    // return the flights belonging to the current instance only (ignore descendants airlines flights)
    // for inner use only
    protected List<Flight> getDepartingFlights() {
        return this.flights;
    }

    public void addFlight(Flight... flightList) {
        flights.addAll(Arrays.asList(flightList));
    }

    public void removeFlight(Flight flight) {
        flights.remove(flight);
    }

    //--------------------------------------------------------------------------------------------------//

    /* those 2 functions under is the heart of the composite design pattern
      the Composite(GroupAirLine) and the Leaf(singularAirLine) classes that inherits from this class,
      each of those, must implement those functions in their own way , thats why it is abstract function.
      the client dont need to care if its work with big group company airline or with singular airLine.
     */

    /**
     * @return - list of all descendants airline flights include those of the current instance itself
     */
    public abstract List<Flight> getAllFlights();

    /** @return - list of all descendants airline include the company itself
     */
    public abstract List<AirLine> getAllCompanies();

    //--------------------------------------------------------------------------------------------------//

    @Override
    public String toString() {
        return getCompanyName();
    }
}
