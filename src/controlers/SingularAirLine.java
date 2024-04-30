package controlers;

import java.util.ArrayList;
import java.util.List;

/**
 This class represents a small airline company that can optionally be purchased by a larger group (GroupAirLine).
 In terms of the composite design pattern, this class represents the 'leaf' component.
 A leaf component can be contained by a composite component or can function independently.
 */

public class SingularAirLine extends AirLine {

    public SingularAirLine(String name) {
        super(name);
    }

    @Override
    public List<Flight> getAllFlights() {
        return new ArrayList<>(getInternalFlights());
    }

    @Override
    public List<AirLine> getAllCompanies() {
        return new ArrayList<>(List.of(this));
    }


}
