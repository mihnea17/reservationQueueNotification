package notif;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

//http://localhost:8080/h2-console
@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
//        Notificator notificator = new Notificator();
//        Restaurant cantuccio = new Restaurant("Cantuccio", 1, notificator);
//        Client firstClientInQueue = new Client(1, "Mihnea Patentasu", "0123", LocalDateTime.now());
//        Client secondClientInQueue = new Client(2, "Victor Patentasu", "0124", LocalDateTime.now());

        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... strings) {
        log.info("Creating client table");
        jdbcTemplate.execute("DROP TABLE clients IF EXISTS");
        jdbcTemplate.execute("DROP TABLE restaurants IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE restaurants(" +
                "restaurantId IDENTITY, fullName VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE clients(" +
                "clientId IDENTITY, restaurantId BIGINT, fullName VARCHAR(255), phoneNumber VARCHAR(20), reservationDateAndTime TIMESTAMP WITHOUT TIME ZONE," +
                "foreign key (restaurantId) references restaurants(restaurantId))");

        Notificator notificator = new Notificator(jdbcTemplate);
        Restaurant cantuccio = new Restaurant("Cantuccio", notificator);
        Restaurant bellagio = new Restaurant("Bellagio", notificator);

        notificator.registerNewRestaurant(cantuccio.getRestaurantName());
        notificator.registerNewRestaurant(bellagio.getRestaurantName());
//        jdbcTemplate.update("INSERT INTO restaurants (fullName) VALUES (?)", cantuccio.getRestaurantName());
//        jdbcTemplate.update("INSERT INTO restaurants (fullName) VALUES (?)", bellagio.getRestaurantName());


        Client firstClientInQueue = new Client("Mihnea Patentasu", "0123", LocalDateTime.now());
        notificator.registerClientForRestaurant(cantuccio, firstClientInQueue);
        Client secondClientInQueue = new Client("Victor Patentasu", "0124", LocalDateTime.now());
        notificator.registerClientForRestaurant(cantuccio, secondClientInQueue);
//        jdbcTemplate.update("INSERT INTO clients (restaurantId, fullName, phoneNumber, reservationDateAndTime) VALUES (?,?,?,?)", cantuccio.getRestaurantId(), firstClientInQueue.getReservationName(), firstClientInQueue.getPhoneNumber(), firstClientInQueue.getTimeOfCreation());

//        cantuccio.removeClientFromDB(1);
        cantuccio.removeFirstClientInQueueFromDB();

    }


}
