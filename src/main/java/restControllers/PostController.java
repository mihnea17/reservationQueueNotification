package restControllers;

import notif.Client;
import notif.Notificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PostController {
    @Autowired
    Notificator notificatorWithJDBC;

    @PostMapping("/createClient/{restaurantId}")
    public void postNewClient(@PathVariable Long restaurantId, @RequestBody Client client){
        client.setTimeOfCreation(LocalDateTime.now());
        notificatorWithJDBC.registerClientForRestaurant(restaurantId, client);
    }

}
