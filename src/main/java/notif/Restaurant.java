package notif;

import java.util.LinkedList;
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

    public void removeClientFromDB(long clientId){
        notificator.removeClientFromDB(getRestaurantId(), clientId);
    }

    public void removeFirstClientInQueueFromDB(){
        notificator.removeFirstClientInQueueForRestaurant(this.getRestaurantId());
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
