package kickboard.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name="ticket", url="http://localhost:8081")
public interface TicketService {

    @RequestMapping(method= RequestMethod.GET, path="/chkTicketStatus")
    public boolean chkTicketStatus(@RequestParam("ticketId") Long ticketId, 
                                   @RequestParam("usingTime") Long usingTime);

}

