package controlers;

import java.util.*;

public class AirLineManager {

    private static AirLineManager airLineManager = null;

    public static AirLineManager getInstance() {
        if (airLineManager == null)
            airLineManager = new AirLineManager();
        return airLineManager;
    }

    private final Set<AirLine> airLineGroups;
//    private final String airPortName;
//    private final Map<Integer, List<Passenger>> flightsMap;

    private AirLineManager(){
//        this.airPortName = airPortName;
        this.airLineGroups = new HashSet<>();
//        this.flightsMap =  new HashMap<>();
    }

    public void addAirLineGroup (AirLine ... airLineGroup){
        this.airLineGroups.addAll(Arrays.asList(airLineGroup));
    }

    public Set<AirLine> getAirLineGroups(){
        return this.airLineGroups;
    }

    public AirLine findAirLineGroup(String compName){
        return this.getAirLineGroups().stream().filter(
                airLine -> airLine.getCompanyName().equalsIgnoreCase(compName)).findFirst().orElse(null);
    }
    public void removeAirLineGroup(String compName){
        AirLine removedAirline = findAirLineGroup(compName);
        if (removedAirline != null)
            getAirLineGroups().remove(removedAirline);
    }

    public List<AirLine> getAirLinePool(){
        return getAirLineGroups().stream().flatMap(airLine -> airLine.getAllCompanies().stream()).distinct().toList();
    }

    public AirLine findAirLineComponent(String comp){
        return this.getAirLinePool().stream().
                filter(airLine -> airLine.getCompanyName().equalsIgnoreCase(comp))
                        .findFirst().orElse(null);
    }

//    public Map<Integer, List<Passenger>> getFlightsMap() {
//        return flightsMap;
//    }

//    public String getAirPortName(){
//        return this.airPortName;
//    }

//    public static void main(String[] args) {
//        GroupAirLine airFrance = new GroupAirLine("AirFrance");
//        SingularAirLine KLM = new SingularAirLine("KLM");
//        SingularAirLine transavia = new SingularAirLine("Transavia");
//        SingularAirLine littleFrance = new SingularAirLine("LittleFrance");
//        GroupAirLine ELAL = new GroupAirLine("ELAL");
//        SingularAirLine israir = new SingularAirLine("Israir");
//        SingularAirLine arkia = new SingularAirLine("Arkia");
//        GroupAirLine international = new GroupAirLine("International");
//        SingularAirLine british = new SingularAirLine("British Airways");
//        SingularAirLine europe = new SingularAirLine("Europe Airways");
//        GroupAirLine airChina = new GroupAirLine("air China");
//        SingularAirLine japanAirways = new SingularAirLine("Japan Airways");
//        international.addSubAirLines(british, europe);
//        ELAL.addSubAirLines(israir, arkia);
//        airFrance.addSubAirLines(KLM, transavia, littleFrance);
//        international.addSubAirLines(ELAL);
//        AirLineManager manager = new AirLineManager();
//        manager.addAirLineGroup(airFrance,ELAL, international, japanAirways, airChina);
//
//        Flight flight1
//                = new Flight("arkia", "israel", "albania", "17/5/24 8:00", "17/5/24 12:00", 250.5);
//        Flight flight2
//                = new Flight("KLM", "France", "London", "17/5/24 14:30", "17/5/24 18:45", 550.75);
//        Flight flight3
//                = new Flight("air China", "Japan", "Tel Aviv", "18/5/24 10:15", "19/5/24 6:30", 920.0);
//        Flight flight4
//                = new Flight("ELAL", "Australia", "Israel", "19/6/24 23:45", "20/6/24 15:20", 1250.25);
//        Flight flight5
//                = new Flight("japan Airways", "Dubai", "Singapore", "21/5/24 8:00", "21/5/24 12:30", 420.99);
//        Flight flight6
//                = new Flight("air China", "China", "Israel", "22/5/24 16:45", "22/5/24 19:10", 320.5);
//        Flight flight7
//                = new Flight("Europe Airways", "Italy", "Rome", "23/5/24 12:30", "23/5/24 15:20", 280.75);
//        Flight flight8
//                = new Flight("israir", "Israel", "Mexico City", "24/5/24 9:15", "24/5/24 11:30", 180.0);
//        Flight flight9
//                = new Flight("international", "Germany", "Moscow", "25/5/24 21:00", "26/5/24 2:45", 380.25);
//        Flight flight10
//                = new Flight("ELAL", "Israel", "Bangkok", "26/5/24 13:20", "26/5/24 15:10", 150.99);
//        Flight flight11
//                = new Flight("japan Airways", "China", "Seoul", "27/5/24 8:45", "27/5/24 10:30", 200.5);
//        Flight flight12
//                = new Flight("israir", "Israel", "Madrid", "28/5/24 14:00", "28/5/24 16:20", 320.75);
//        Flight flight13
//                = new Flight("ELAL", "Mexico City", "Israel", "29/5/24 19:30", "30/5/24 1:15", 680.0);
//        Flight flight14
//                = new Flight("air china", "Russia", "Dubai", "30/5/24 6:45", "30/5/24 11:10", 470.25);
//        Flight flight15
//                = new Flight("transavia", "Thailand", "Hong Kong", "31/5/24 10:15", "31/5/24 12:45", 180.99);
//        Flight flight16
//                = new Flight("ELAL", "Japan", "Israel", "1/6/24 14:30", "1/6/24 16:10", 220.5);
//        Flight flight17
//                = new Flight("Europe Airways", "Spain", "Barcelona", "2/6/24 11:45", "2/6/24 13:00", 90.75);
//        Flight flight18
//                = new Flight("KLM", "Argentina", "Santiago", "3/6/24 8:00", "3/6/24 10:15", 120.0);
//        Flight flight19
//                = new Flight("air China", "Hong Kong", "Israel", "4/6/24 17:20", "4/6/24 19:00", 150.25);
//        Flight flight20
//                = new Flight("japan Airways", "Japan", "Osaka", "5/6/24 10:30", "5/6/24 12:00", 100.99);
//        Flight flight21
//                = new Flight("Europe Airways", "Portugal", "Lisbon", "6/6/24 13:45", "6/6/24 15:30", 180.5);
//
//
//
//    }

}
