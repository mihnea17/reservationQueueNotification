package notif;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.InputStream;
import java.sql.Types;

public class Notificator {
    private JdbcTemplate jdbcTemplate;
    public Notificator(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Notificator(){}

    public void registerNewRestaurant(String restaurantName){
        this.jdbcTemplate.update("INSERT INTO restaurants (fullName) VALUES (?)", restaurantName);
    }

    public void registerClientForRestaurant(Restaurant restaurant, Client client){
        Long restaurantId = this.getRestaurantId(restaurant);
        if(restaurant != null)
            this.jdbcTemplate.update("INSERT INTO clients (restaurantId, fullName, phoneNumber, reservationDateAndTime) VALUES (?,?,?,?)", restaurantId, client.getReservationName(), client.getPhoneNumber(), client.getTimeOfCreation());
        else
            System.out.println("Restaurant was not found. Insertion of client failed.");
    }

    public void sendNotification(Restaurant restaurant, Client client, int queuePosition){
        System.out.println("Notification sent to client on position "+ queuePosition +": " + client.getReservationName() + " on phone no. " + client.getPhoneNumber() +
                "\nRestaurant: " + restaurant.getRestaurantName() + "\n");
    }

    public Long getRestaurantId(Restaurant restaurant){
        return jdbcTemplate.queryForObject("SELECT restaurantId FROM restaurants WHERE fullName = ?", new Object[] { restaurant.getRestaurantName() }, Long.class);
    }

    public void removeClientFromDB(long restaurantId, long clientId) {
        jdbcTemplate.update(" DELETE FROM clients WHERE restaurantId = ? AND clientId = ?", new Object[] {restaurantId, clientId}, new int[] {
            Types.BIGINT, Types.BIGINT});
    }

    public void removeFirstClientInQueueForRestaurant(long restaurantId) {
        Long clientId = jdbcTemplate.queryForObject("SELECT clientId FROM clients WHERE restaurantId = ? SORT BY reservationDateAndTime LIMIT 1", new Object[] {restaurantId}, Long.class);
        if(clientId != null)
            jdbcTemplate.update(" DELETE FROM clients WHERE restaurantId = ? AND clientId = ?", new Object[] {restaurantId, clientId}, new int[] {
                Types.BIGINT, Types.BIGINT});
    }
}
