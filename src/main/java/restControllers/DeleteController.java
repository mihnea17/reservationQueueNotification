package restControllers;

import notif.Notificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteController {
    @Autowired
    Notificator notificatorWithJDBC;

    @DeleteMapping("/clients/{restaurantId}/{clientId}")
    public String removeClientFromQueueDB(@PathVariable Long restaurantId, @PathVariable Long clientId){
        this.notificatorWithJDBC.removeClientFromQueueDB(restaurantId, clientId);
        return "Client removed from queue";
    }

    @DeleteMapping("/removeFirstClientFromWaitingQueue/{restaurantId}")
    public String removeFirstClientFromWaitingQueueDB(@PathVariable Long restaurantId){
        try{
            this.notificatorWithJDBC.removeFirstClientInQueueForRestaurant(restaurantId);
            return "First client removed from list";
        }
        catch (EmptyResultDataAccessException e){
            return "No client to remove from waiting queue";
        }
    }
}
