package flights_manager.airlines_component;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent a small airline company that optionally can be purchased by a large group (big airline company)
 * in composite design pattern terms, this class represent the "leaf" component ,
 * a leaf component can be contained by a composite component, or can work by itself
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
