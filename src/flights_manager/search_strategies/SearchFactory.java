package flights_manager.search_strategies;


public class SearchFactory {
    public static SearchStrategy generate(SearchMethod method) throws RuntimeException {
        switch (method){
            case BY_DATE:
                return new ByDate();
            case BY_PRICE:
                return new ByPrice();
            case BY_DESTINATION:
                return new ByDestination();
            default:
                throw new RuntimeException(
                        "cant find such search method -> use Enum unit from SearchMethod Enum class");
        }
    }
}
