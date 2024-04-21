package flights_manager.airlines_component;

import flights_manager.Flight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * this class represent big air line company that can purchased little air line company or another big company.
 * in composite design pattern terms, this class represent the "composite" component , a composite
 * component can contain a lot of components from his own type (AirLine type)
 */

public class GroupAirLine extends AirLine {
    private final List<AirLine> subsidiary;

    public GroupAirLine(String name) {
        super(name);
        this.subsidiary = new ArrayList<>();
    }

    // TODO: 20/04/2024 check for better ways
    @Override
    public List<Flight> getAllFlights() {
        if (this.subsidiary.size() == 0)
            return new ArrayList<>(getDepartingFlights());
        else
            return Stream.concat(getAllCompanies().stream().filter(
                    x -> !x.equals(this)).flatMap( x -> x.getAllFlights().stream()), // concat all the flights of
                    getDepartingFlights().stream()).toList();                       // of the subsidiary air lines
    }                                                                               // with the current company flights

    @Override
    public List<AirLine> getAllCompanies() {
        List<AirLine> allAirlines = new ArrayList<>(List.of(this));
        if (this.subsidiary.size() > 0)
            allAirlines.addAll(this.subsidiary.stream().flatMap(
                    x -> x.getAllCompanies().stream()).toList()); // this is where the recursion start flow
        return allAirlines;
    }

    public void addSubAirLines(AirLine... subAirLine) {
        this.subsidiary.addAll(Arrays.asList(subAirLine));
    }

    public void removeSubsidiaryAirLine(AirLine subAirLine) {
        this.subsidiary.remove(subAirLine);
    }

    @Override
    public String toString(){
        return super.toString() + " Group";
    }
}
