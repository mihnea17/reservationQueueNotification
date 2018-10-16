package restControllers;

import notif.Client;
import notif.Notificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetController {
    @Autowired
    Notificator notificatorWithJDBC;

    @RequestMapping("/getWaitingClients")
    public List<Client> getWaitingClientsForARestaurant(@RequestParam(value="restaurantId") long restaurantId){
        List<Client> awaitingClients = notificatorWithJDBC.getAllWaitingClientsForRestaurant(restaurantId);
        return awaitingClients;
    }

    @RequestMapping("/getAllClients")
    public List<Client> getAllClientsOfARestaurant(@RequestParam(value="restaurantId") long restaurantId){
        List<Client> allClients = notificatorWithJDBC.getAllClientsForRestaurant(restaurantId);
        return allClients;
    }
}
