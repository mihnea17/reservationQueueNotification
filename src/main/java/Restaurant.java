import java.util.LinkedList;
import java.util.Queue;

public class Restaurant {
    private String restaurantName;
    private long restaurantId;
    private Queue<Client> clientsWaiting = new LinkedList<>();

    public Restaurant(String restaurantName, long restaurantId){
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
    }

    public void addClient(Client incomingClient){
        clientsWaiting.add(incomingClient);
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void removeClientFromQueue(Client cancelledClient){
        if(!clientsWaiting.remove(cancelledClient)){
            System.out.println("Client was not in the queue anymore");
        }
        else{
            System.out.println("Client was removed successfully from queue");
        }
    }

    public void removeClientFromQueue(){
        if(!clientsWaiting.isEmpty()) {
            clientsWaiting.remove();
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
        System.out.println("Notification sent to client on position "+ queuePosition +": " + client.getReservationName() + " on phone no. " + client.getPhoneNumber() +
                "\nRestaurant: " + this.getRestaurantName() + "\n");
    }

}
