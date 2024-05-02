package flight_management.system_clients;


import flight_management.observer_components.FlightObserver;

public class Worker implements FlightObserver {

    private static int WORKER_NUMBER = 1;
    private final int ID, workerNum;
    private final String name;
    private final String lastName;
    private final WorkerServiceFacade workerService;

    public Worker (int ID, String name, String lastName, WorkerServiceFacade bookingManager){
        this.ID = ID;
        this.workerNum = WORKER_NUMBER++;
        this.name = name;
        this.lastName = lastName;
        this.workerService = bookingManager;
    }
    public WorkerServiceFacade getWorkerService() {
        return workerService;
    }
    public int getID() {
        return ID;
    }
    public int getWorkerNumber(){
        return workerNum;
    }
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public void update(String message) {
        System.out.printf("""
                Hello %s %s, number %d
                There is a change:
                %s
                %n""",name, lastName,workerNum, message);
    }
}
