package kickboard;

import kickboard.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired TicketRepository ticketRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverKickRented_UpdateTicket(@Payload KickRented kickRented){

        if(!kickRented.validate()) return;

        System.out.println("\n\n##### listener UpdateTicket : " + kickRented.toJson() + "\n\n");

        java.util.Optional<Ticket> ticketOptional = ticketRepository.findById(kickRented.getTicketId());
        Ticket ticket = ticketOptional.get();
    
        ticket.setTicketStatus("ticketUsed");
        ticketRepository.save(ticket);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_UpdateTicket(@Payload PaymentCancelled paymentCancelled){

        if(!paymentCancelled.validate()) return;

        System.out.println("\n\n##### listener UpdateTicket : " + paymentCancelled.toJson() + "\n\n");

        java.util.Optional<Ticket> ticketOptional = ticketRepository.findById(paymentCancelled.getTicketId());
            Ticket ticket = ticketOptional.get();

        ticketRepository.delete(ticket);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_UpdateTicket(@Payload PaymentApproved paymentApproved){

        if(!paymentApproved.validate()) return;

        System.out.println("\n\n##### listener UpdateTicket : " + paymentApproved.toJson() + "\n\n");

        java.util.Optional<Ticket> ticketOptional = ticketRepository.findById(paymentApproved.getTicketId());
            Ticket ticket = ticketOptional.get();
        
        ticket.setTicketStatus("ticketAvailable");
        ticketRepository.save(ticket);

    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
