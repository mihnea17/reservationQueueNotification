import notif.Client;
import notif.Notificator;
import notif.Restaurant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Queue;

public class BasicTests {
    private Notificator notificator;
    private Restaurant cantuccio;
    private Client firstClientInQueue, secondClientInQueue;
    @Before
    public void setup(){
        notificator = new Notificator();
        cantuccio = new Restaurant("Cantuccio", notificator);
        firstClientInQueue = new Client("Mihnea Patentasu", "0123", LocalDateTime.now());
        secondClientInQueue = new Client("Victor Patentasu", "0124", LocalDateTime.now());
        cantuccio.addClient(firstClientInQueue);
        cantuccio.addClient(secondClientInQueue);
    }

    @Test
    public void testAddClient(){

        Assert.assertEquals(2,cantuccio.getClientsWaiting().size());
    }

    @Test
    public void testRemoveFirstClientFromQueue(){
        cantuccio.removeFirstClientFromQueue();
        Queue<Client> clientWaiting = cantuccio.getClientsWaiting();
        Assert.assertEquals(1, clientWaiting.size());
        Assert.assertEquals(secondClientInQueue, clientWaiting.peek());
    }

    @Test
    public void testNotifyNewFirstAndSecondClients(){
        cantuccio.notifyNewFirstAndSecondClients();
        cantuccio.removeFirstClientFromQueue();
        cantuccio.notifyNewFirstAndSecondClients();
        cantuccio.removeFirstClientFromQueue();
        cantuccio.notifyNewFirstAndSecondClients();
    }
}
