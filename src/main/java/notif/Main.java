package notif;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

//http://localhost:8080/h2-console
@SpringBootApplication
@ComponentScan({"notif", "restControllers"})
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
    public void run(String... strings) throws InterruptedException {
        log.info("Creating client table");
        jdbcTemplate.execute("DROP TABLE clients IF EXISTS");
        jdbcTemplate.execute("DROP TABLE restaurants IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE restaurants(" +
                "restaurantId IDENTITY, fullName VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE clients(" +
                "clientId IDENTITY, restaurantId BIGINT, " +
                "fullName VARCHAR(255), " +
                "phoneNumber VARCHAR(20), " +
                "reservationDateAndTime TIMESTAMP WITHOUT TIME ZONE, " +
                "deletionDateAndTime TIMESTAMP WITHOUT TIME ZONE," +
                "foreign key (restaurantId) references restaurants(restaurantId))");

        Notificator notificator = new Notificator(jdbcTemplate);
        Restaurant cantuccio = new Restaurant("Cantuccio", notificator);
//        Restaurant bellagio = new Restaurant("Bellagio", notificator);
//        notificator.registerNewRestaurant(bellagio.getRestaurantName());
//        bellagio.registerClientForRestaurant(firstClientInQueue);
//        Thread.sleep(1000);
//        bellagio.registerClientForRestaurant(secondClientInQueue);

        notificator.registerNewRestaurant(cantuccio.getRestaurantName());

        Client firstClientInQueue = new Client("Mihnea Patentasu", "0123", LocalDateTime.now(), null);
        cantuccio.registerClientForRestaurant(firstClientInQueue);
        Thread.sleep(1000);
        Client secondClientInQueue = new Client("Victor Patentasu", "0124", LocalDateTime.now(), null);
        cantuccio.registerClientForRestaurant(secondClientInQueue);


//        cantuccio.notifyNewFirstAndSecondClientsFromDB();
//        cantuccio.removeClientFromQueueDB(1);
//        cantuccio.removeFirstClientInQueueFromDB();

        List<Client> cantuccioClientsWaiting = cantuccio.getAllWaitingClients();
        for(Client client : cantuccioClientsWaiting){
            System.out.println(client);
        }


    }
}
