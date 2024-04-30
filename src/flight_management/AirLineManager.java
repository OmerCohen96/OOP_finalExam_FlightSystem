package flight_management;

import java.util.*;

/**
 The primary purpose of this class is to collect ,maintain data, and managing the existing airlines inventory.
 this class utilizes the Singleton design pattern, meaning that it can be instantiated only once.
 since this project is intended for use in a single airport,
 I believe it's appropriate to ensure there is only one instance of it.
 */

public class AirLineManager {


    private static AirLineManager airLineManager = null; // the single object is stored inside this field

    //the singleton "constructor"
    public static AirLineManager getInstance() {
        if (airLineManager == null)
            airLineManager = new AirLineManager();
        return airLineManager;
    }

/**
 * This set holds only major airline groups or individual airlines that are at the top of the hierarchy,
 * I mean just for AirLines that are parent companies.
 * Subsidiary airlines cannot be stored here.
 * To retrieve all existing companies, including subsidiaries,
 * we utilize the Composite Pattern implemented by AirLine objects.
 */
    private final Set<AirLine> airLineGroups;

    private AirLineManager(){
        this.airLineGroups = new HashSet<>();
    }

    public void addAirLineGroup (AirLine ... airLineGroup){
        this.airLineGroups.addAll(Arrays.asList(airLineGroup));
    }

    public Set<AirLine> getAirLineGroups(){
        return this.airLineGroups;
    }

    /**
     * Search for the parent company whose name matches the input compName.
     * @return {@code AirLine} if there is a match, otherwise return {@code null}.
     */
    public AirLine findAirLineGroup(String compName){
        return this.getAirLineGroups().stream().filter(
                airLine -> airLine.getCompanyName().equalsIgnoreCase(compName)).findFirst().orElse(null);
    }

    /**
     * removes the matching parent airLine if it exists
     */
    public void removeAirLineGroup(String compName){
        AirLine removedAirline = findAirLineGroup(compName);
        if (removedAirline != null)
            getAirLineGroups().remove(removedAirline);
    }

    /**
     * @return all AirLine objects, including those considered as subsidiary airlines
     */
    public List<AirLine> getAirLinePool(){
    /*
    Iterate over the airLineGroups set field,
    extract all subsidiary companies using their getAllCompanies method on each parent airline in the set,
    and concatenate all objects into one list.
    This function leverages one of the advantages of the composite pattern.
    Each object in the list can be a complex AirLine (a group airline that encompasses many subsidiary airlines),
    but this function treats it as a simple object like SingularAirLine
    */
        return getAirLineGroups().stream().flatMap(airLine -> airLine.getAllCompanies().stream()).distinct().toList();
    }

    /**
     * Search for the airline company including those considered as subsidiary airlines
     * whose name matches the input compName.
     * @return {@code AirLine} if there is a match, otherwise return {@code null}.
     */
    public AirLine findAirLineComponent(String comp){
        return this.getAirLinePool().stream().
                filter(airLine -> airLine.getCompanyName().equalsIgnoreCase(comp))
                        .findFirst().orElse(null);
    }

}
