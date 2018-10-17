package notif;

import org.springframework.dao.EmptyResultDataAccessException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Restaurant {
    private String restaurantName;
    private Queue<Client> clientsWaiting = new LinkedList<>();
    private Notificator notificator;

    private int internalQueueCounter;

    public Restaurant(String restaurantName, Notificator notificator){
        this.restaurantName = restaurantName;
        this.notificator = notificator;
    }

    public void addClient(Client incomingClient){

        clientsWaiting.add(incomingClient);
        this.internalQueueCounter++;
    }

    public void registerClientForRestaurant(Client client){
        this.notificator.registerClientForRestaurant(this, client);
    }

    public void removeClientFromDB(long clientId){
        notificator.removeClientFromQueueDB(getRestaurantId(), clientId);
    }

    public void removeFirstClientInQueueFromDB(){
        try{
            notificator.removeFirstClientInQueueForRestaurant(this.getRestaurantId());
        }
        catch (EmptyResultDataAccessException e){
            System.out.println("No client to remove from waiting queue");
        }
    }

    public void notifyNewFirstAndSecondClientsFromDB(){
        List<Client> firstTwoWaitingClients = getFirstTwoWaitingClients();
        if(!firstTwoWaitingClients.isEmpty()){
            this.sendNotification(firstTwoWaitingClients.get(0), 1);
            if(firstTwoWaitingClients.size()==2){
                this.sendNotification(firstTwoWaitingClients.get(1), 2);
            }
        }
    }

    public List<Client> getAllWaitingClients(){
        return this.notificator.getAllWaitingClientsForRestaurant(this.getRestaurantId());
    }

    public List<Client> getFirstTwoWaitingClients(){
        return this.notificator.getFirstTwoWaitingClientsForRestaurant(this.getRestaurantId());
    }

    public void removeClientFromQueue(Client cancelledClient){
        if(!clientsWaiting.remove(cancelledClient)){
            System.out.println("Client was not in the queue anymore");
        }
        else{
            System.out.println("Client was removed successfully from queue");
            this.internalQueueCounter--;
        }
    }

    public void removeFirstClientFromQueue(){
        if(!clientsWaiting.isEmpty()) {
            clientsWaiting.remove();
            this.internalQueueCounter--;
        }
        else{
            System.out.println(this.restaurantName + " has no waiting clients anymore.");
        }
    }

    public void notifyNewFirstAndSecondClients(){
        if(!clientsWaiting.isEmpty()) {
            sendNotification(clientsWaiting.element(),1);
            if(clientsWaiting.size()>=2){
                sendNotification(((LinkedList<Client>) clientsWaiting).get(1),2);
            }
        }
        else{
            System.out.println(this.restaurantName + " has no waiting clients anymore.");
        }
    }

    public void sendNotification(Client client, int queuePosition){
        this.notificator.sendNotification(this, client, queuePosition);
    }

    public Queue<Client> getClientsWaiting(){
        return this.clientsWaiting;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    private long getRestaurantId() {
        return this.notificator.getRestaurantId(this);
    }
}
