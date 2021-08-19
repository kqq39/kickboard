package kickboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    @RequestMapping(value = "/chkTicketStatus", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")

    public boolean chkTicketStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("##### /ticket/chkTicketStatus  called #####");

        boolean result = false;
        Long ticketId = Long.valueOf(request.getParameter("ticketId"));
        Long usingTime = Long.valueOf(request.getParameter("usingTime"));

        Ticket ticket = ticketRepository.findById(ticketId).get();

        if(ticket.getTicketStatus() == "ticketAvailable") {
            result = true;
        }

        return result;
        }

 }