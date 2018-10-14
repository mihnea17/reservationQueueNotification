public class Notificator {
    public void sendNotification(Restaurant restaurant, Client client){
        System.out.println("Notification sent to: " + client.getReservationName() + " on phone no. " + client.getPhoneNumber() + "\nRestaurant: " + restaurant.getRestaurantName());
    }
}
