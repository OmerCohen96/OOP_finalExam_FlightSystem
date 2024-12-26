package flight_management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 This class represents a large airline company that can purchase any airline object.
 In terms of the composite design pattern, this class represents the 'composite' component.
 A composite component can contain many components of its own type (AirLine type)
 */

public class GroupAirLine extends AirLine {
    private final List<AirLine> subsidiary;

    public GroupAirLine(String name) {
        super(name);
        this.subsidiary = new ArrayList<>();
    }

    private List<AirLine> getSubsidiary(){
        return this.subsidiary;
    }
    @Override
    List<Flight> getAllFlights() {
        if (this.subsidiary.size() == 0)
            return new ArrayList<>(getInternalFlights());
        else
            return Stream.concat(getSubsidiary().stream().
                            flatMap(x -> x.getAllFlights().stream()).distinct(), // concat all the flights of
                            getInternalFlights().stream()).toList();                       // of the subsidiary air lines
    }
    @Override
    List<AirLine> getAllCompanies() {
        List<AirLine> allAirlines = new ArrayList<>(List.of(this));
        if (this.subsidiary.size() > 0)
            allAirlines.addAll(getSubsidiary().stream().flatMap(
                    x -> x.getAllCompanies().stream()).distinct().toList()); // this is where the recursion start flow
        return allAirlines;
    }

    public void addSubAirLines(AirLine... subAirLine) {
        this.subsidiary.addAll(Arrays.asList(subAirLine));
    }

    public void removeSubAirLine(AirLine subAirLine) {
        this.subsidiary.remove(subAirLine);
    }

    @Override
    public String toString(){
        return super.toString() + " Group";
    }
}
