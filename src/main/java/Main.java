import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        Restaurant cantuccio = new Restaurant("Cantuccio", 1);
        Client firstClientInQueue = new Client(1, "Mihnea Patentasu", "0784291808", LocalDate.now());
        Client secondClientInQueue = new Client(2, "Victor Patentasu", "0784291809", LocalDate.now());
        cantuccio.addClient(firstClientInQueue);
        cantuccio.addClient(secondClientInQueue);
        cantuccio.notifyNewFirstAndSecondClients();

        cantuccio.removeClientFromQueue(firstClientInQueue);
    }
}
