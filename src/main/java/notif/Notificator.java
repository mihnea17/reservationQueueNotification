package notif;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class Notificator {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Notificator(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Notificator(){
    }

    @Bean
    public Notificator notificatorWithJDBC(){
        return new Notificator(jdbcTemplate);
    }

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

    public void registerClientForRestaurant(long restaurantId, Client client){
        this.jdbcTemplate.update("INSERT INTO clients (restaurantId, fullName, phoneNumber, reservationDateAndTime) VALUES (?,?,?,?)", restaurantId, client.getReservationName(), client.getPhoneNumber(), client.getTimeOfCreation());
    }

    public List<Client> getAllWaitingClientsForRestaurant(long restaurantId){
        return this.jdbcTemplate.query("SELECT * FROM clients WHERE deletionDateAndTime IS NULL AND restaurantId = ? ORDER BY reservationDateAndTime", new Object[] {restaurantId}, new RowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet resultSet, int i) throws SQLException {
                Client client = new Client(resultSet.getLong(1), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime(), (resultSet.getTimestamp(6) != null) ? resultSet.getTimestamp(6).toLocalDateTime() : null);
                return client;
            }
        });
    }

    public List<Client> getAllClientsForRestaurant(long restaurantId){
        return this.jdbcTemplate.query("SELECT * FROM clients WHERE restaurantId = ? ORDER BY reservationDateAndTime", new Object[] {restaurantId}, new RowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet resultSet, int i) throws SQLException {
                Client client = new Client(resultSet.getLong(1), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime(), (resultSet.getTimestamp(6) != null) ? resultSet.getTimestamp(6).toLocalDateTime() : null);
                return client;
            }
        });
    }

    public void sendNotification(Restaurant restaurant, Client client, int queuePosition){
        System.out.println("Notification sent to client on position "+ queuePosition +": " + client.getReservationName() + " on phone no. " + client.getPhoneNumber() +
                "\nRestaurant: " + restaurant.getRestaurantName() + "\n");
    }

    public Long getRestaurantId(Restaurant restaurant){
        return jdbcTemplate.queryForObject("SELECT restaurantId FROM restaurants WHERE fullName = ?", new Object[] { restaurant.getRestaurantName() }, Long.class);
    }

    public void removeClientFromDB(long restaurantId, long clientId) {
        jdbcTemplate.update(" UPDATE clients SET deletionDateAndTime = ? WHERE restaurantId = ? AND clientId = ?", new Object[] {LocalDateTime.now() ,restaurantId, clientId}, new int[] {
                Types.TIMESTAMP ,Types.BIGINT, Types.BIGINT});
    }

    public void removeFirstClientInQueueForRestaurant(long restaurantId) {
        try{
            Long clientId = jdbcTemplate.queryForObject("SELECT clientId FROM clients WHERE deletionDateAndTime IS NULL and restaurantId = ? ORDER BY reservationDateAndTime LIMIT 1", new Object[] {restaurantId}, Long.class);
            if(clientId != null)
                jdbcTemplate.update(" UPDATE clients SET deletionDateAndTime = ? WHERE restaurantId = ? AND clientId = ?", new Object[] {LocalDateTime.now() ,restaurantId, clientId}, new int[] {
                        Types.TIMESTAMP ,Types.BIGINT, Types.BIGINT});
        }
        catch(EmptyResultDataAccessException e){
            System.out.println("There are no more clients waiting for their reservation/notification!");
        }
    }

    public List<Client> getFirstTwoWaitingClientsForRestaurant(long restaurantId) {
        return this.jdbcTemplate.query("Select * FROM clients WHERE deletionDateAndTime IS NULL AND restaurantId = ? ORDER BY reservationDateAndTime LIMIT 2", new Object[] {restaurantId}, new RowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet resultSet, int i) throws SQLException {
                Client client = new Client(resultSet.getLong(1), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime(), (resultSet.getTimestamp(6) != null) ? resultSet.getTimestamp(6).toLocalDateTime() : null);
                return client;
            }
        });
    }
}
