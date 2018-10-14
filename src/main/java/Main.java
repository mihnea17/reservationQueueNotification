import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        Restaurant cantuccio = new Restaurant("Cantuccio", 1);
        Client firstClientInQueue = new Client(1, "Mihnea Patentasu", "0123", LocalDate.now());
        Client secondClientInQueue = new Client(2, "Victor Patentasu", "0124", LocalDate.now());
        cantuccio.addClient(firstClientInQueue);
        cantuccio.addClient(secondClientInQueue);
        cantuccio.notifyNewFirstAndSecondClients();

        cantuccio.removeClientFromQueue(firstClientInQueue);
    }
}
