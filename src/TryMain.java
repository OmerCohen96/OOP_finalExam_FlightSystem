import flight_management.AirLineManager;
import flight_management.BookingManager;
import flight_management.AirLine;
import flight_management.Flight;
import flight_management.GroupAirLine;
import flight_management.SingularAirLine;

import java.util.List;

public class TryMain {
    public static void main(String[] args) {
        AirLineManager airLineManager = AirLineManager.getInstance();
        BookingManager bookingManager = BookingManager.getInstance();

        GroupAirLine airFrance = new GroupAirLine("Air France");
        GroupAirLine ELAL = new GroupAirLine("ELAL");
        SingularAirLine israir = new SingularAirLine("Israir");
        SingularAirLine arkia = new SingularAirLine("Arkia");

        ELAL.addSubAirLines(israir, arkia);
        airFrance.addSubAirLines(ELAL,arkia,ELAL,ELAL);


        Flight flight1
                = new Flight("arkia", "israel", "albania", "17/5/24 8:00", "17/5/24 12:00", 250.5);
        Flight flight2
                = new Flight("ELAL", "France", "London", "17/5/24 14:30", "17/5/24 18:45", 550.75);
        Flight flight3
                = new Flight("air france", "Japan", "Tel Aviv", "18/5/24 10:15", "19/5/24 6:30", 920.0);
        Flight flight4
                = new Flight("israir", "Australia", "Israel", "19/6/24 23:45", "20/6/24 15:20", 1250.25);
        Flight flight5
                = new Flight("israir", "Dubai", "Singapore", "21/5/24 8:00", "21/5/24 12:30", 420.99);

        airLineManager.addAirLineGroup(airFrance);
        bookingManager.addNewFlight(flight1,flight2,flight3,flight4,flight5);





        System.out.println("-----------------------------");


        List<AirLine> gaga = airLineManager.getAirLinePool();





    }
}
